package com.gui;

import com.gui.base.BaseJavaFXController;
import com.gui.tabs.activity.ActivityTabController;
import com.gui.tabs.activityLog.ActivityLogTabController;
import com.gui.tabs.goal.GoalTabController;
import com.gui.utilities.TabManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class MainWindowController implements Initializable {

    @FXML private TabPane fxRootPane;
    @FXML private TextArea fxConsoleTextArea;
    @FXML private ActivityTabController activityTabController;
    @FXML private GoalTabController goalTabController;
    @FXML private ActivityLogTabController activityLogTabController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Vector<BaseJavaFXController> controllers = new Vector<>();
        TabManager.setBaseControllers(controllers, this.fxConsoleTextArea);
        controllers.add(this.activityTabController);
        controllers.add(this.activityLogTabController);
        controllers.add(this.goalTabController);
    }
}
