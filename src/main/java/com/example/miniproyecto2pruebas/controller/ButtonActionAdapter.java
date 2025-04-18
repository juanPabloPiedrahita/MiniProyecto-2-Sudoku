package com.example.miniproyecto2pruebas.controller;

import com.example.miniproyecto2pruebas.view.HomeStage;
import com.example.miniproyecto2pruebas.view.SudokuStage;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import java.io.IOException;

/**
 * The {@code ButtonActionAdapter} class provides adapter implementations for the {@link IButtonAction} interface.
 * It defines actions for buttons in the user interface, such as displaying instructions and starting the game.
 *
 * This class helps to decouple the UI components from the specific actions that need to be performed when buttons
 * are clicked. It contains two internal static classes that handle specific button actions.
 * These internal classes are used to encapsulate the logic for each button action, ensuring that each button
 * behaves independently.
 *
 * @author Juan Pablo Piedrahita.
 * @version 3.0
 * @since 2.0
 */
public class ButtonActionAdapter {

    /**
     * The {@code InstructionsButtonAction} class is a static inner class that implements the {@link IButtonAction} interface.
     * It defines the action to be performed when the "Instructions" button is clicked. This action shows an informational
     * dialog with the rules and instructions for the game.
     *
     * This class is static because it does not need to hold a reference to an instance of {@code ButtonActionAdapter}
     * in order to function. It is independent of any other objects in the class.
     *
     * @author Juan Pablo Piedrahita.
     * @version 3.0
     * @since 2.0
     */
    public static class InstructionsButtonAction implements IButtonAction {
        /**
         * Executes the action for the "Instructions" button.
         * It displays a dialog containing the game instructions and rules.
         *
         * @param event the event generated by the button click.
         * @throws IOException if there is an issue during the display of the instructions (e.g., loading resources).
         */
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

    /**
     * The {@code PlayButtonAction} class is a static inner class that implements the {@link IButtonAction} interface.
     * It defines the action to be performed when the "Play" button is clicked. This action opens the Sudoku game stage
     * and closes the Home stage.
     *
     * This class is static because it does not need to hold a reference to an instance of {@code ButtonActionAdapter}
     * in order to function. It is independent of any other objects in the class.
     *
     * @author Juan Pablo Piedrahita and David Taborda.
     * @version 3.0
     * @since 2.0
     */
    public static class PlayButtonAction implements IButtonAction {
        /**
         * Executes the action for the "Play" button.
         * It starts the Sudoku game by opening the {@link SudokuStage} and closes the {@link HomeStage}.
         *
         * @param event the event generated by the button click.
         * @throws IOException if there is an issue during the stage transition.
         */
        @Override
        public void execute(ActionEvent event) throws IOException {
            SudokuStage.getInstance();
            HomeStage.deleteInstance();
        }
    }
}