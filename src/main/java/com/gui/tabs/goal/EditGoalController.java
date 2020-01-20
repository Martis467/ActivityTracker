package com.gui.tabs.goal;

import com.enumerations.GoalType;
import com.exception.UIException;
import com.gui.base.BaseJavaFXController;
import com.gui.utilities.ParsingService;
import com.gui.utilities.TabManager;
import com.gui.utilities.ValidationService;
import com.models.Goal;
import com.models.GoalActivityRelation;
import com.repositories.GoalActivityRelationRepository;
import com.repositories.GoalRepository;
import com.utilities.Extensions;
import com.utilities.JFXUtilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EditGoalController extends BaseJavaFXController {

    private final String GOAL_ACTIVITY_RELATION_FXML = "GoalActivity";

    //region FXML variables

    @FXML private AnchorPane fxRootPane;
    @FXML private TextField fxNameTextField;
    @FXML private TextField fxCompletionWeightTextField;
    @FXML private TextArea fxDescriptionTextField;
    @FXML private ComboBox<GoalType> fxTypeComboBox;
    @FXML private DatePicker fxExpectedFinishDatePicker;

    //endregion

    //Repositories
    private GoalRepository goalRepository;
    private GoalActivityRelationRepository goalActivityRelationRepository;

    private BaseJavaFXController mainController;
    private int goalId;

    private List<GoalActivityRelation> goalActivityRelationList;

    public void initData(GoalRepository goalRepository, int goalId){
        this.goalRepository = goalRepository;
        this.setStage(fxRootPane);
        this.mainController = TabManager.getController(GoalTabController.class);
        this.goalId = goalId;


        try {
            this.setDirectory("tabs/goal/");
            this.initFields();
        } catch (SQLException e) {
            JFXUtilities.showAlert("Not found", "Error while fetching data from database, activity was not found", Alert.AlertType.ERROR);
        } catch (UIException e) {
            JFXUtilities.showAlert(e.getTitle(), e.getErrorMessage(), Alert.AlertType.ERROR);
        }
    }

    public void updateGoal(ActionEvent actionEvent) {
        try {
            ValidationService.validateGoalFields(fxNameTextField,fxCompletionWeightTextField,fxDescriptionTextField,
                    fxTypeComboBox, fxExpectedFinishDatePicker);

            Goal model = ParsingService.parseGoalModel(fxNameTextField,fxCompletionWeightTextField,fxDescriptionTextField,
                    fxTypeComboBox, fxExpectedFinishDatePicker);

            this.goalRepository.<Goal>update(model,goalId);

            handleGoalActivityRelationUpdate();

            this.mainController.refreshTab();
            this.closeCurrentStage();
        } catch (UIException e) {
            JFXUtilities.showAlert(e.getTitle(), e.getErrorMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            JFXUtilities.showAlert("Insertion failed", "Insertion failed, check the console for more details", Alert.AlertType.ERROR);
            this.closeCurrentStage();
        }
    }

    public void removeGoal(ActionEvent actionEvent) {
        try {
            if (!JFXUtilities.showConfirmation("Delete goal", "Are you sure you want to delete this goal?"))
                return;

            this.goalRepository.delete(goalId);

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

    public void relateToActivities(ActionEvent actionEvent) {
        GoalActivityController controller = this.moveToStage(GOAL_ACTIVITY_RELATION_FXML, "Relate goal to activities", false);

        // passing 0 as goalId because the current goal has yet been added to the database
        controller.initData(goalId, goalActivityRelationList);
    }

    private void initFields() throws SQLException {
        Goal goal = this.goalRepository.getById(this.goalId);
        this.goalActivityRelationRepository = new GoalActivityRelationRepository();

        this.goalActivityRelationList = this.goalActivityRelationRepository.getByGoalId(this.goalId);

        this.<GoalType>fillComboBox(fxTypeComboBox, Arrays.stream(GoalType.values()));

        this.fxNameTextField.setText(goal.name);
        this.fxCompletionWeightTextField.setText(String.valueOf(goal.completionWeight));
        this.fxDescriptionTextField.setText(goal.description);
        this.fxTypeComboBox.getSelectionModel().select(goal.type);
        this.fxExpectedFinishDatePicker.setValue(Extensions.getDate(goal.expectedFinish));
    }

    private void handleGoalActivityRelationUpdate() throws SQLException {
        List<GoalActivityRelation> updateGars = this.goalActivityRelationRepository.getByActivityId(this.goalId);

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
