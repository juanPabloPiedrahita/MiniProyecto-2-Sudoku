package com.example.miniproyecto2pruebas.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SudokuBoard {
    private ArrayList<ArrayList<Integer>> board;

    public SudokuBoard() {
        board = new ArrayList<>();
        for (int row = 0; row < 6; row++) {
            ArrayList<Integer> rowList = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0));
            board.add(rowList);
        }
    }

    // Clase interna que representa una celda
    private static class Cell {
        int row;
        int col;

        Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public void fillRandomTwoNumbersPerBlock() {
        for (int blockRow = 0; blockRow < 3; blockRow++) { // bloques verticales
            for (int blockCol = 0; blockCol < 2; blockCol++) { // bloques horizontales
                int rowStart = blockRow * 2;
                int colStart = blockCol * 3;

                List<Cell> cells = new ArrayList<>();
                for (int r = rowStart; r < rowStart + 2; r++) {
                    for (int c = colStart; c < colStart + 3; c++) {
                        cells.add(new Cell(r, c));
                    }
                }

                Collections.shuffle(cells);
                int placedCount = 0;

                for (Cell cell : cells) {
                    if (placedCount >= 2) break;

                    List<Integer> candidates = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
                    Collections.shuffle(candidates);

                    for (int num : candidates) {
                        if (isSafe(cell.row, cell.col, num)) {
                            board.get(cell.row).set(cell.col, num);
                            placedCount++;
                            break;
                        }
                    }
                }
            }
        }
    }

    public int getNumberAt(int row, int col) {
        return board.get(row).get(col);
    }

    public void setNumberAt(int row, int col, int num) {
        board.get(row).set(col, num);
    }

    public boolean isSafe(int row, int col, int num) {
        for (int c = 0; c < 6; c++) {
            if (board.get(row).get(c) == num) {
                return false;
            }
        }

        for (int r = 0; r < 6; r++) {
            if (board.get(r).get(col) == num) {
                return false;
            }
        }

        int blockRowStart = (row / 2) * 2;
        int blockColStart = (col / 3) * 3;

        for (int r = blockRowStart; r < blockRowStart + 2; r++) {
            for (int c = blockColStart; c < blockColStart + 3; c++) {
                if (board.get(r).get(c) == num) {
                    return false;
                }
            }
        }

        return true;
    }

    public void printBoard() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                System.out.print(board.get(row).get(col) + " ");
            }
            System.out.println();
        }
    }

    public boolean solve() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                if (getNumberAt(row, col) == 0) {
                    for (int num = 1; num <= 6; num++) {
                        if (isSafe(row, col, num)) {
                            setNumberAt(row, col, num);
                            if (solve()) {
                                return true;
                            }
                            setNumberAt(row, col, 0);
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isRowValid(int row, int col, int num) {
        for (int c = 0; c < 6; c++) {
            if (c != col && board.get(row).get(c) == num) return false;
        }
        return true;
    }

    public boolean isColumnValid(int row, int col, int num) {
        for (int r = 0; r < 6; r++) {
            if (r != row && board.get(r).get(col) == num) return false;
        }
        return true;
    }

    public boolean isBlockValid(int row, int col, int num) {
        int startRow = (row / 2) * 2;
        int startCol = (col / 3) * 3;
        for (int r = startRow; r < startRow + 2; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if ((r != row || c != col) && board.get(r).get(c) == num) return false;
            }
        }
        return true;
    }
}