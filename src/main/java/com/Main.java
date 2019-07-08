package com;

import com.utilities.JFXUtilities;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Martynas Jakimcikas
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage = JFXUtilities.loadWindow("MainWindow", "ActivityTracker", true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}