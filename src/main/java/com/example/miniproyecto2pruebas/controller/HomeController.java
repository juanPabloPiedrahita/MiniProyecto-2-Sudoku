

package com.example.miniproyecto2pruebas.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;

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
        IButtonAction action = new ButtonActionAdapter.PlayButtonAction();
        action.execute(event);
    }
}