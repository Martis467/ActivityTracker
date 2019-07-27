package com.gui.tabs.activity;

import com.enumerations.ActivityDuration;
import com.enumerations.ActivityType;
import com.exception.UIException;
import com.gui.base.BaseJavaFXController;
import com.gui.utilities.ParsingService;
import com.gui.utilities.ValidationService;
import com.models.Activity;
import com.repositories.ActivityRepository;
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
    private ActivityRepository repository;
    private ActivityTabController mainController;
    private int activityId;

    /**
     * Initialize data from parent controller
     *
     * @param repository
     */
    public void initData(ActivityRepository repository, int activityId, ActivityTabController mainController) {
        this.repository = repository;
        this.setStage(fxRootPane);
        this.mainController = mainController;
        this.activityId = activityId;

        try {
            initFields();
        } catch (SQLException e) {
            JFXUtilities.showAlert("Not found", "Error while fetching data from database, activity was not found", Alert.AlertType.ERROR);
            this.closeCurrentStage();
        }
    }

    public void removeActivity(ActionEvent actionEvent) {
        try {
            if (!JFXUtilities.showConfirmation("Delete activity", "Are you sure you want to delete this activity?"))
                return;

            this.repository.delete(activityId);

            // After deletion renew displayed activity list
            this.mainController.refreshActivities();
        } catch (SQLException e) {
            JFXUtilities.showAlert("Delete failed", "Error deleting data from database, activity was not found", Alert.AlertType.ERROR);
        }
        this.closeCurrentStage();
    }

    public void updateActivity(ActionEvent actionEvent) {
        try {
            ValidationService.validateActivityFields(fxNameTextField, fxDescriptionTextField,
                    fxTypeComboBox, fxDurationCheckComboBox);

            Activity model = ParsingService.parseActivityModel(fxNameTextField, fxDescriptionTextField,
                    fxTypeComboBox, fxDurationCheckComboBox);

            this.repository.update(model, activityId);

            // After update renew displayed activity list
            this.mainController.refreshActivities();
        } catch (UIException e) {
            JFXUtilities.showAlert(e.getTitle(), e.getErrorMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            JFXUtilities.showAlert("Update failed", "Error updating data in database, activity was not found", Alert.AlertType.ERROR);
        }
        this.closeCurrentStage();
    }

    private void initFields() throws SQLException {
        Activity activity = this.repository.getById(this.activityId);

        this.<ActivityType>fillComboBox(fxTypeComboBox, Arrays.stream(ActivityType.values()));
        this.<ActivityDuration>fillCheckComboBox(fxDurationCheckComboBox, ActivityDuration.getValues());

        this.fxNameTextField.setText(activity.name);
        this.fxDescriptionTextField.setText(activity.description);
        this.fxTypeComboBox.getSelectionModel().select(activity.type);
        activity.expectedDurations.forEach(ad -> fxDurationCheckComboBox.getCheckModel().toggleCheckState(ad));
    }
}
