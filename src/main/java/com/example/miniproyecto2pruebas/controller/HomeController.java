

package com.example.miniproyecto2pruebas.controller;

import com.example.miniproyecto2pruebas.view.HomeStage;
import com.example.miniproyecto2pruebas.view.SudokuStage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.Parent;

import java.io.IOException;

public class HomeController {
    @FXML
    private ImageView logoImage;

    @FXML
    private Button playButton;

    @FXML
    public void initialize() {
        Image logo = new Image(String.valueOf(getClass().getResource("/com/example/miniproyecto2pruebas/logo.png")));
        logoImage.setImage(logo);
    }

    @FXML
    public void handlePlayButton(ActionEvent event) throws IOException {
        new SudokuStage(); // 👈 Se usa la clase personalizada
        ((Stage) playButton.getScene().getWindow()).close(); // 👈 Cerramos esta ventana

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
