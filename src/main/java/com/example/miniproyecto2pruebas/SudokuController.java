package com.example.miniproyecto2pruebas;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SudokuController {
    @FXML
    private GridPane gridPane;
    private SudokuBoard sudokuBoard = new SudokuBoard();
    private TextField[][] textFields = new TextField[6][6];

    @FXML
    public void initialize() {
        // Generar tablero con 2 números por bloque
        sudokuBoard.fillRandomTwoNumbersPerBlock();

        // Crear TextFields y añadirlos al GridPane
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                TextField tf = new TextField();
                tf.setPrefSize(50, 50);
                tf.setStyle(getBorderStyle(row, col));
                tf.setAlignment(Pos.CENTER);

                int value = sudokuBoard.getNumberAt(row, col);
                if (value != 0) {
                    tf.setText(String.valueOf(value));
                    tf.setDisable(true);
                } else {
                    final int r = row, c = col;
                    tf.textProperty().addListener((obs, oldVal, newVal) -> {
                        if (!newVal.matches("[1-6]?")) {
                            tf.setText(oldVal); // Solo permite 1-6
                        } else if (!newVal.isEmpty()) {
                            int num = Integer.parseInt(newVal);
                            if (sudokuBoard.isSafe(r, c, num)) {
                                sudokuBoard.setNumberAt(r, c, num);
                                tf.setStyle("-fx-background-color: white;");
                            } else {
                                tf.setStyle("-fx-background-color: pink;");
                            }
                        } else {
                            tf.setStyle("-fx-background-color: white;");
                            sudokuBoard.setNumberAt(r, c, 0); // limpiar celda
                        }
                    });
                }

                textFields[row][col] = tf;
                gridPane.add(tf, col, row);
            }
        }
    }

    @FXML
    private void handleHelpButton() {
        List<int[]> emptyCells = new ArrayList<>();

        // Buscar todas las celdas vacías
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                if (sudokuBoard.getNumberAt(row, col) == 0) {
                    emptyCells.add(new int[]{row, col});
                }
            }
        }

        // Si no hay celdas vacías, salir
        if (emptyCells.isEmpty()) return;

        // Mezclar las vacías y buscar una que pueda recibir un número válido
        Collections.shuffle(emptyCells);
        for (int[] cell : emptyCells) {
            int row = cell[0];
            int col = cell[1];

            List<Integer> candidates = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
            Collections.shuffle(candidates);
            for (int num : candidates) {
                if (sudokuBoard.isSafe(row, col, num)) {
                    sudokuBoard.setNumberAt(row, col, num);
                    textFields[row][col].setText(String.valueOf(num));
                    textFields[row][col].setDisable(true);
                    textFields[row][col].setStyle("-fx-background-color: lightgreen;");
                    return; // Completó una ayuda
                }
            }
        }
    }


    private String getBorderStyle(int row, int col) {
        StringBuilder style = new StringBuilder("-fx-border-color: black;");

        // Borde superior grueso para filas 0 y 2 y 4 (inicio de cada bloque)
        if (row == 0 || row == 2 || row == 4) {
            style.append("-fx-border-top-width: 2px;");
        } else {
            style.append("-fx-border-top-width: 1px;");
        }

        // Borde izquierdo grueso para columnas 0 y 3 (inicio de cada bloque)
        if (col == 0 || col == 3) {
            style.append("-fx-border-left-width: 2px;");
        } else {
            style.append("-fx-border-left-width: 1px;");
        }

        // Borde derecho para última columna o borde fino normal
        if (col == 5) {
            style.append("-fx-border-right-width: 2px;");
        } else {
            style.append("-fx-border-right-width: 0px;");
        }

        // Borde inferior para última fila
        if (row == 5) {
            style.append("-fx-border-bottom-width: 2px;");
        } else {
            style.append("-fx-border-bottom-width: 0px;");
        }

        return style.toString();
    }

    public void solveBoard() {
        if (sudokuBoard.solve()) {
            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 6; col++) {
                    int value = sudokuBoard.getNumberAt(row, col);
                    textFields[row][col].setText(String.valueOf(value));
                    textFields[row][col].setStyle("-fx-background-color: lightgreen;");
                }
            }
        } else {
            System.out.println("El tablero no tiene solución.");
        }
    }
}
