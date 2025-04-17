package com.example.miniproyecto2pruebas.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The SudokuBoard class represents the game board of a Sudoku puzzle.
 * It provides methods for manipulating and solving the Sudoku puzzle.
 *
 * @author Juan Pablo Piedrahita and David Taborda.
 * @version 3.0
 * @since 1.0
 */
public class SudokuBoard {
    // 2D board representation using ArrayList
    private ArrayList<ArrayList<Integer>> board;

    /**
     * Constructor that initializes an empty 6x6 Sudoku board with all cells set to 0.
     */
    public SudokuBoard() {
        board = new ArrayList<>();
        for (int row = 0; row < 6; row++) {
            ArrayList<Integer> rowList = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0));
            board.add(rowList);
        }
    }

    /**
     * Inner static class representing a cell in the Sudoku board.
     * It contains the row and column position of the cell.
     */
    private static class Cell {
        int row;
        int col;

        Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    /**
     * Fills two random cells in each 2x3 block with numbers from 1 to 6.
     * The numbers are placed in such a way that no two numbers repeat in the row, column, or block.
     */
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

    /**
     * Gets the number at a specific position in the board.
     *
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @return The number at the specified position.
     */
    public int getNumberAt(int row, int col) {
        return board.get(row).get(col);
    }

    /**
     * Sets a number at a specific position in the board.
     *
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @param num The number to set at the specified position.
     */
    public void setNumberAt(int row, int col, int num) {
        board.get(row).set(col, num);
    }

    /**
     * Checks if it is safe to place a number at the given row and column position.
     * It ensures that the number does not already exist in the row, column, or 2x3 block.
     *
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @param num The number to check.
     * @return true if it is safe to place the number, false otherwise.
     */
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

    /**
     * Prints the current state of the Sudoku board to the console.
     */
    public void printBoard() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                System.out.print(board.get(row).get(col) + " ");
            }
            System.out.println();
        }
    }

    /**
     * Solves the Sudoku board using a backtracking algorithm.
     *
     * @return true if the board can be solved, false otherwise.
     */
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

    /**
     * Checks if placing a number in the given row is valid by ensuring the number does not repeat in the row.
     *
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @param num The number to check.
     * @return true if the number is valid for the row, false otherwise.
     */
    public boolean isRowValid(int row, int col, int num) {
        for (int c = 0; c < 6; c++) {
            if (c != col && board.get(row).get(c) == num) return false;
        }
        return true;
    }

    /**
     * Checks if placing a number in the given column is valid by ensuring the number does not repeat in the column.
     *
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @param num The number to check.
     * @return true if the number is valid for the column, false otherwise.
     */
    public boolean isColumnValid(int row, int col, int num) {
        for (int r = 0; r < 6; r++) {
            if (r != row && board.get(r).get(col) == num) return false;
        }
        return true;
    }

    /**
     * Checks if placing a number in the given 2x3 block is valid by ensuring the number does not repeat in the block.
     *
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @param num The number to check.
     * @return true if the number is valid for the block, false otherwise.
     */
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