package sudoku;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public GridPane grid;
    private Puzzle puzzle;
    private final int SIZE = 9;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        puzzle = new Puzzle();
        initializeGrid();
    }

    private void initializeGrid() {
        Button cellButton;
        int[][] board = puzzle.getPuzzleArray();
        boolean[][] inputCells = puzzle.getInputCells();
        for(int r=0; r<SIZE; r++) {
            for(int c=0; c<SIZE; c++) {
                cellButton = (Button) grid.getChildren().get(r*SIZE+c);     //"r*SIZE+c" computes cell index
                if(!inputCells[r][c])
                    cellButton.setText(String.valueOf(board[r][c]));
                else
                    cellButton.setText("0");
            }
        }
    }

}
