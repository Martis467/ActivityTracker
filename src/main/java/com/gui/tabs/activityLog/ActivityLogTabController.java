package com.gui.tabs.activityLog;

import com.enumerations.ActivityType;
import com.enumerations.FilteringPriority;
import com.enumerations.GoalType;
import com.exception.UIException;
import com.gui.base.BaseJavaFXController;
import com.models.ActivityLog;
import com.repositories.ActivityLogRepository;
import com.repositories.GoalActivityRelationRepository;
import com.strategies.randomizer.ActivityRandomizer;
import com.utilities.JFXUtilities;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import jdk.jshell.spi.ExecutionControl;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class ActivityLogTabController extends BaseJavaFXController implements Initializable {
    //region FXML variables

    @FXML private AnchorPane fxRootPane;
    @FXML private TextField fxTimeTextField;
    @FXML private CheckComboBox<ActivityType> fxActivityTypeCheckBox;
    @FXML private CheckComboBox<GoalType> fxGoalTypeCheckBox;
    @FXML private ComboBox<FilteringPriority> fxPriorityComboBox;
    @FXML private HBox fxInProgressVBox;
    @FXML private HBox fxCompletedVBox;
    @FXML private CheckBox fxOverFlowCheckBox;

    //endregion

    private final String ACTIVITY_LOG_VIEW = "ActivityLogView";
    private final String ACTIVITY_LOG_CREATOR = "ActivityLogCreator";
    private final String RANDOM_ACTIVITY_LOG_VIEW = "RandomActivityLogView";
    private ActivityLogRepository activityLogRepository;
    private GoalActivityRelationRepository goalActivityRelationRepository;
    private List<Button> activityLogs;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            this.setDirectory("tabs/activityLog/");
            this.activityLogRepository = new ActivityLogRepository();
            this.goalActivityRelationRepository = new GoalActivityRelationRepository();
            this.<ActivityType>fillCheckComboBox(fxActivityTypeCheckBox, Arrays.stream(ActivityType.values()), true);
            this.<GoalType>fillCheckComboBox(fxGoalTypeCheckBox, Arrays.stream(GoalType.values()), true);
            this.<FilteringPriority>fillComboBox(fxPriorityComboBox, Arrays.stream(FilteringPriority.values()));
            this.fxPriorityComboBox.getSelectionModel().selectFirst();
            refreshTab();
        } catch (UIException e){
            JFXUtilities.showAlert(e.getTitle(), e.getErrorMessage(), Alert.AlertType.ERROR);
        }
    }

    public void randomise(ActionEvent actionEvent) {
        try {
            ActivityRandomizer activityRandomizer = new ActivityRandomizer(
                    Integer.parseInt(this.fxTimeTextField.getText()),
                    this.fxActivityTypeCheckBox.getCheckModel().getCheckedItems(),
                    this.fxGoalTypeCheckBox.getCheckModel().getCheckedItems(),
                    this.fxPriorityComboBox.getValue(),
                    this.fxOverFlowCheckBox.isSelected(),
                    this.goalActivityRelationRepository.getAllMapped()
                    );
            var activities = activityRandomizer.getRandomActivities();
            System.out.println("lol");
        }catch (NumberFormatException e){
            JFXUtilities.showAlert("Total time invalid", "The total time value entered must be an intger.",
                    Alert.AlertType.ERROR);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ExecutionControl.NotImplementedException e) {
            JFXUtilities.showAlert("Not supported method", "The selected priority method has not been implemented," +
                    "please choose a different one", Alert.AlertType.ERROR);
        }
    }

    public void create(ActionEvent actionEvent) {

    }

    public void updateInProgressActivities(){

    }

    @Override
    public void refreshTab() {
        try {
            this.activityLogs = new LinkedList<>();

            List<Button> completedActivities = new LinkedList<>();
            List<Button> inProgressActivities = new LinkedList<>();
            List<ActivityLog> activityLogs = this.activityLogRepository.getAllMapped();

            if (activityLogs == null || activityLogs.isEmpty())
                return;

            activityLogs.forEach(al -> {
                if(al.completedAt != 0) completedActivities.add(createButton(al, true));
                else inProgressActivities.add(createButton(al, false));

            });

            this.activityLogs.addAll(inProgressActivities);
            this.fxInProgressVBox.getChildren().addAll(inProgressActivities);

            this.activityLogs.addAll(completedActivities);
            this.fxCompletedVBox.getChildren().addAll(completedActivities);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void openLogView(ActivityLog log){
        ActivityLogViewController controller = this.moveToStage(ACTIVITY_LOG_VIEW, "Log", false);
        controller.initData(log, activityLogRepository);
    }

    private Button createButton(ActivityLog log, boolean completed) {
        Button button = new Button(log.toString());
        button.setPrefHeight(35.0);
        button.setMinHeight(35.0);
        button.textAlignmentProperty().setValue(TextAlignment.CENTER);
        button.setWrapText(true);
        button.setUserData(log);

        if(completed){
            button.setPrefWidth(this.fxCompletedVBox.getPrefWidth());
        } else {
            button.setPrefWidth(this.fxInProgressVBox.getPrefWidth());
        }

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Button b = (Button) actionEvent.getSource();
                openLogView((ActivityLog) b.getUserData());
            }
        });

        return button;
    }
}
