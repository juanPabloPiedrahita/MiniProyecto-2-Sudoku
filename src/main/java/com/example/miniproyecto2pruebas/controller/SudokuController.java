package com.example.miniproyecto2pruebas.controller;

import com.example.miniproyecto2pruebas.model.SudokuBoard;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.*;

public class SudokuController {

    //public Button helpButton;
    @FXML
    private GridPane boardGridPane;

    @FXML
    private Label helpLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private Button helpButton;

    private SudokuBoard sudokuBoard;
    private int remainingHelps = 4;
    private List<TextField> editableFields = new ArrayList<>();

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
                if (value != 0) {
                    tf.setText(String.valueOf(value));
                    tf.setDisable(true);
                    tf.setStyle("-fx-background-color: lightgray; -fx-font-weight: bold;");
                } else {
                    final int r = row, c = col;
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
                                tf.setStyle(getBorderStyle(r, c)); // válido
                                errorLabel.setText("");
                                moveToNextEmptyField(tf);
                            } else {
                                tf.setStyle(getBorderStyle(r, c) + "-fx-background-color: pink;"); // inválido

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
                                errorLabel.setText(message.toString());
                            }
                        } else {
                            sudokuBoard.setNumberAt(r, c, 0);
                            tf.setStyle(getBorderStyle(r, c)); // vacío
                            errorLabel.setText("");
                        }
                    });
                }

                boardGridPane.add(tf, col, row);
            }
        }
        helpLabel.setText("¿Atascado? Aquí tienes " + remainingHelps + " ayudas.");
        errorLabel.setText("");
    }

    @FXML
    private void handleHelpButton() {
        //if(remainingHelps <= 0) return;
        //helpLabel.setText("¿Atascado? Aquí tienes X ayudas.");
        List<int[]> emptyCells = new ArrayList<>();

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                if (sudokuBoard.getNumberAt(row, col) == 0) {
                    emptyCells.add(new int[]{row, col});
                }
            }
        }

        Collections.shuffle(emptyCells);
        for (int[] cell : emptyCells) {
            int row = cell[0];
            int col = cell[1];

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
                                helpLabel.setText("¿Atascado? Aquí tienes " + remainingHelps + " ayudas.");

                                if(remainingHelps == 0){
                                    helpButton.setDisable(true);
                                    helpLabel.setText("Ya no hay ayudas.");
                                }

                                return;
                            }
                        }
                    }
                }
            }
        }
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
        String style = "-fx-border-color: black; -fx-border-width: ";
        style += (row % 2 == 0 ? "2 " : "1 ") + (col == 5 ? "2 " : "1 ") +
                ((row == 5) ? "2 " : "1 ") + (col % 3 == 0 ? "2;" : "1;");
        return style;
    }
}

