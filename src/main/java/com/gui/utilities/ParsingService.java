package com.gui.utilities;

import com.enumerations.ActivityDuration;
import com.enumerations.ActivityType;
import com.enumerations.GoalType;
import com.exception.UIException;
import com.models.Activity;
import com.models.Goal;
import com.utilities.HexColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.controlsfx.control.CheckComboBox;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.EnumSet;

public class ParsingService {

    /**
     * Parse out activity model from javafx fields
     * @throws UIException
     * @param fxNameTextField
     * @param fxDescriptionTextField
     * @param fxTypeComboBox
     * @param fxDurationCheckComboBox
     * @return
     */
    public static Activity parseActivityModel(TextField fxNameTextField, TextArea fxDescriptionTextField,
                                              ComboBox<ActivityType> fxTypeComboBox,
                                              CheckComboBox<ActivityDuration> fxDurationCheckComboBox) throws UIException {
        Activity model = new Activity();

        model.name = fxNameTextField.getText();
        model.description = fxDescriptionTextField.getText();
        model.type = fxTypeComboBox.getValue();
        model.expectedDurations = EnumSet.copyOf(fxDurationCheckComboBox.getCheckModel().getCheckedItems());
        model.createdAt = new Date().getTime()/1000; // Unix time stamp
        model.hexColor = HexColorPicker.getRandomColor();

        return model;
    }

    /**
     * Parse out goal model from javafx fields
     * @param fxNameTextField
     * @param fxCompletionWeightTextField
     * @param fxDescriptionTextField
     * @param fxTypeComboBox
     * @param fxExpectedFinishDatePicker
     * @return
     * @throws UIException
     */
    public static Goal parseGoalModel(TextField fxNameTextField, TextField fxCompletionWeightTextField,
                                      TextArea fxDescriptionTextField, ComboBox<GoalType> fxTypeComboBox,
                                      DatePicker fxExpectedFinishDatePicker) throws UIException {
        Goal model = new Goal();

        model.name = fxNameTextField.getText();
        model.completionWeight = Integer.parseInt(fxCompletionWeightTextField.getText());
        model.description = fxDescriptionTextField.getText();
        model.createdAt = getNowInUnixTime();
        model.expectedFinish = getUnixTimeStamp(fxExpectedFinishDatePicker.getValue());
        model.finished = 0;
        model.type = fxTypeComboBox.getValue();
        model.percentage = 0;
        model.hexColor = HexColorPicker.getRandomColor();

        model.validate();

        return model;
    }

    private static long getNowInUnixTime(){return new Date().getTime()/1000;}

    private static long getUnixTimeStamp(LocalDate d) {return d.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();}

}
