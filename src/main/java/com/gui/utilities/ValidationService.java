package com.gui.utilities;

import com.enumerations.ActivityDuration;
import com.enumerations.ActivityType;
import com.enumerations.GoalType;
import com.exception.UIException;
import com.exception.UIExceptionType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.controlsfx.control.CheckComboBox;

public class ValidationService {

    private static final String ALPHANUMERIC_REGEX = "^[a-zA-Z0-9]+$";
    private static final String NUMERIC_REGEX = "[0-9]+";

    /**
     * Validate if new activity fields are in correct format
     * @throws UIException
     * @param fxNameTextField
     * @param fxDescriptionTextField
     * @param fxTypeComboBox
     * @param fxDurationCheckComboBox
     */
    public static void validateActivityFields(TextField fxNameTextField, TextArea fxDescriptionTextField,
                                              ComboBox<ActivityType> fxTypeComboBox,
                                              CheckComboBox<ActivityDuration> fxDurationCheckComboBox) throws UIException {
        if (!fxNameTextField.getText().matches(ALPHANUMERIC_REGEX) || fxNameTextField.getText().isEmpty())
            throw new UIException("Name", "Wrong fields", UIExceptionType.InvalidFields);

        if (fxNameTextField.getText().length() > 25)
            throw new UIException("Name field should be up to 25 characters", "Wrong fields");

        if (fxDurationCheckComboBox.getCheckModel().getCheckedItems().isEmpty())
            throw new UIException("Duration", "Wrong fields", UIExceptionType.InvalidFields);

        //todo: MOVE TO GOAL ACTIVITY RELATION
        //if (!fxWeightTextField.getShortText().matches(NUMERIC_REGEX) || fxWeightTextField.getShortText().isEmpty())
           // throw new UIException("Weight", "Wrong fields", UIExceptionType.InvalidFields);

        if (fxDescriptionTextField.getText().length() > 250 || fxDescriptionTextField.getText().isEmpty())
            throw new UIException("Description too long or empty, maximum amount 250 characters", "Wrong fields");

        if(fxTypeComboBox.getValue() == null)
            throw new UIException("Type not selected", "Wrong fields", UIExceptionType.InvalidFields);
    }

    /**
     * Validate if new goal fields are in correct format
     * @param fxNameTextField
     * @param fxCompletionWeightTextField
     * @param fxDescriptionTextField
     * @param fxTypeComboBox
     * @param fxExpectedFinishDatePicker
     */
    public static void validateGoalFields(TextField fxNameTextField, TextField fxCompletionWeightTextField,
                                          TextArea fxDescriptionTextField, ComboBox<GoalType> fxTypeComboBox,
                                          DatePicker fxExpectedFinishDatePicker) throws UIException {
        if (!fxNameTextField.getText().matches(ALPHANUMERIC_REGEX) || fxNameTextField.getText().isEmpty())
            throw new UIException("Name", "Wrong fields", UIExceptionType.InvalidFields);

        if (fxNameTextField.getText().length() > 25)
            throw new UIException("Name field should be up to 25 characters", "Wrong fields");

        if (!fxCompletionWeightTextField.getText().matches(NUMERIC_REGEX) || fxCompletionWeightTextField.getText().isEmpty())
            throw new UIException("Completion weight", "Wrong fields", UIExceptionType.InvalidFields);

        if (fxDescriptionTextField.getText().length() > 250 || fxDescriptionTextField.getText().isEmpty())
            throw new UIException("Description too long or empty, maximum amount 250 characters", "Wrong fields");

        if(fxTypeComboBox.getValue() == null)
            throw new UIException("Type not selected", "Wrong fields", UIExceptionType.InvalidFields);

        if (fxExpectedFinishDatePicker.getValue() == null)
            throw new UIException("Date not selected", "Wrong fields", UIExceptionType.InvalidFields);
    }
}
