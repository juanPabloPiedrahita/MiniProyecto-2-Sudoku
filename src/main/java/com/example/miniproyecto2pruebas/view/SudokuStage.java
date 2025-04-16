
package com.example.miniproyecto2pruebas.view;


import com.example.miniproyecto2pruebas.controller.SudokuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class SudokuStage extends Stage {

    public SudokuStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/miniproyecto2pruebas/sudoku-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        setTitle("Miniproyecto 2 - Sudoku Master");
        setResizable(false);
        setScene(scene);
        Image icon = new Image(String.valueOf(getClass().getResource("/com/example/miniproyecto2pruebas/favicon.png")));
        getIcons().add(icon);
        show(); // Se muestra la ventana al instante
    }

    private static class SudokuStageHolder {
        private static SudokuStage INSTANCE;
    }

    public static SudokuStage getInstance() throws IOException {
        SudokuStageHolder.INSTANCE = SudokuStageHolder.INSTANCE != null ? SudokuStageHolder.INSTANCE : new SudokuStage();

        return SudokuStageHolder.INSTANCE;
    }

    public static void deleteInstance() throws IOException {
        SudokuStageHolder.INSTANCE.close();
        SudokuStageHolder.INSTANCE = null;
    }
}