package com.example.miniproyecto2pruebas.view;

import com.example.miniproyecto2pruebas.controller.HomeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.Parent;

public class HomeStage extends Stage {

    public HomeStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/miniproyecto2pruebas/home-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        setTitle("Miniproyecto 2 - Sudoku Master");
        setResizable(false);
        setScene(scene);
        // Establece el icono
        //Solo cambia la el "icon.png" y ya.
        Image icon = new Image(String.valueOf(getClass().getResource("/com/example/miniproyecto2pruebas/favicon.png")));
        getIcons().add(icon);
        show(); // Mostrar la ventana
    }

    private static class HomeStageHolder {
        private static HomeStage INSTANCE;
    }

    public static HomeStage getInstance() throws IOException {
        HomeStageHolder.INSTANCE = HomeStageHolder.INSTANCE != null ? HomeStageHolder.INSTANCE : new HomeStage();

        return HomeStageHolder.INSTANCE;
    }

    public static void deleteInstance() throws IOException {
        HomeStageHolder.INSTANCE.close();
        HomeStageHolder.INSTANCE = null;
    }
}