package com.example.miniproyecto2pruebas;

import com.example.miniproyecto2pruebas.view.HomeStage;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The present development was made by David Taborda and Juan Pablo Piedrahita.
 */

/**
 * The Main class is the entry point for the JavaFX application.
 * It extends the {@link javafx.application.Application} class and is responsible for launching the application.
 * This class initializes the primary stage and displays the home screen of the game.
 *
 * @author Juan Pablo Piedrahita.
 * @version 3.0
 * @since 1.0
 */
public class Main extends Application {

    /**
     * The main entry point for the application.
     *
     * @param primaryStage The primary stage for this application.
     * @throws Exception If an error occurs during stage setup.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        HomeStage.getInstance();
    }

    /**
     * The main method to launch the application.
     * This is the standard entry point for any JavaFX application.
     *
     * @param args Command-line arguments (not used in this case).
     */
    public static void main(String[] args) {
        launch(args);
    }
}

