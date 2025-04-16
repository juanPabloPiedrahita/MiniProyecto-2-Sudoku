package com.example.miniproyecto2pruebas.controller;


import com.example.miniproyecto2pruebas.model.SudokuBoard;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.*;

public class SudokuController{

    //public Button helpButton;
    @FXML
    private GridPane boardGridPane;

    @FXML
    private Label helpLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private Button helpButton;

    @FXML
    private Button instructionsButton;

    private SudokuBoard sudokuBoard;
    private int remainingHelps = 4;
    private List<TextField> editableFields = new ArrayList<>();
    private Map<String, String> errorMap = new HashMap<>();

    @FXML
    public void initialize() {
        sudokuBoard = new SudokuBoard();
        sudokuBoard.fillRandomTwoNumbersPerBlock();

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                TextField tf = new TextField();
                tf.setPrefSize(50, 50);
                tf.setAlignment(Pos.CENTER);
                tf.setStyle(getBorderStyle(row, col));

                int value = sudokuBoard.getNumberAt(row, col);
                String baseStyle = getBorderStyle(row, col);
                if (value != 0) {
                    tf.setText(String.valueOf(value));
                    tf.setDisable(true);
                    tf.setStyle(baseStyle + "-fx-background-color: lightgray; -fx-font-weight: bold;");
                } else {
                    final int r = row, c = col;
                    tf.setStyle(baseStyle);
                    editableFields.add(tf);
                    tf.setOnKeyReleased(event  -> {
                        String newVal = tf.getText();
                        if(newVal.length() > 1){
                            tf.setText(newVal.substring(0, 1));
                            return;
                        }
                        if (!newVal.matches("[1-6]?")) {
                            tf.setText("");
                        } else if (!newVal.isEmpty()) {
                            int num = Integer.parseInt(newVal);

                            boolean inRow = !sudokuBoard.isRowValid(r, c, num);
                            boolean inCol = !sudokuBoard.isColumnValid(r, c, num);
                            boolean inBlock = !sudokuBoard.isBlockValid(r, c, num);
                            if (sudokuBoard.isSafe(r, c, num) && !inRow && !inCol && !inBlock) {
                                sudokuBoard.setNumberAt(r, c, num);
                                tf.setStyle(baseStyle); // válido
                                errorMap.remove(r + "," + c);
                                updateErrorLabel();
                                //errorLabel.setText("");
                                moveToNextEmptyField(tf);
                            } else {
                                tf.setStyle(baseStyle + "-fx-background-color: #F5A9A9;"); // inválido

                                StringBuilder message = new StringBuilder("Error: el número ");
                                message.append(num).append(" ya está en esta parte: ");

                                List<String> parts = new ArrayList<>();
                                if(inRow) parts.add("la fila " + (r+1) + "; ");
                                //if(inRow && inCol) parts.add("la fila " + (r+1) + " y la columna " + (c+1) + ".");
                                if(inCol) parts.add("la columna " + (c+1) + "; ");
                                //if(inRow && inCol && inBlock) parts.add("la fila " + (r+1) + ", la columna " + (c+1) + " y en el bloque.");
                                //if(inRow && inBlock) parts.add("la fila " + (r+1) + " y en el bloque.");
                                //if(inCol && inBlock) parts.add("la columna " + (c+1) + " y en el bloque.");
                                if(inBlock) parts.add("el bloque.");

                                message.append(String.join(" ", parts));
                                errorMap.put(r + "," + c, message.toString());
                                updateErrorLabel();
                                //errorLabel.setText(message.toString());
                            }
                        } else {
                            sudokuBoard.setNumberAt(r, c, 0);
                            tf.setStyle(getBorderStyle(r, c)); // vacío
                            errorMap.remove(r + "," + c);
                            updateErrorLabel();
                            //errorLabel.setText("");
                        }
                    });
                }

                boardGridPane.add(tf, col, row);
            }
        }
        helpLabel.setText("¿Atascado? Aquí tienes " + remainingHelps + " ayudas.  --> ");
        errorLabel.setText("");
    }

    @FXML
    private void handleHelpButton() {
        List<List<Integer>> emptyCells = new ArrayList<>();

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                if (sudokuBoard.getNumberAt(row, col) == 0) {
                    emptyCells.add(Arrays.asList(row, col));
                }
            }
        }

        Collections.shuffle(emptyCells);
        for (List<Integer> cell : emptyCells) {
            int row = cell.get(0);
            int col = cell.get(1);

            List<Integer> candidates = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
            Collections.shuffle(candidates);
            for (int num : candidates) {
                if (sudokuBoard.isSafe(row, col, num)) {
                    sudokuBoard.setNumberAt(row, col, num);

                    for (javafx.scene.Node node : boardGridPane.getChildren()) {
                        if (node instanceof TextField tf) {
                            Integer r = GridPane.getRowIndex(tf);
                            Integer c = GridPane.getColumnIndex(tf);
                            if (r == null) r = 0;
                            if (c == null) c = 0;
                            if (r == row && c == col) {
                                tf.setText(String.valueOf(num));
                                tf.setDisable(true);
                                tf.setStyle(getBorderStyle(r, c) + "-fx-background-color: lightgreen;");

                                remainingHelps--;
                                helpLabel.setText("¿Atascado? Aquí tienes " + remainingHelps + " ayudas.  --> ");

                                if (remainingHelps == 0) {
                                    helpButton.setDisable(true);
                                    helpLabel.setText("Has gastado todas las ayudas.");
                                }

                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    @FXML
    private void handleInstructionsButton(javafx.event.ActionEvent event) {
        IButtonAction action = new ButtonActionAdapter.InstructionsButtonAction();
        try {
            action.execute(event);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }
    /*
    private void handleInstructionsButton() {
        String instrucciones = """
            🎯 Objetivo:
            Completa la cuadrícula 6x6 con los números del 1 al 6.

            ✅ Reglas:
            - No puede repetirse ningún número en la misma fila.
            - No puede repetirse ningún número en la misma columna.
            - No puede repetirse ningún número en el mismo bloque 2x3.

            🧠 Consejo:
            Usa la lógica, no adivinanzas. ¡Y diviértete!
            """;
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Instrucciones");
        alert.setHeaderText("Cómo jugar al Sudoku 6x6");
        alert.setContentText(instrucciones);
        alert.showAndWait();
    }*/

    private void updateErrorLabel() {
        StringBuilder allErrors = new StringBuilder();

        for(Map.Entry<String, String> entry : errorMap.entrySet()) {
            allErrors.append(entry.getValue()).append("\n");
        }

        errorLabel.setText(allErrors.toString().trim());
    }

    private void moveToNextEmptyField(TextField currentField) {
        int currentIndex = editableFields.indexOf(currentField);

        for(int i = currentIndex + 1; i < editableFields.size(); i++) {
            TextField next = editableFields.get(i);
            if (next.getText().isEmpty()) {
                next.requestFocus();
                return;
            }
        }

        for(int i = 0; i < currentIndex; i++) {
            TextField next = editableFields.get(i);
            if(next.getText().isEmpty()) {
                next.requestFocus();
                return;
            }
        }
    }

    @FXML
    private void handleSolveButton() {
        if (sudokuBoard.solve()) {
            for (javafx.scene.Node node : boardGridPane.getChildren()) {
                if (node instanceof TextField tf) {
                    Integer r = GridPane.getRowIndex(tf);
                    Integer c = GridPane.getColumnIndex(tf);
                    if (r == null) r = 0;
                    if (c == null) c = 0;

                    tf.setText(String.valueOf(sudokuBoard.getNumberAt(r, c)));
                    tf.setDisable(true);
                    tf.setStyle(getBorderStyle(r, c) + "-fx-background-color: lightblue;");
                }
            }
        } else {
            System.out.println("No se pudo resolver el tablero.");
        }
    }

    private String getBorderStyle(int row, int col) {
        StringBuilder style = new StringBuilder("-fx-border-color: #4A64F5; -fx-border-width:");

        // Grosor por lado: top, right, bottom, left
        style.append((row % 2 == 0 ? 2 : 1)).append("px ")
                .append((col % 3 == 2 ? 2 : 1)).append("px ")
                .append((row % 2 == 1 ? 2 : 1)).append("px ")
                .append((col % 3 == 0 ? 2 : 1)).append("px; ");

        return style.toString();
    }
}

