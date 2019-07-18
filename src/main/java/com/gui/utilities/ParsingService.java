package com.gui.utilities;

import com.enumerations.ActivityDuration;
import com.enumerations.ActivityType;
import com.exception.UIException;
import com.models.Activity;
import com.utilities.HexColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.controlsfx.control.CheckComboBox;

import java.util.Date;
import java.util.EnumSet;

public class ParsingService {

    /**
     * Parse out activity model from javafx fields
     * @throws UIException
     * @param fxNameTextField
     * @param fxWeightTextField
     * @param fxDescriptionTextField
     * @param fxTypeComboBox
     * @param fxDurationCheckComboBox
     * @return
     */
    public static Activity parseActivityModel(TextField fxNameTextField, TextField fxWeightTextField,
                                              TextArea fxDescriptionTextField, ComboBox<ActivityType> fxTypeComboBox,
                                              CheckComboBox<ActivityDuration> fxDurationCheckComboBox) throws UIException {
        Activity model = new Activity();

        model.name = fxNameTextField.getText();
        model.weight = Integer.parseInt(fxWeightTextField.getText());
        model.description = fxDescriptionTextField.getText();
        model.type = fxTypeComboBox.getValue();
        model.expectedDurations = EnumSet.copyOf(fxDurationCheckComboBox.getCheckModel().getCheckedItems());
        model.createdAt = new Date().getTime()/1000; // Unix time stamp
        model.hexColor = HexColorPicker.getRandomColor();

        model.validate();

        return model;
    }
}
