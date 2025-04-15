package com.example.miniproyecto2pruebas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sudoku-view.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Sudoku 6x6");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();

        // Si en el futuro deseas usar el controlador directamente:
        // SudokuController controller = loader.getController();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

