package sudoku;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class functions as the logical connection between front-end and back-end
 *
 * @author Youmeng Hin
 * @version 21.08.20
 */
public class Controller implements Initializable {
    public GridPane gridBoard;
    public GridPane gridPad;
    private Puzzle puzzle;
    private int[][] board;
    private final int SIZE = 9;

    /**
     * Initializes front-end and back-end connection
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        puzzle = new Puzzle();
        board = puzzle.getPuzzleArray();
        initializeGrid();
    }

    /**
     * Sets up the 9x9 GridPane to show correct digits representing the puzzle board
     * Functions as a helper method for initialize()
     */
    private void initializeGrid() {

        Button cellButton;
        EventHandler<ActionEvent> event;

        boolean[][] inputCells = puzzle.getInputCells();

        for(int r=0; r<SIZE; r++) {
            for(int c=0; c<SIZE; c++) {

                cellButton = (Button) gridBoard.getChildren().get(r*SIZE+c);     //"r*SIZE+c" computes cell index
                if(!inputCells[r][c])
                    cellButton.setText(String.valueOf(board[r][c]));
                else
                    cellButton.setText("0");

                event = new CellEventHandler(cellButton);

                cellButton.setOnAction(event);
            }
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
            int r = (gridBoard.getRowIndex(b) == null) ? 0 : gridBoard.getRowIndex(b);
            int c = (gridBoard.getColumnIndex(b) == null) ? 0 : gridBoard.getColumnIndex(b);

            System.out.println("Selected Cell\t" + r + " " + c);

//            //adds the user's guessing number to the puzzle
//            puzzle.addGuess(r, c, input);
//
//            if(GameLogic.checkCompletion(puzzle.getPuzzleArray())) {
//                Alert over = new Alert(Alert.AlertType.INFORMATION, "PUZZLE COMPLETED!");
//            }

        }
    }

}
