package com.gui.tabs.goal;

import com.gui.base.BaseJavaFXController;
import com.gui.utilities.GridPaneUtility;
import com.models.Activity;
import com.models.GoalActivityRelation;
import com.repositories.ActivityRepository;
import com.utilities.JFXUtilities;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class GoalActivityController extends BaseJavaFXController {

    @FXML
    private AnchorPane fxRootPane;

    /**
     * Initialize controller data
     */
    public void initData(int goalId, List<GoalActivityRelation> goalActivities) {
        ActivityRepository activityRepository = new ActivityRepository();
        this.setStage(this.fxRootPane);

        if(!goalActivities.isEmpty()){
            List<GoalActivityRelation> emptyModels = goalActivities.stream()
                    .filter(ga -> ga.weight == 0).collect(Collectors.toList());

            goalActivities.removeAll(emptyModels);
        }

        try {
            List<Activity> activities = activityRepository.getAll();
            GridPaneUtility.GoalActivityRelationMapper(fxRootPane, activities, goalActivities, goalId);

        } catch (SQLException e) {
            JFXUtilities.showAlert("Not found", "Error while fetching data from database, activity goal relation was not found", Alert.AlertType.ERROR);
            this.closeCurrentStage();
        }
    }
}
