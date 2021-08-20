package sudoku;

import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public GridPane grid;
    private Puzzle puzzle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        puzzle = new Puzzle();
        initializeGrid();
    }

    private void initializeGrid() {
        
    }

}
