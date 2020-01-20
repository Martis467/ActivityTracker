package com.gui.tabs.goal;

import com.enumerations.GoalType;
import com.exception.UIException;
import com.gui.base.BaseJavaFXController;
import com.gui.utilities.ParsingService;
import com.gui.utilities.ValidationService;
import com.models.Goal;
import com.models.GoalActivityRelation;
import com.repositories.GoalActivityRelationRepository;
import com.repositories.GoalRepository;
import com.utilities.JFXUtilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AddNewGoalController extends BaseJavaFXController {

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

    private GoalTabController mainController;

    private List<GoalActivityRelation> goalActivityRelationList;


    /**
     * Initialize data from parent controller
     * @param goalRepository
     * @param goalTabController
     */
    public void initData(GoalRepository goalRepository, GoalTabController goalTabController){
        this.goalRepository = goalRepository;
        this.goalActivityRelationRepository = new GoalActivityRelationRepository();

        this.mainController = goalTabController;

        this.goalActivityRelationList = new LinkedList<>();

        try {
            this.setDirectory("tabs/goal/");
        } catch (UIException e) {
            JFXUtilities.showAlert(e.getTitle(), e.getErrorMessage(), Alert.AlertType.ERROR);
        }

        this.setStage(fxRootPane);
        this.setCurrentDate(fxExpectedFinishDatePicker);
        this.<GoalType>fillComboBox(fxTypeComboBox, Arrays.stream(GoalType.values()));
    }

    public void saveGoal(ActionEvent actionEvent) {
        try {
            ValidationService.validateGoalFields(fxNameTextField,fxCompletionWeightTextField,fxDescriptionTextField,
                    fxTypeComboBox, fxExpectedFinishDatePicker);

            Goal model = ParsingService.parseGoalModel(fxNameTextField,fxCompletionWeightTextField,fxDescriptionTextField,
                    fxTypeComboBox, fxExpectedFinishDatePicker);

            int id = this.goalRepository.<Goal>insert(model);

            if (goalActivityRelationList.isEmpty()){
                this.mainController.refreshTab();
                this.closeCurrentStage();
                return;
            }

            for (GoalActivityRelation ga : goalActivityRelationList){
                ga.goalId = id;
                goalActivityRelationRepository.insert(ga);
            }

            this.mainController.refreshTab();
            JFXUtilities.showAlert("Goal added", "New goal has been created", Alert.AlertType.INFORMATION);
        } catch (UIException e) {
            JFXUtilities.showAlert(e.getTitle(), e.getErrorMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            JFXUtilities.showAlert("Insertion failed", "Insertion failed, check the console for more details", Alert.AlertType.ERROR);
        }
        this.closeCurrentStage();
    }

    public void close(ActionEvent actionEvent) { this.closeCurrentStage();}

    public void relateToActivities(ActionEvent actionEvent) {
        GoalActivityController controller = this.moveToStage(GOAL_ACTIVITY_RELATION_FXML, "Relate goal to activities", false);

        // passing 0 as goalId because the current goal has yet been added to the database
        controller.initData(0, goalActivityRelationList);
    }
}
