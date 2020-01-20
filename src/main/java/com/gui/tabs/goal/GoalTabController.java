package com.gui.tabs.goal;

import com.exception.UIException;
import com.gui.base.BaseJavaFXController;
import com.models.Activity;
import com.models.Goal;
import com.models.GoalActivityRelation;
import com.repositories.GoalActivityRelationRepository;
import com.repositories.GoalRepository;
import com.utilities.JFXUtilities;
import com.views.GoalTabView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class GoalTabController extends BaseJavaFXController implements Initializable {
    //region FXML variables

    @FXML private AnchorPane fxRootPane;
    @FXML private Text fxGoalTitle;
    @FXML private VBox fxActivityViewVBox;
    @FXML private AnchorPane fxGoalAnchorPane;
    @FXML private LineChart fxGoalLineChart;

    //endregion

    private final String ADD_NEW_GOAL = "AddNewGoal";
    private final String EDIT_GOAL = "EditGoal";
    private GoalRepository goalRepository;
    private GoalActivityRelationRepository goalActivityRelationRepository;
    private GoalTabView goalTabView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.setDirectory("tabs/goal/");
            this.goalRepository = new GoalRepository();
            this.goalActivityRelationRepository = new GoalActivityRelationRepository();
            this.fxGoalTitle.setWrappingWidth(this.fxGoalAnchorPane.getPrefWidth());
            this.fxGoalTitle.textAlignmentProperty().setValue(TextAlignment.LEFT);
            refreshTab();
        } catch (UIException e) {
            e.printStackTrace();
        }
    }

    public void openAddNewGoal(ActionEvent actionEvent) {
        AddNewGoalController controller = this.moveToStage(ADD_NEW_GOAL, "Add Goal", false);
        controller.initData(goalRepository, this);
    }

    public void openEditGoal(ActionEvent actionEvent) {
        EditGoalController controller = this.moveToStage(EDIT_GOAL, "Edit Goal", false);
        controller.initData(goalRepository, goalTabView.getGoal().id);
    }

    public void nextGoal(ActionEvent actionEvent) {
        this.goalTabView.next();
        this.setGoalView(goalTabView.getGoal(), goalTabView.getGoalActivities());
    }

    public void previousGoal(ActionEvent actionEvent) {
        this.goalTabView.previous();
        this.setGoalView(goalTabView.getGoal(), goalTabView.getGoalActivities());
    }

    @Override
    public void refreshTab() {
        try {
            List<Goal> goals = this.goalRepository.getAll();
            List<GoalActivityRelation> goalActivities = this.goalActivityRelationRepository.getAllMapped();
            this.goalTabView = new GoalTabView(goals, goalActivities);
            this.setGoalView(goalTabView.getGoal(), goalTabView.getGoalActivities());

        } catch (SQLException e) {
            JFXUtilities.showAlert("Database error", "Failed to retrieve data from the database", Alert.AlertType.ERROR);
        }
    }

    private void setGoalView(Goal goal, List<Activity> goalActivities){
        this.fxActivityViewVBox.getChildren().clear();

        this.fxGoalTitle.setText(goal.name);

        goalActivities.forEach(a -> {
            Button button = createButton(a);
            this.fxActivityViewVBox.getChildren().add(button);
        });
    }

    private Button createButton(Activity activity) {
        Button button = new Button(activity.getLongText());
        button.setPrefWidth(this.fxActivityViewVBox.getPrefWidth());
        button.setPrefHeight(150.0);
        button.setMinHeight(150.0);
        button.textAlignmentProperty().setValue(TextAlignment.LEFT);
        button.setWrapText(true);

        return button;
    }
}
