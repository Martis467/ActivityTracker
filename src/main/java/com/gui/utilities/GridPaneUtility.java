package com.gui.utilities;

import com.models.Goal;
import com.models.GoalActivityRelation;
import com.utilities.JFXUtilities;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.List;

public class GridPaneUtility {

    /**
     * Create a grid pane with (CheckBox, Text, Text, TexField)
     * that monitors changes and maps activities to goals
     * @param fxRootPane
     * @param goals
     * @param goalActivities
     * @param activityId
     */
    public static void createGridPaneActivityGoalRelationMapper(AnchorPane fxRootPane, List<Goal> goals,
                                                                List<GoalActivityRelation> goalActivities, int activityId) {


        GridPane gridPane = getGridPane(fxRootPane.getPrefWidth(), fxRootPane.getPrefHeight());

        int i = 0;

        /**
         * Make a grid pane with columns like:
         * Checkbox | GoalName | Weight
         */
        for (Goal g :
                goals) {

            CheckBox checkBox = new CheckBox();
            Text nameText = new Text(g.name);
            Text typeText = new Text(g.type.toString());
            TextField textField = new TextField(getWeight(goalActivities, g.id, activityId));
            textField.setPrefWidth(40);


            gridPane.add(checkBox, 1, i);
            gridPane.add(nameText, 2, i);
            gridPane.add(typeText, 3, i);
            gridPane.add(textField, 4, i);
            i++;

            GoalActivityRelation gar = getGoalActivity(goalActivities, activityId, g.id);
            textField.setText(String.valueOf(gar.weight));

            if(gar.weight != 0) checkBox.selectedProperty().setValue(true);

            addListeners(textField, checkBox, goalActivities, activityId, g.id);
        }

        fxRootPane.getChildren().add(gridPane);
    }

    private static void addListeners(TextField weightTextField, CheckBox checkBox,
                                     List<GoalActivityRelation> goalActivities, int activityId, int goalId) {

        weightTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> {

            if (newValue.length() != 1) return;
            if (!newValue.matches("[0-9]+")) return;

            int weight = Integer.parseInt(newValue);

            if (weight < 1)
                JFXUtilities.showAlert("Shame", "Weight should be at least 1, no point in doing the activity otherwise", Alert.AlertType.INFORMATION);

            if (weight > 5)
                JFXUtilities.showAlert("Overkill", "Weight is to high, no such activity exists", Alert.AlertType.INFORMATION);

            GoalActivityRelation goalActivityRelation = getGoalActivity(goalActivities, activityId, goalId);
            goalActivityRelation.weight = weight;

        }));

        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    int weight = Integer.parseInt(weightTextField.getText());
                    goalActivities.add(new GoalActivityRelation(activityId, goalId, weight));

                } else {
                    GoalActivityRelation goalActivityRelation = getGoalActivity(goalActivities, activityId, goalId);
                    goalActivities.remove(goalActivityRelation);
                }
            }
        });
    }

    /**
     * Returns the goal activity relation weight, if this doesn't exist returns 0;
     *
     * @param goalActivities
     * @param goalId
     * @param activityId
     * @return
     */
    private static String getWeight(List<GoalActivityRelation> goalActivities, int goalId, int activityId) {
        GoalActivityRelation model = goalActivities.stream().filter(ga -> ga.goalId == goalId && ga.activityId == activityId)
                .findFirst().orElse(GoalActivityRelation.GetDefault());

        return String.valueOf(model.weight);
    }

    /**
     * Creates a grid pane with preset dimensions and node centering
     *
     * @param prefWidth
     * @param preffHeight
     * @return
     */
    private static GridPane getGridPane(double prefWidth, double preffHeight) {
        GridPane gridPane = new GridPane();
        gridPane.setPrefHeight(preffHeight);
        gridPane.setPrefWidth(prefWidth);
        gridPane.setHgap(15);
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setPadding(new Insets(20, 0, 0, 20));

        return gridPane;
    }

    /**
     * Get GoalActivityRelation entity by goalId and activityId
     *
     * @param goalActivities
     * @param activityId
     * @param goalId
     * @return GoalActivityRelation if exists else null
     */
    private static GoalActivityRelation getGoalActivity(List<GoalActivityRelation> goalActivities,
                                                        int activityId, int goalId) {
        GoalActivityRelation gar = goalActivities.stream()
                .filter(ga -> ga.activityId == activityId && ga.goalId == goalId).findFirst().orElse(GoalActivityRelation.GetDefault());
        return gar;
    }
}
