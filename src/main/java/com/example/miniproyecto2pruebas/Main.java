package com.example.miniproyecto2pruebas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sudoku.fxml")));
        primaryStage.setTitle("Sudoku 6x6");
        primaryStage.setScene(new Scene(root, 340, 340));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
