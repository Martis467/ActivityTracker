package com.gui.tabs.goal;

import com.exception.UIException;
import com.gui.base.BaseJavaFXController;
import com.repositories.GoalRepository;
import com.utilities.JFXUtilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class GoalTabController extends BaseJavaFXController implements Initializable {
    //region FXML variables

    @FXML private AnchorPane fxRootPane;
    @FXML private Text fxGoalTitle;
    @FXML private VBox fxActivityView;
    @FXML private AnchorPane fxGoalAnchorPane;
    @FXML private LineChart fxGoalLineChart;

    //endregion

    private final String ADD_NEW_GOAL = "AddNewGoal";
    private final String EDIT_GOAL = "-";
    private GoalRepository repository;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.setDirectory("tabs/goal/");
            this.repository = new GoalRepository();
            //refreshGoals();
        } catch (UIException e) {
            e.printStackTrace();
        }
    }

    public void openAddNewGoal(ActionEvent actionEvent) {
        AddNewGoalController controller = this.moveToStage(ADD_NEW_GOAL, "Add Goal", false);
        controller.initData(repository, this);
    }

    public void openEditGoal(ActionEvent actionEvent) {

    }

    public void nextGoal(ActionEvent actionEvent) {

    }

    public void previousGoal(ActionEvent actionEvent) {

    }
}
