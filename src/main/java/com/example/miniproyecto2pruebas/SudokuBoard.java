package com.example.miniproyecto2pruebas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SudokuBoard {
    private ArrayList<ArrayList<Integer>> board;

    public SudokuBoard() {
        board = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            ArrayList<Integer> column = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0));
            board.add(column);
        }
    }

    public void fillRandomTwoNumbersPerBlock() {
        int[][] blocks = {
                {0, 1, 0, 2}, // Bloque 0: filas 0-1, columnas 0-2
                {0, 1, 3, 5}, // Bloque 1: filas 0-1, columnas 3-5
                {2, 3, 0, 2}, // Bloque 2: filas 2-3, columnas 0-2
                {2, 3, 3, 5}, // Bloque 3: filas 2-3, columnas 3-5
                {4, 5, 0, 2}, // Bloque 4: filas 4-5, columnas 0-2
                {4, 5, 3, 5}  // Bloque 5: filas 4-5, columnas 3-5
        };

        for (int[] block : blocks) {
            int rowStart = block[0];
            int rowEnd = block[1];
            int colStart = block[2];
            int colEnd = block[3];

            List<int[]> cells = new ArrayList<>();
            for (int r = rowStart; r <= rowEnd; r++) {
                for (int c = colStart; c <= colEnd; c++) {
                    cells.add(new int[]{r, c});
                }
            }

            // Mezclamos las celdas para colocar los dos números en posiciones aleatorias
            Collections.shuffle(cells);
            int placedCount = 0;

            for (int[] cell : cells) {
                int row = cell[0];
                int col = cell[1];

                // Si ya pusimos 2 números en este bloque, salimos
                if (placedCount >= 2) break;

                List<Integer> candidates = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
                Collections.shuffle(candidates);

                for (int num : candidates) {
                    if (isSafe(row, col, num)) {
                        board.get(col).set(row, num);  // <- Insertamos en board[column][row]
                        placedCount++;
                        break;
                    }
                }
            }
        }
    }

    public int getNumberAt(int row, int col) {
        return board.get(col).get(row);
    }

    public void setNumberAt(int row, int col, int num) {
        board.get(col).set(row, num);
    }

    public boolean isSafe(int row, int col, int num) {
        // Verificar fila
        for (int c = 0; c < 6; c++) {
            if (board.get(c).get(row) == num) {
                return false;
            }
        }

        // Verificar columna
        for (int r = 0; r < 6; r++) {
            if (board.get(col).get(r) == num) {
                return false;
            }
        }

        // Verificar bloque 2x3
        int blockRowStart = (row / 2) * 2; // inicio de fila del bloque
        int blockColStart = (col / 3) * 3; // inicio de columna del bloque

        for (int r = blockRowStart; r < blockRowStart + 2; r++) {
            for (int c = blockColStart; c < blockColStart + 3; c++) {
                if (board.get(c).get(r) == num) {
                    return false;
                }
            }
        }

        // Si pasó todas las validaciones, está permitido
        return true;
    }

    public void printBoard() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                System.out.print(board.get(col).get(row) + " ");
            }
            System.out.println();
        }
    }
}