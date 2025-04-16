
package com.example.miniproyecto2pruebas.view;


import com.example.miniproyecto2pruebas.controller.SudokuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SudokuStage extends Stage {

    public SudokuStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/miniproyecto2pruebas/sudoku-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        setTitle("Sudoku 6x6");
        setResizable(false);
        setScene(scene);
        show(); // Se muestra la ventana al instante
    }
}