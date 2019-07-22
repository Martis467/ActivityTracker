package com.utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class JFXUtilities {

    /**
     * Displays an alert message and waits for the user to press confirm
     * @param title
     * @param message
     * @param type
     */
    public static void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays a confirmation window and return the users choise
     * @param title
     * @param message
     * @return
     */
    public static boolean showConfirmation(String title, String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        Optional<ButtonType> action = alert.showAndWait();

        return action.get() == ButtonType.OK;
    }

    /**
     * Loads a Java FX stage, from xml file name.
     * To get the FXML loader, get scene from stage and call <b>getUserData</b>
     * @param xmlFileName file name without the extension
     * @param title stage title
     * @param debug f this is set to true, will stdout xml file full path
     * @return stage
     */
    public static Stage loadWindow(String xmlFileName, String title, boolean debug) throws IOException {
        String path = createPath(xmlFileName);
        URL resource = JFXUtilities.class.getClassLoader().getResource(path);

        // If debugging is on print out the path of the xml file
        if(debug)
            System.out.println("Loading resource: " + path + System.lineSeparator() + "Loaded : " + (resource != null));

        if (resource == null)
            throw new IOException();

        FXMLLoader loader = new FXMLLoader(resource);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setTitle(title);
        stage.setUserData(loader); // Add loader for later reference
        return stage;
    }

    /**
     * Loads a Java FX stage, from FXMLoader
     * @param loader
     * @param title
     * @return
     * @throws IOException
     */
    public static Stage loadWindowFromLoader(FXMLLoader loader, String title) throws IOException {
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        scene.setUserData(loader); //Remembering this thing for later reference
        stage.setScene(scene);
        stage.setTitle(title);
        return stage;
    }

    /**
     * Gets java fx loader
     * @param xmlFileName scene to be attached to the loader
     * @return
     */
    public static FXMLLoader getLoader(String xmlFileName){
        URL resource = JFXUtilities.class.getClassLoader().getResource(createPath(xmlFileName));
        FXMLLoader loader = new FXMLLoader(resource);
        return loader;
    }

    /**
     * creates a full path from a given xml file name
     * @param xmlFileName
     * @return
     */
    private static String createPath(String xmlFileName) {
        return "gui/" + xmlFileName + ".fxml";
    }
}
