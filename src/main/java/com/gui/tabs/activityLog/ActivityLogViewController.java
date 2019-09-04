package com.gui.tabs.activityLog;

import com.enumerations.ActivityRating;
import com.gui.base.BaseJavaFXController;
import com.models.ActivityLog;
import com.repositories.ActivityLogRepository;
import com.utilities.Extensions;
import com.utilities.JFXUtilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.sql.SQLException;

public class ActivityLogViewController extends BaseJavaFXController {

    //region FXML variables

    @FXML private AnchorPane fxRootPane;
    @FXML private Text fxName;
    @FXML private org.controlsfx.control.Rating fxRating;
    @FXML private Text fxDescription;
    @FXML private Text fxDuration;
    @FXML private Text fxType;
    @FXML private Text fxCreated;
    @FXML private Text fxCompleted;
    @FXML private Button fxCompleteActivityButton;

    //endregion

    private ActivityLog log;
    private ActivityLogRepository repository;

    public void completeActivity(ActionEvent actionEvent) {
        if(fxRating.getRating() == 0){
            JFXUtilities.showAlert("Completion failed", "Can't complete an activity without a rating", Alert.AlertType.ERROR);
            return;
        }

        this.log.rating = ActivityRating.parseFromInt((int)fxRating.getRating());

        try {
            this.repository.update(this.log, this.log.id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.closeCurrentStage();
    }

    public void initData(ActivityLog log, ActivityLogRepository repository){
        this.setStage(this.fxRootPane);

        this.log = log;
        this.repository = repository;
        boolean completed = log.completedAt != 0;

        this.fxName.setText(log.activity.name);
        this.fxRating.setRating(log.rating.getRating());
        this.fxDescription.setText(log.activity.description);
        this.fxDuration.setText(String.valueOf((log.duration)));
        this.fxType.setText(log.activity.type.toString());
        this.fxCreated.setText(Extensions.getDate(log.createdAt).toString());

        if (completed){
            fxCompleted.setText(Extensions.getDate(log.completedAt).toString());
            fxCompleteActivityButton.setVisible(false);
            fxRating.setDisable(true);
        }
        else fxCompleted.setText("");
    }
}
