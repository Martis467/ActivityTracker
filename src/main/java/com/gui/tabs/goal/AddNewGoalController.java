package com.gui.tabs.goal;

import com.enumerations.GoalType;
import com.exception.UIException;
import com.gui.base.BaseJavaFXController;
import com.gui.utilities.ParsingService;
import com.gui.utilities.ValidationService;
import com.models.Goal;
import com.repositories.GoalRepository;
import com.utilities.JFXUtilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.util.Arrays;

public class AddNewGoalController extends BaseJavaFXController {
    //region FXML variables

    @FXML private AnchorPane fxRootPane;
    @FXML private TextField fxNameTextField;
    @FXML private TextField fxCompletionWeightTextField;
    @FXML private TextArea fxDescriptionTextField;
    @FXML private ComboBox<GoalType> fxTypeComboBox;
    @FXML private DatePicker fxExpectedFinishDatePicker;

    //endregion

    private GoalRepository goalRepository;
    private GoalTabController mainController;

    public void saveGoal(ActionEvent actionEvent) {
        try {
            ValidationService.validateGoalFields(fxNameTextField,fxCompletionWeightTextField,fxDescriptionTextField,
                    fxTypeComboBox, fxExpectedFinishDatePicker);

            Goal model = ParsingService.parseGoalModel(fxNameTextField,fxCompletionWeightTextField,fxDescriptionTextField,
                    fxTypeComboBox, fxExpectedFinishDatePicker);

            this.goalRepository.<Goal>insert(model);

            // After insertion we should refresh the goal page
            //this.mainController.refreshGoals();
        } catch (UIException e) {
            JFXUtilities.showAlert(e.getTitle(), e.getErrorMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            JFXUtilities.showAlert("Insertion failed", "Insertion failed, check the console for more details", Alert.AlertType.ERROR);
            this.closeCurrentStage();
        }
    }

    public void close(ActionEvent actionEvent) { this.closeCurrentStage();}

    /**
     * Initialize data from parent controller
     * @param goalRepository
     * @param goalTabController
     */
    public void initData(GoalRepository goalRepository, GoalTabController goalTabController){
        this.goalRepository = goalRepository;
        this.mainController = mainController;

        this.setStage(fxRootPane);
        this.setCurrentDate(fxExpectedFinishDatePicker);
        this.<GoalType>fillComboBox(fxTypeComboBox, Arrays.stream(GoalType.values()));
    }
}
