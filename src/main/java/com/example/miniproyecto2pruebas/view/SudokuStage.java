package com.example.miniproyecto2pruebas.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The {@code SudokuStage} class represents the stage (window) for the Sudoku game.
 * It is responsible for displaying the Sudoku game interface when the player starts the game.
 * This class follows the Singleton design pattern to ensure that only one instance of the Sudoku stage exists at any time.
 *
 * The {@code SudokuStage} initializes the scene by loading an FXML file and setting it as the root of the scene.
 * It also sets the window title, icon, and disables window resizing.
 *
 * This class is used as the view in the Model-View-Controller (MVC) architecture of the Sudoku application.
 *
 * @author Juan Pablo Piedrahita and David Taborda.
 * @version 3.0
 * @since 2.0
 */
public class SudokuStage extends Stage {

    /**
     * Creates a new instance of {@code SudokuStage}.
     * This constructor loads the FXML file for the Sudoku game view and sets it as the scene's root.
     * It also sets the window title, icon, and disables resizing.
     *
     * @throws IOException if there is an issue loading the FXML file or the resources.
     */
    public SudokuStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/miniproyecto2pruebas/sudoku-view.fxml"));
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
     * This inner static class holds the unique instance of {@code SudokuStage}.
     * It implements the Singleton pattern to ensure only one instance of the Sudoku stage exists.
     */
    private static class SudokuStageHolder {
        /**
         * The unique instance of {@code SudokuStage}.
         */
        private static SudokuStage INSTANCE;
    }

    /**
     * Returns the unique instance of {@code SudokuStage}.
     * If the instance does not exist, it is created.
     * This method follows the Singleton pattern to ensure only one instance exists.
     *
     * @return the unique instance of {@code SudokuStage}.
     * @throws IOException if there is an issue creating the instance.
     */
    public static SudokuStage getInstance() throws IOException {
        SudokuStageHolder.INSTANCE = SudokuStageHolder.INSTANCE != null ? SudokuStageHolder.INSTANCE : new SudokuStage();

        return SudokuStageHolder.INSTANCE;
    }

    /**
     * Closes and deletes the current instance of {@code SudokuStage}.
     * This method is used to close the Sudoku window and reset the instance to {@code null}.
     *
     * @throws IOException if there is an issue closing the instance.
     */
    public static void deleteInstance() throws IOException {
        SudokuStageHolder.INSTANCE.close();
        SudokuStageHolder.INSTANCE = null;
    }
}