package com.gui.tabs.activity;

import com.base.BaseJavaFXController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
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

    private final String ADD_NEW_ACTIVITY = "AddNewActivity";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dir = "tabs/activity/";
    }

    public void openAddNewActivity(ActionEvent actionEvent) {
        moveToStage(ADD_NEW_ACTIVITY, "New Activity", false);
    }

    public void test(){
        System.out.println("works");
    }
}
