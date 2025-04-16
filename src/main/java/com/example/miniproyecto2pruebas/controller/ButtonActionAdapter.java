package com.example.miniproyecto2pruebas.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import com.example.miniproyecto2pruebas.view.HomeStage;
import com.example.miniproyecto2pruebas.view.SudokuStage;

import java.io.IOException;

public class ButtonActionAdapter {

    // Clase interna para el botón de Instrucciones
    public static class InstructionsButtonAction implements IButtonAction {
        @Override
        public void execute(ActionEvent event) throws IOException {
            String instrucciones = """
                    🎯 Objetivo:
                    Completa la cuadrícula 6x6 con los números del 1 al 6.
                    
                    ✅ Reglas:
                    - No se repite ningún número en la misma fila.
                    - No se repite ningún número en la misma columna.
                    - No se repite ningún número en el mismo bloque 2x3.
                    
                    🧠 Consejo:
                    Usa la lógica, ¡y diviértete!
                    """;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Instrucciones");
            alert.setHeaderText("Cómo jugar al Sudoku 6x6");
            alert.setContentText(instrucciones);
            alert.showAndWait();
        }
    }
    public static class PlayButtonAction implements IButtonAction {
        @Override
        public void execute(ActionEvent event) throws IOException {

            // Abre la ventana del Sudoku
            SudokuStage.getInstance();

            // Cierra la ventana actual
            // Se puede obtener el Stage de la fuente del evento.
            /*Node source = (javafx.scene.Node) event.getSource();
            Stage currentStage = (javafx.stage.Stage) source.getScene().getWindow();
            currentStage.close();*/
            HomeStage.deleteInstance();
            // Aquí podrías mostrar un error al usuario o gestionar la excepción según convenga.
        }
    }
}