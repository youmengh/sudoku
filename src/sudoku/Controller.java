package sudoku;

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
        initializeGrid();
    }

    /**
     * Sets up the 9x9 GridPane to show correct digits representing the puzzle board
     * Functions as a helper method for initialize()
     */
    private void initializeGrid() {
        Button cellButton;
        int[][] board = puzzle.getPuzzleArray();
        boolean[][] inputCells = puzzle.getInputCells();
        for(int r=0; r<SIZE; r++) {
            for(int c=0; c<SIZE; c++) {
                cellButton = (Button) gridBoard.getChildren().get(r*SIZE+c);     //"r*SIZE+c" computes cell index
                if(!inputCells[r][c])
                    cellButton.setText(String.valueOf(board[r][c]));
                else
                    cellButton.setText("0");
            }
        }
    }

}
