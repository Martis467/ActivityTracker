package com.gui.tabs.activity;

import com.enumerations.ActivityDuration;
import com.enumerations.ActivityType;
import com.exception.UIException;
import com.gui.base.BaseJavaFXController;
import com.gui.utilities.ParsingService;
import com.gui.utilities.TabManager;
import com.gui.utilities.ValidationService;
import com.models.Activity;
import com.models.GoalActivityRelation;
import com.repositories.ActivityRepository;
import com.repositories.GoalActivityRelationRepository;
import com.utilities.JFXUtilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.CheckComboBox;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EditActivityController extends BaseJavaFXController {

    //region FXML variables

    @FXML
    private AnchorPane fxRootPane;
    @FXML
    private TextField fxNameTextField;
    @FXML
    private TextArea fxDescriptionTextField;
    @FXML
    private ComboBox<ActivityType> fxTypeComboBox;
    @FXML
    private CheckComboBox<ActivityDuration> fxDurationCheckComboBox;

    //endregion

    // Repositories
    private ActivityRepository activityRepository;
    private GoalActivityRelationRepository goalActivityRelationRepository;

    private BaseJavaFXController mainController;
    private int activityId;

    private List<GoalActivityRelation> goalActivityRelationList;

    private final String ACTIVITY_GOAL_RELATION_FXML = "ActivityGoal";

    /**
     * Initialize data from parent controller
     * @param repository
     * @param activityId
     */
    public void initData(ActivityRepository repository, int activityId) {
        this.activityRepository = repository;
        this.setStage(fxRootPane);
        this.mainController = TabManager.getController(ActivityTabController.class);
        this.activityId = activityId;

        try {
            this.setDirectory("tabs/activity/");
            this.initFields();
        } catch (SQLException e) {
            JFXUtilities.showAlert("Not found", "Error while fetching data from database, activity was not found", Alert.AlertType.ERROR);
            this.closeCurrentStage();
        } catch (UIException e) {
            JFXUtilities.showAlert(e.getTitle(), e.getErrorMessage(), Alert.AlertType.ERROR);
        }
    }

    public void updateActivity(ActionEvent actionEvent) {
        try {
            ValidationService.validateActivityFields(fxNameTextField, fxDescriptionTextField,
                    fxTypeComboBox, fxDurationCheckComboBox);

            Activity model = ParsingService.parseActivityModel(fxNameTextField, fxDescriptionTextField,
                    fxTypeComboBox, fxDurationCheckComboBox);

            this.activityRepository.update(model, activityId);

            handleGoalActivityRelationUpdate();

            // After update renew displayed activity list
            this.mainController.refreshTab();
        } catch (UIException e) {
            JFXUtilities.showAlert(e.getTitle(), e.getErrorMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            JFXUtilities.showAlert("Update failed", "Error updating data in database, activity was not found", Alert.AlertType.ERROR);
        }
        this.closeCurrentStage();
    }

    public void removeActivity(ActionEvent actionEvent) {
        try {
            if (!JFXUtilities.showConfirmation("Delete activity", "Are you sure you want to delete this activity?"))
                return;

            this.activityRepository.delete(activityId);

            for (GoalActivityRelation ga :
                    goalActivityRelationList) {
                this.goalActivityRelationRepository.delete(ga.id);
            }

            // After deletion renew displayed activity list
            this.mainController.refreshTab();
            this.closeCurrentStage();
        } catch (SQLException e) {
            JFXUtilities.showAlert("Delete failed", "Error deleting data from database, activity was not found", Alert.AlertType.ERROR);
        } catch (UIException e) {
            JFXUtilities.showAlert(e.getTitle(), e.getErrorMessage(), Alert.AlertType.ERROR);
        }
    }

    public void relateToGoals(ActionEvent actionEvent) {
        ActivityGoalController controller = this.moveToStage(this.ACTIVITY_GOAL_RELATION_FXML, "Relate activities to goals", false);

        // passing 0 id because we have not yet added this activity
        controller.initData(this.activityId, this.goalActivityRelationList);
    }

    private void initFields() throws SQLException {
        Activity activity = this.activityRepository.getById(this.activityId);
        this.goalActivityRelationRepository = new GoalActivityRelationRepository();

        this.goalActivityRelationList = this.goalActivityRelationRepository.getByActivityId(this.activityId);

        this.<ActivityType>fillComboBox(fxTypeComboBox, Arrays.stream(ActivityType.values()));
        this.<ActivityDuration>fillCheckComboBox(fxDurationCheckComboBox, ActivityDuration.getValues());

        this.fxNameTextField.setText(activity.name);
        this.fxDescriptionTextField.setText(activity.description);
        this.fxTypeComboBox.getSelectionModel().select(activity.type);
        activity.expectedDurations.forEach(ad -> fxDurationCheckComboBox.getCheckModel().toggleCheckState(ad));
    }

    private void handleGoalActivityRelationUpdate() throws SQLException {
        List<GoalActivityRelation> updateGars = this.goalActivityRelationRepository.getByActivityId(this.activityId);

        List<GoalActivityRelation> deleteGars = updateGars.stream()
                        .filter(ga -> !goalActivityRelationList.contains(ga))
                        .collect(Collectors.toList());

        List<GoalActivityRelation> createGars = goalActivityRelationList.stream()
                .filter(ga -> !updateGars.contains(ga))
                .collect(Collectors.toList());

        updateGars.removeAll(deleteGars);

        if (!deleteGars.isEmpty()){
            for (GoalActivityRelation ga : deleteGars) goalActivityRelationRepository.delete(ga.id);
        }

        if(!createGars.isEmpty()){
            for (GoalActivityRelation ga : createGars) goalActivityRelationRepository.insert(ga);
        }

        if(!updateGars.isEmpty()){
            for (GoalActivityRelation ga : updateGars) goalActivityRelationRepository.update(ga, ga.id);
        }
    }
}
