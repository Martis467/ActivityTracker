package com.gui.tabs.activity;

import com.enumerations.ActivityDuration;
import com.enumerations.ActivityType;
import com.repositories.ActivityRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.CheckComboBox;

public class EditActivityController {

    //region FXML variables

    @FXML
    private AnchorPane fxRootPane;
    @FXML private TextField fxNameTextField;
    @FXML private TextField fxWeightTextField;
    @FXML private TextArea fxDescriptionTextField;
    @FXML private ComboBox<ActivityType> fxTypeComboBox;
    @FXML private CheckComboBox<ActivityDuration> fxDurationCheckComboBox;

    //endregion
    private ActivityRepository repository;
    private int activityId;

    /**
     * Initialize data from parent controller
     * @param repository
     */
    public void initData(ActivityRepository repository, int activityId){
        this.repository = repository;
        this.activityId = activityId;
    }

    public void removeActivity(ActionEvent actionEvent) {

    }

    public void updateActivity(ActionEvent actionEvent) {

    }
}
