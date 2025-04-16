package com.example.miniproyecto2pruebas;

import com.example.miniproyecto2pruebas.view.HomeStage;
import com.example.miniproyecto2pruebas.view.SudokuStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        new HomeStage(); // Mostramos directamente la pantalla de inicio
    }

    public static void main(String[] args) {
        launch(args);
}
}

