package com.gui.tabs.activity;

import com.enumerations.ActivityDuration;
import com.gui.base.BaseJavaFXController;
import com.enumerations.ActivityType;
import com.exception.UIException;
import com.exception.UIExceptionType;
import com.models.Activity;
import com.repositories.ActivityRepository;
import com.utilities.HexColorPicker;
import com.utilities.JFXUtilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;
import java.util.ResourceBundle;

public class AddNewActivityController extends BaseJavaFXController implements Initializable{
    //region FXML variables

    @FXML private AnchorPane fxRootPane;
    @FXML private TextField fxNameTextField;
    @FXML private TextField fxWeightTextField;
    @FXML private TextArea fxDescriptionTextField;
    @FXML private ComboBox<ActivityType> fxTypeComboBox;
    @FXML private CheckComboBox<ActivityDuration> fxDurationCheckComboBox;

    //endregion

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.<ActivityType>fillComboBox(fxTypeComboBox, Arrays.stream(ActivityType.values()));
        this.<ActivityDuration>fillCheckComboBox(fxDurationCheckComboBox, ActivityDuration.getValues());
    }

    public void saveActivity(ActionEvent actionEvent) {
        try {
            validateFields();
            Activity model = parseModel();
            ActivityRepository repository = new ActivityRepository();
            repository.<Activity>insert(model);
        } catch (UIException e) {
            JFXUtilities.showAlert(e.getTitle(), e.getErrorMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            JFXUtilities.showAlert("Insertion failed", "Insertion failed, check the console for more details", Alert.AlertType.ERROR);
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

        if (fxNameTextField.getText().length() > 25)
            throw new UIException("Name field should be up to 25 characters", "Wrong fields");

        if (fxDurationCheckComboBox.getCheckModel().getCheckedItems().isEmpty())
            throw new UIException("Duration", "Wrong fields", UIExceptionType.InvalidFields);

        if (!fxWeightTextField.getText().matches(NUMERIC_REGEX) || fxWeightTextField.getText().isEmpty())
            throw new UIException("Weight", "Wrong fields", UIExceptionType.InvalidFields);

        if (fxDescriptionTextField.getText().length() > 250 || fxDescriptionTextField.getText().isEmpty())
            throw new UIException("Description too long or empty, maximum amount 250 characters", "Wrong fields");

        if(fxTypeComboBox.getValue() == null)
            throw new UIException("Type not selected", "Wrong fields", UIExceptionType.InvalidFields);
    }

    /**
     * Parse out activity model from javafx fields
     * @throws UIException
     * @return
     */
    private Activity parseModel() throws Exception{
        Activity model = new Activity();

        model.name = fxNameTextField.getText();
        model.weight = Integer.parseInt(fxWeightTextField.getText());
        model.description = fxDescriptionTextField.getText();
        model.type = fxTypeComboBox.getValue();
        model.expectedDurations = EnumSet.copyOf(fxDurationCheckComboBox.getCheckModel().getCheckedItems());
        model.createdAt = new Date().getTime()/1000; // Unix time stamp
        model.hexColor = HexColorPicker.getRandomColor();

        return model;
    }
}
