package com.gui;

import com.gui.tabs.activity.ActivityTabController;
import com.gui.tabs.activityLog.ActivityLogTabController;
import com.gui.tabs.goal.GoalTabController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML private TabPane fxRootPane;
    @FXML private TextArea fxConsoleTextArea;
    @FXML private ActivityTabController activityTabController;
    @FXML private GoalTabController goalTabController;
    @FXML private ActivityLogTabController activityLogTabController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        activityTabController.setConsole(fxConsoleTextArea);
    }
}
