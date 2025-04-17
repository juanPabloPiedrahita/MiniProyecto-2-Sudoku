package com.example.miniproyecto2pruebas.controller;

import com.example.miniproyecto2pruebas.model.SudokuBoard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import java.io.IOException;
import java.util.*;

/**
 * Controller class for the Sudoku game. Manages the game board, user input, error handling, and victory conditions.
 *
 * @author David Taborda and Juan Pablo Piedrahita.
 * @version 3.0
 * @since 2.0
 */
public class SudokuController{

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

    // The board for the Sudoku game.
    private SudokuBoard sudokuBoard;
    // The number of available help options.
    private int remainingHelps = 12;
    // List of editable TextFields for the game.
    private List<TextField> editableFields = new ArrayList<>();
    // Map to track errors for each cell.
    private Map<String, String> errorMap = new HashMap<>();

    /**
     * Initializes the Sudoku board, populates the GridPane with TextFields, and sets up the game board.
     * Also checks if the board has a solution and handles user input validation.
     */
    @FXML
    public void initialize() {
        sudokuBoard = new SudokuBoard();
        // Fills the board with two random numbers per block.
        sudokuBoard.fillRandomTwoNumbersPerBlock();
        // Prints the board for debugging purposes.
        sudokuBoard.printBoard();

        // Check if the Sudoku board has a solution.
        if(sudokuBoard.solve()) {
            System.out.println("El tablero tiene solución.");
        } else {
            System.out.println("El tablero no tiene solución.");
        }

        // Initialize the game board with TextFields.
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                // Create a new TextField for each cell in the board.
                TextField tf = new TextField();
                // Set the preferred size for the TextField.
                tf.setPrefSize(50, 50);
                // Align text to the center of the TextField.
                tf.setAlignment(Pos.CENTER);
                // Set the border style for the cell.
                tf.setStyle(getBorderStyle(row, col));

                // Get the value for the cell from the Sudoku board.
                int value = sudokuBoard.getNumberAt(row, col);
                // Get the base style for the cell.
                String baseStyle = getBorderStyle(row, col);
                // If the cell already has a value.
                if (value != 0) {
                    // Set the value in the TextField.
                    tf.setText(String.valueOf(value));
                    // Disable the TextField (cannot be edited).
                    tf.setDisable(true);
                    // Style the cell as a filled one.
                    tf.setStyle(baseStyle + "-fx-background-color: lightgray; -fx-font-weight: bold;");
                } else {
                    final int r = row, c = col;
                    // Set the style for the empty cell.
                    tf.setStyle(baseStyle);
                    // Add the cell to the list of editable fields.
                    editableFields.add(tf);

                    // Add a key released event listener for user input.
                    tf.setOnKeyReleased(event  -> {
                        // Get the current text in the TextField.
                        String newVal = tf.getText();

                        // If the user types more than one character, keep only the first one.
                        if(newVal.length() > 1){
                            tf.setText(newVal.substring(0, 1));
                            System.out.println("El número " + newVal + " es válido.");
                            return;
                        }

                        // If the input is not a valid number between 1 and 6, clear the text.
                        if (!newVal.matches("[1-6]?")) {
                            tf.setText("");
                            System.out.println("El número " + newVal + " es inválido.");
                        } else if (!newVal.isEmpty()) {
                            // Convert the input to an integer.
                            int num = Integer.parseInt(newVal);

                            // Check if the number is valid in the current row, column, and block.
                            boolean inRow = !sudokuBoard.isRowValid(r, c, num);
                            boolean inCol = !sudokuBoard.isColumnValid(r, c, num);
                            boolean inBlock = !sudokuBoard.isBlockValid(r, c, num);

                            // If the number is safe and does not violate Sudoku rules, set the number.
                            if (sudokuBoard.isSafe(r, c, num) && !inRow && !inCol && !inBlock) {
                                sudokuBoard.setNumberAt(r, c, num);
                                // Set the style for a valid number.
                                tf.setStyle(baseStyle);
                                // Remove any error for the cell.
                                errorMap.remove(r + "," + c);
                                updateErrorLabel();
                                moveToNextEmptyField(tf);
                                System.out.println("El número " + newVal + " ha sido agregado.");
                                sudokuBoard.printBoard();
                            } else {
                                // If the number is invalid, style the cell with a red background.
                                tf.setStyle(baseStyle + "-fx-background-color: #F5A9A9;");

                                // Generate an error message describing the issue.
                                StringBuilder message = new StringBuilder("Error: el número ");
                                message.append(num).append(" ya está en esta parte: ");

                                List<String> parts = new ArrayList<>();
                                if(inRow) parts.add("la fila " + (r+1) + "; ");
                                if(inCol) parts.add("la columna " + (c+1) + "; ");
                                if(inBlock) parts.add("el bloque.");

                                message.append(String.join(" ", parts));
                                errorMap.put(r + "," + c, message.toString());
                                updateErrorLabel();
                                System.out.println(message);
                            }
                        } else {
                            // If the user clears the input, reset the cell.
                            sudokuBoard.setNumberAt(r, c, 0);
                            tf.setStyle(getBorderStyle(r, c)); // vacío
                            errorMap.remove(r + "," + c);
                            updateErrorLabel();
                            System.out.println("El número " + newVal + " ha sido eliminado.");
                        }
                    });
                }

                // Add the TextField to the boardGridPane at the specified position.
                boardGridPane.add(tf, col, row);
                // Check if the Sudoku is solved and show a victory window if it is.
                if(isSudokuSolved()) {
                    showVictoryWindow();
                }
            }
        }

        // Set the initial help label text.
        helpLabel.setText("¿Atascado? Aquí tienes " + remainingHelps + " ayudas.  --> ");
        errorLabel.setText("");
    }

    /**
     * Handles the event when the help button is pressed.
     * Randomly selects an empty cell and fills it with a valid number.
     * The help count is decremented and the help button is disabled when no more helps are available.
     */
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
                                System.out.println("El botón de ayuda ha sido usado. Se ha puesto el número " + num + ".");

                                if (remainingHelps == 0) {
                                    helpButton.setDisable(true);
                                    helpLabel.setText("Has gastado todas las ayudas.");
                                    System.out.println("Se han gastado todas las ayudas.");
                                }

                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Handles the event when the instructions button is pressed.
     * Executes the action to display the game instructions.
     *
     * @param event The ActionEvent triggered by pressing the instructions button.
     * @throws IOException If there is an issue loading the instructions.
     *
     * @author Juan Pablo Piedrahita.
     * @since 3.0
     */
    @FXML
    private void handleInstructionsButton(ActionEvent event) throws IOException {
        IButtonAction action = new ButtonActionAdapter.InstructionsButtonAction();
            action.execute(event);
    }

    /**
     * Checks if the Sudoku puzzle has been solved.
     * A puzzle is solved if all cells are filled with valid numbers.
     *
     * @return true if the puzzle is solved, false otherwise.
     */
    private boolean isSudokuSolved() {
        for(int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                int value = sudokuBoard.getNumberAt(row, col);
                if (value == 0 || !sudokuBoard.isSafe(row, col, value)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Displays a victory window when the player completes the Sudoku puzzle.
     * The window shows a congratulatory message and a button to close the window.
     *
     * @author Juan Pablo Piedrahita.
     * @since 3.0
     */
    private void showVictoryWindow() {
        Stage victoryStage = new Stage();
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        Label result = new Label("¡Felicidades, has completado el sudoku!");
        result.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        result.setTextFill(Color.GREEN);

        Button closeButton = new Button("Cerrar");
        closeButton.setOnAction(e -> victoryStage.close());
        closeButton.setStyle("-fx-background-color: lightgreen;");

        root.getChildren().addAll(result, closeButton);
        Scene scene = new Scene(root, 300, 150);
        victoryStage.setScene(scene);
        victoryStage.setTitle("Victoria");
        victoryStage.show();
    }

    /**
     * Updates the error label with all the error messages stored in the errorMap.
     * Each error is displayed on a new line in the label.
     */
    private void updateErrorLabel() {
        StringBuilder allErrors = new StringBuilder();

        for(Map.Entry<String, String> entry : errorMap.entrySet()) {
            allErrors.append(entry.getValue()).append("\n");
        }

        errorLabel.setText(allErrors.toString().trim());
    }

    /**
     * Moves the focus to the next empty editable field in the Sudoku board.
     * The next empty field is found by checking the TextField list.
     * If no empty fields are found after the current one, it wraps around and starts from the beginning.
     *
     * @param currentField The current TextField that the user has entered a number into.
     */
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

    /**
     * Generates a custom border style for the TextField based on its position on the board.
     * The border style varies depending on the row and column, with thicker borders on certain sides.
     *
     * @param row The row index of the TextField.
     * @param col The column index of the TextField.
     * @return A string representing the CSS style for the border of the TextField.
     */
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

