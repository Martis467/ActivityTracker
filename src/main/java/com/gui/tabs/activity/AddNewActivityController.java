package com.gui.tabs.activity;

import com.gui.base.BaseJavaFXController;
import com.enumerations.ActivityType;
import com.exception.UIException;
import com.exception.UIExceptionType;
import com.models.Activity;
import com.repositories.ActivityRepository;
import com.utilities.JFXUtilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class AddNewActivityController extends BaseJavaFXController implements Initializable{

    //region FXML variables

    @FXML private AnchorPane fxRootPane;
    @FXML private TextField fxNameTextField;
    @FXML private TextField fxDurationTextField;
    @FXML private TextField fxWeightTextField;
    @FXML private ComboBox<ActivityType> fxTypeComboBox;
    @FXML private TextArea fxDescriptionTextField;

    //endregion


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.<ActivityType>fillComboBox(fxTypeComboBox, Arrays.stream(ActivityType.values()));

        fxTypeComboBox.getSelectionModel().selectFirst();
    }

    public void saveActivity(ActionEvent actionEvent) {
        try {
            validateFields();
            Activity model = parseModel();
        } catch (UIException e) {
            JFXUtilities.showAlert(e.getTitle(), e.getErrorMessage(), Alert.AlertType.ERROR);
        }
    }

    public void close(ActionEvent actionEvent) {
        try {
            this.closeCurrentStage();
        } catch (UIException e) {
            JFXUtilities.showAlert(e.getTitle(), e.getErrorMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Validate if fields are in correct format
     * @throws UIException
     */
    private void validateFields() throws UIException {

        if (!fxNameTextField.getText().matches(ALPHANUMERIC_REGEX) || fxNameTextField.getText().isEmpty())
            throw new UIException("Name", "Wrong fields", UIExceptionType.InvalidFields);

        if (!fxDurationTextField.getText().matches(NUMERIC_REGEX) || fxDurationTextField.getText().isEmpty())
            throw new UIException("Duration", "Wrong fields", UIExceptionType.InvalidFields);

        if (!fxWeightTextField.getText().matches(NUMERIC_REGEX) || fxWeightTextField.getText().isEmpty())
            throw new UIException("Weight", "Wrong fields", UIExceptionType.InvalidFields);

        if (fxDescriptionTextField.getText().length() > 250 || fxDescriptionTextField.getText().isEmpty())
            throw new UIException("Description too long or empty, maximum amount 250 characters", "Wrong fields");
    }

    /**
     * Parse out activity model from javafx fields
     * @throws UIException
     * @return
     */
    private Activity parseModel() throws UIException{
        //Activity model = new Activity();
        return null;
    }
}
