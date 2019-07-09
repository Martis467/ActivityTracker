package com.gui.tabs.activity;

import com.base.BaseJavaFXController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AddNewActivityController extends BaseJavaFXController {

    //region FXML variables

    @FXML private AnchorPane fxRootPane;
    @FXML private TextField fxNameTextField;
    @FXML private TextField fxDurationTextField;
    @FXML private TextField fxWeightTextField;
    @FXML private ComboBox fxTypeComboBox;
    @FXML private TextArea fxDescriptionTextField;

    //endregion

    public void saveActivity(ActionEvent actionEvent) {

    }

    public void close(ActionEvent actionEvent) {

    }
}
