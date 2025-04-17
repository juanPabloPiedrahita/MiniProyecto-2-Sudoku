package com.example.miniproyecto2pruebas.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.Parent;
import java.io.IOException;

/**
 * The HomeStage class represents the starting screen of the application.
 * This class extends the Stage class and serves as the initial window displayed when the application is launched.
 * It follows the Singleton design pattern to ensure that only one instance of this stage exists.
 *
 * It loads the home view from the FXML file, sets the window title, disables resizing, and adds an icon to the window.
 *
 *
 * This class is part of the View in the Model-View-Controller (MVC) architecture.
 *
 * @author David Taborda and Juan Pablo Piedrahita.
 * @version 3.0
 * @since 2.0
 * @see javafx.stage.Stage
 */
public class HomeStage extends Stage {

    /**
     * Constructor for the HomeStage class.
     * Initializes the home stage by loading the FXML view and setting up the stage properties.
     *
     * @throws IOException if the FXML file cannot be loaded
     */
    public HomeStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/miniproyecto2pruebas/home-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        setTitle("Miniproyecto 2 - Sudoku Master");
        setResizable(false);
        setScene(scene);
        Image icon = new Image(String.valueOf(getClass().getResource("/com/example/miniproyecto2pruebas/favicon.png")));
        getIcons().add(icon);
        show();
    }

    /**
     * Private static class used for implementing the Singleton pattern.
     * Holds the single instance of HomeStage.
     */
    private static class HomeStageHolder {
        private static HomeStage INSTANCE;
    }

    /**
     * Returns the unique instance of the HomeStage class.
     * If the instance is not created yet, it will be initialized.
     *
     * @return the instance of HomeStage
     * @throws IOException if the FXML file cannot be loaded
     */
    public static HomeStage getInstance() throws IOException {
        HomeStageHolder.INSTANCE = HomeStageHolder.INSTANCE != null ? HomeStageHolder.INSTANCE : new HomeStage();

        return HomeStageHolder.INSTANCE;
    }

    /**
     * Closes and deletes the instance of the HomeStage.
     * This method ensures that the stage is properly closed and its instance is set to null.
     *
     * @throws IOException if the instance cannot be closed
     */
    public static void deleteInstance() throws IOException {
        HomeStageHolder.INSTANCE.close();
        HomeStageHolder.INSTANCE = null;
    }
}