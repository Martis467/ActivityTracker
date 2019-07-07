package com.base;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import com.utilities.JFXUtilities;

import java.io.IOException;
import java.util.AbstractCollection;

public class BaseJavaFXController {

    // Set to true to log exceptions
    protected boolean debug = false;

    /**
     * Move to next stage
     * @param xmlFile gui file name to switch to
     * @param title title of the next stage
     * @param close set true to close current stage, false to keep it open
     * @return next stage controller
     */
    protected  <T> T moveToStage(String xmlFile, String title, boolean close){
        try {
            Stage nextStage = JFXUtilities.loadWindow(xmlFile, title, debug);
            FXMLLoader loader = (FXMLLoader)nextStage.getUserData();
            nextStage.show();
            return loader.<T>getController();
        } catch (IOException e) {
            if (debug)
                e.printStackTrace();
            JFXUtilities.showAlert("Stage opening failed",
                    "Could not open stage, the given xmlFile could not be found",
                    Alert.AlertType.ERROR);
        }

        return null;
    }

    /**
     * Fill a list with collection values
     * @param view list view to fill
     * @param collection collection with values to fill
     * @param <T> type of object to fill in the view
     */
    private <T> void fillListView(ListView<T> view, AbstractCollection<T> collection ){
        ObservableList<T> items = FXCollections.observableArrayList(collection);
        view.setItems(items);
    }

    /**
     * Fill a combo box with collection values
     * @param comboBox combo box to fill
     * @param collection collection with values to fill
     * @param <T> type of object to fill in the combo box
     */
    private <T> void fillComboBox(ComboBox<T> comboBox, AbstractCollection<T> collection) {
        ObservableList<T> items = FXCollections.observableArrayList(collection);
        comboBox.getItems().setAll(items);
    }

    /**
     * Init current scene with given data
     * @throws Exception
     */
    public void initData() throws Exception { throw new Exception("Not implemented");}
}
