package com.example.miniproyecto2pruebas.view;

import com.example.miniproyecto2pruebas.controller.HomeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HomeStage extends Stage {

    public HomeStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/miniproyecto2pruebas/home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        setTitle("Inicio - Sudoku 6x6");
        setResizable(false);
        setScene(scene);
        show(); // Mostrar la ventana
    }
}