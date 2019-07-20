package com.gui.tabs.activity;

import com.enumerations.ActivityDuration;
import com.gui.base.BaseJavaFXController;
import com.enumerations.ActivityType;
import com.exception.UIException;
import com.gui.utilities.ParsingService;
import com.gui.utilities.ValditionService;
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
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.Arrays;
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

    private ActivityRepository repository;
    private ActivityTabController mainController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setStage(fxRootPane);
        this.<ActivityType>fillComboBox(fxTypeComboBox, Arrays.stream(ActivityType.values()));
        this.<ActivityDuration>fillCheckComboBox(fxDurationCheckComboBox, ActivityDuration.getValues());
    }

    public void saveActivity(ActionEvent actionEvent) {
        try {
            ValditionService.validateActivityFields(fxNameTextField, fxWeightTextField, fxDescriptionTextField,
                    fxTypeComboBox, fxDurationCheckComboBox);

            Activity model = ParsingService.parseActivityModel(fxNameTextField, fxWeightTextField, fxDescriptionTextField,
                    fxTypeComboBox, fxDurationCheckComboBox);

            this.repository.<Activity>insert(model);

            // After insertion renew displayed activity list
            this.mainController.refreshActivities();
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

    /**
     * Initialize data from parent controller
     * @param repository
     * @param mainController
     */
    public void initData(ActivityRepository repository, ActivityTabController mainController){
        this.repository = repository;
        this.mainController = mainController;
    }
}
