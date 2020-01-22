package com.gui.tabs.activity;

import com.exception.UIException;
import com.gui.base.BaseJavaFXController;
import com.gui.utilities.TabManager;
import com.models.Activity;
import com.repositories.ActivityRepository;
import com.utilities.JFXUtilities;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class ActivityTabController extends BaseJavaFXController implements Initializable {
    //region FXML variables

    @FXML private AnchorPane fxRootPane;
    @FXML private VBox fxActivityVBox;
    @FXML private MenuBar fxMenuBar;
    @FXML private Text fxTypeTickBox;
    @FXML private CheckBox fxAlphabeticalTickBox;
    @FXML private CheckBox fxDurationTickBox;
    @FXML private CheckBox fxAmountTickBox;
    @FXML private BarChart fxBarChart;

    //endregion

    private final String ADD_NEW_ACTIVITY_FXML = "AddNewActivity";
    private final String EDIT_ACTIVITY_FXML = "EditActivity";
    private ActivityRepository repository;

    // containers to keep references
    List<Button> activityButtons;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.setDirectory("tabs/activity/");
            this.repository = new ActivityRepository();
            refreshTab();
        } catch (UIException e) {
            JFXUtilities.showAlert(e.getTitle(), e.getErrorMessage(), Alert.AlertType.ERROR);
        }
    }

    public void openAddNewActivity(ActionEvent actionEvent) {
        AddNewActivityController controller = this.moveToStage(ADD_NEW_ACTIVITY_FXML, "New Activity", false);
        controller.initData(this.repository);
    }

    private void openEditActivity(int activityId) {
        EditActivityController controller = this.moveToStage(EDIT_ACTIVITY_FXML, "Edit", false);
        controller.initData(repository, activityId);
    }

    @Override
    public void refreshTab(){
        try {
            List<Activity> activities = repository.getAll();
            activityButtons = new LinkedList<>();

            if(activities == null || activities.isEmpty())
                return;

            activities.forEach(a -> this.activityButtons.add(createButton(a)));

            // Reset VBox
            this.fxActivityVBox.getChildren().clear();
            this.fxActivityVBox.getChildren().addAll(activityButtons);
        } catch (SQLException e) {
            // Add info to console
            JFXUtilities.showAlert("Database error", "Failed to retrieve data from the database", Alert.AlertType.ERROR);
        }
    }

    private Button createButton(Activity activity){
        Button button = new Button(activity.getShortText());
        button.setPrefWidth(this.fxActivityVBox.getPrefWidth());
        button.setPrefHeight(100.0);
        button.setMinHeight(100.0);
        button.textAlignmentProperty().setValue(TextAlignment.CENTER);
        button.setWrapText(true);
        button.setUserData(activity.id);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Button b = (Button)actionEvent.getSource();
                openEditActivity((int)b.getUserData());
            }
        });
        return button;
    }
}
