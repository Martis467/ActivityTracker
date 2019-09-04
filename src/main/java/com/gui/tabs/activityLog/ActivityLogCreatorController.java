package com.gui.tabs.activityLog;

import com.exception.UIException;
import com.gui.base.BaseJavaFXController;
import com.models.ActivityLog;
import com.repositories.ActivityLogRepository;
import com.repositories.ActivityRepository;
import com.strategies.TypeListManager;
import com.utilities.JFXUtilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.sql.SQLException;

public class ActivityLogCreatorController extends BaseJavaFXController {
    //region FXML variables

    @FXML private AnchorPane fxRootPane;

    @FXML private VBox fxWorkoutVBox;
    @FXML private VBox fxReadVBox;
    @FXML private VBox fxProgramVBox;
    @FXML private VBox fxGameVBox;
    @FXML private VBox fxMusicVBox;
    @FXML private VBox fxEducationVBox;

    @FXML private Text fxTotalTimeText;
    @FXML private VBox fxTotalVBox;

    //endregion

    private ActivityLogTabController controller;

    private ActivityLogRepository repository;
    private TypeListManager typeListManager;

    public void initData(ActivityLogRepository repository, ActivityLogTabController controller){
        this.repository = repository;
        var activityRepository = new ActivityRepository();
        this.controller = controller;

        // Since all the boxes are the same width, the same width can be used for every box
        var width = fxProgramVBox.getWidth();

        try {
            var activityList = activityRepository.getAll();
            typeListManager = new TypeListManager(activityList, width, 30);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createLogs(ActionEvent actionEvent) {
        try {
            var logs = typeListManager.getLogs();

            for (var l :
                    logs) {
                repository.insert(l);
            }

            controller.updateInProgressActivities();
        } catch (UIException e) {
            JFXUtilities.showAlert(e.getTitle(), e.getErrorMessage(), Alert.AlertType.ERROR);
        } catch (SQLException e) {
            JFXUtilities.showAlert("Creation failed", "Could not create selected logs", Alert.AlertType.ERROR);
        }
    }
}
