package sudoku;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class functions as the logical connection between front-end and back-end
 *
 * @author Youmeng Hin
 * @version 21.09.01
 */
public class Controller implements Initializable {

    @FXML
    public GridPane gridBoard;
    @FXML
    public GridPane gridPad;
    @FXML
    private Puzzle puzzle;

    //grid-related variables
    private int[][] board;          //user board of the puzzle
    private int[][] solution;       //solution board of the puzzle
    private boolean[][] inputCells; //array showing locations of input cells

    //other variables
    private final int SIZE = 9;
    private int inputKey;
    private int r, c;               //general row and column variables used to store as a coordinate
    private int selectedCell;       //stores the index of child on gridBoard

    /**
     * Initializes front-end and back-end connection
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        puzzle = new Puzzle();
        initializeGrid();
        initializeKeyPad();
    }

    /**
     * Sets up keypad
     * Implements action handlers to buttons
     * Functions as a helper method for initialize()
     */
    private void initializeKeyPad() {

        Button keyButton;
        EventHandler<ActionEvent> event;

        for(int r=0; r<gridPad.getRowCount(); r++) {
            for (int c=0; c<gridPad.getColumnCount(); c++) {
                keyButton = (Button) gridPad.getChildren().get(r*gridPad.getRowCount()+c);   //r*gridPad.getRowCount()+c computes the index
                event = new KeyEventHandler(keyButton);
                keyButton.setOnAction(event);
            }
        }

    }

    /**
     * Sets up the 9x9 GridPane to show correct digits representing the puzzle board
     * Implements action handlers to buttons
     * Functions as a helper method for initialize()
     */
    private void initializeGrid() {

        board = puzzle.getUserBoard();
        solution = puzzle.getSolution();
        inputCells = puzzle.getInputCells();

        Button cellButton;
        EventHandler<ActionEvent> event;

        for(int r=0; r<SIZE; r++) {
            for(int c=0; c<SIZE; c++) {

                cellButton = (Button) gridBoard.getChildren().get(r*SIZE+c);     //"r*SIZE+c" computes cell index
                cellButton.setText(String.valueOf(board[r][c]));

                event = new CellEventHandler(cellButton);

                cellButton.setOnAction(event);
            }
        }
    }

    /**
     * A private helper class that handles the logic behind each keypad button
     */
    private class KeyEventHandler implements EventHandler<ActionEvent> {

        private final Button b;
        private Button temp;    //temporary button for changing value of a cell

        /**
         * Constructor
         *
         * @param b - the keypad button being passed
         */
        public KeyEventHandler(Button b) {
            this.b = b;
        }

        @Override
        public void handle(ActionEvent actionEvent) {

            inputKey = Integer.valueOf(b.getText());

            //allows for cell value edit if cell location allows for take inputs
            if(inputCells[r][c]) {
                temp = (Button) gridBoard.getChildren().get(selectedCell);
                temp.setText(String.valueOf(inputKey));
            }
            else {
                System.out.println("You can't edit this cell");
            }
            //puzzle.addGuess(r, c, inputKey);

            System.out.println("Selected Number: " + inputKey);

        }
    }

    /**
     * A private helper class that handles the logic behind each button on puzzle board
     */
    private class CellEventHandler implements EventHandler<ActionEvent> {

        private final Button b;

        /**
         * Constructor
         *
         * @param b - the cell button being passed
         */
        public CellEventHandler(Button b) {
            this.b = b;
        }

        /**
         * Implements the functionality when the button is clicked
         *
         * @param e - button's event being passed
         */
        @Override
        public void handle(ActionEvent e) {

            //store the row and column indices in r and c
            r = (gridBoard.getRowIndex(b) == null) ? 0 : gridBoard.getRowIndex(b);
            c = (gridBoard.getColumnIndex(b) == null) ? 0 : gridBoard.getColumnIndex(b);
            selectedCell = r*SIZE+c;

            System.out.println("Selected Cell:\t" + r + " " + c);

//            //adds the user's guessing number to the puzzle
//            puzzle.addGuess(r, c, input);
//
//            if(GameLogic.checkCompletion(puzzle.getPuzzleArray())) {
//                Alert over = new Alert(Alert.AlertType.INFORMATION, "PUZZLE COMPLETED!");
//            }

        }
    }

}
