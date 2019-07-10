package com.base;

import com.exception.UIException;
import com.exception.UIExceptionType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.utilities.JFXUtilities;

import java.io.IOException;
import java.util.AbstractCollection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BaseJavaFXController {

    // Set to true to log exceptions
    protected boolean debug = false;

    // Fxml file directry
    protected String dir = "";

    // Current stage, needed to close current stage
    private Stage fxStage = null;

    // Regexes
    protected final String ALPHANUMERIC_REGEX = "^[a-zA-Z0-9]+$";
    protected final String NUMERIC_REGEX = "[0-9]+";

    /**
     * Move to next stage
     * @param xmlFile gui file name to switch to
     * @param title title of the next stage
     * @param close set true to close current stage, false to keep it open
     * @return next stage controller
     */
    protected <T> T moveToStage(String xmlFile, String title, boolean close){
        try {
            Stage nextStage = JFXUtilities.loadWindow(dir + xmlFile, title, debug);
            FXMLLoader loader = (FXMLLoader)nextStage.getUserData();
            nextStage.show();

            if(close)
                fxStage.close();

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
     * Closes current stage
     */
    protected void closeCurrentStage() throws UIException {
        if (fxStage == null)
            throw new UIException(this.getClass().getSimpleName(), "Cannot close window", UIExceptionType.NullElement);
    }

    /**
     * Sets stage var
     * @param pane
     */
    protected void setStage(AnchorPane pane){fxStage = (Stage) pane.getScene().getWindow();}

    /**
     * Sets stage var
     * @param pane
     */
    protected void setStage(BorderPane pane){fxStage = (Stage) pane.getScene().getWindow();}

    /**
     * Fill a list with collection values
     * @param view list view to fill
     * @param collection collection with values to fill
     * @param <T> type of object to fill in the view
     */
    protected  <T> void fillListView(ListView<T> view, AbstractCollection<T> collection ){
        ObservableList<T> items = FXCollections.observableArrayList(collection);
        view.setItems(items);
    }

    /**
     * Fill a combo box with collection values
     * @param comboBox combo box to fill
     * @param collection collection with values to fill
     * @param <T> type of object to fill in the combo box
     */
    protected <T> void fillComboBox(ComboBox<T> comboBox, AbstractCollection<T> collection) {
        ObservableList<T> items = FXCollections.observableArrayList(collection);
        comboBox.getItems().setAll(items);
    }

    /**
     * Fill a combo box with stream values
     * @param comboBox combo box to fill
     * @param stream collection with values to fill
     * @param <T> type of object to fill in the combo box
     */
    protected <T> void fillComboBox(ComboBox<T> comboBox, Stream<T> stream) {
        ObservableList<T> items = FXCollections.observableArrayList(stream.collect(Collectors.toList()));
        comboBox.getItems().setAll(items);
    }

    /**
     * Init current scene with given data
     * @throws Exception
     */
    public void initData() throws Exception { throw new Exception("Not implemented");}
}
