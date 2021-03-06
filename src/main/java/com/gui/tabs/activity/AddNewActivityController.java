package com.gui.tabs.activity;

import com.enumerations.ActivityDuration;
import com.gui.base.BaseJavaFXController;
import com.enumerations.ActivityType;
import com.exception.UIException;
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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AddNewActivityController extends BaseJavaFXController {

    private final String ACTIVITY_GOAL_RELATION_FXML = "ActivityGoal";

    //region FXML variables

    @FXML private AnchorPane fxRootPane;
    @FXML private TextField fxNameTextField;
    @FXML private TextArea fxDescriptionTextField;
    @FXML private ComboBox<ActivityType> fxTypeComboBox;
    @FXML private CheckComboBox<ActivityDuration> fxDurationCheckComboBox;

    //endregion

    //Repositories
    private ActivityRepository activityRepository;
    private GoalActivityRelationRepository goalActivityRelationRepository;

    private BaseJavaFXController mainController;

    private List<GoalActivityRelation> goalActivityRelationList;

    /**
     * Initialize data from parent controller
     * @param repository
     */
    public void initData(ActivityRepository repository){
        this.activityRepository = repository;
        this.goalActivityRelationRepository = new GoalActivityRelationRepository();

        this.mainController = TabManager.getController(ActivityTabController.class);

        this.goalActivityRelationList = new LinkedList<>();

        try {
            this.setDirectory("tabs/activity/");
        } catch (UIException e) {
            JFXUtilities.showAlert(e.getTitle(), e.getErrorMessage(), Alert.AlertType.ERROR);
        }

        this.<ActivityType>fillComboBox(fxTypeComboBox, Arrays.stream(ActivityType.values()));
        this.<ActivityDuration>fillCheckComboBox(fxDurationCheckComboBox, ActivityDuration.getValues(), false);
        this.setStage(fxRootPane);
    }

    public void saveActivity(ActionEvent actionEvent) {
        try {
            ValidationService.validateActivityFields(fxNameTextField, fxDescriptionTextField,
                    fxTypeComboBox, fxDurationCheckComboBox);

            Activity model = ParsingService.parseActivityModel(fxNameTextField, fxDescriptionTextField,
                    fxTypeComboBox, fxDurationCheckComboBox);

            int id = this.activityRepository.<Activity>insert(model);

            this.mainController.refreshTab();

            if(goalActivityRelationList.isEmpty()){
                this.closeCurrentStage();
                return;
            }

            for (GoalActivityRelation ga :
                    goalActivityRelationList) {
                ga.activityId = id;
                goalActivityRelationRepository.insert(ga);
            }

            this.closeCurrentStage();
            JFXUtilities.showAlert("Activity added", "New activity has been created", Alert.AlertType.INFORMATION);
        } catch (UIException e) {
            JFXUtilities.showAlert(e.getTitle(), e.getErrorMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            JFXUtilities.showAlert("Insertion failed", "Insertion failed, check the console for more details", Alert.AlertType.ERROR);
            this.closeCurrentStage();
        }
    }

    public void close(ActionEvent actionEvent) {
            this.closeCurrentStage();
    }

    public void relateToGoals(ActionEvent actionEvent) {
        ActivityGoalController controller = this.moveToStage(ACTIVITY_GOAL_RELATION_FXML, "Relate activity to goals", false);

        // passing 0 id because we have not yet added this activity
        controller.initData(0, goalActivityRelationList);
    }
}
