package sudoku;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
    public GridPane gridPad;
    public GridPane gridOptions;
    private Puzzle puzzle;

    //grid-related variables
    private int[][] board;          //2D int array of the puzzle solution
    private int[][] userBoard;      //2D int array of the displayed puzzle board
    private boolean[][] inputCells; //array showing locations of input cells

    //Game option buttons
    @FXML
    private Button checkPuzzle;
    public Button newPuzzle;
    public Button reset;
    public Button quit;

    //other variables
    private final int SIZE = 9;
    private int inputKey;
    private int r, c;               //general row and column variables used to store as the selected coordinate
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
        btnNewPuzzle();
    }

    /**
     * Sets up the 9x9 GridPane to show correct digits representing the puzzle board
     * Implements action handlers to buttons
     * Functions as a helper method for initialize()
     */
    private void initializeGrid() {

//        board = new int[SIZE][SIZE];
        userBoard = new int[SIZE][SIZE];

        board = puzzle.getPuzzleArray();        //gets the solution array
        inputCells = puzzle.getInputCells();    //gets the input cell locations array

        Button cellButton;
        EventHandler<ActionEvent> event;

        for(int r=0; r<SIZE; r++) {
            for(int c=0; c<SIZE; c++) {

                cellButton = (Button) gridBoard.getChildren().get(r*SIZE+c);     //"r*SIZE+c" computes cell index

                if(!inputCells[r][c]) {
                    userBoard[r][c] = board[r][c];
                    cellButton.setText(String.valueOf(board[r][c]));
                    cellButton.setStyle("-fx-background-color: #fff");
                } else {
                    selectedCell = r * SIZE + c;        //fixes initial color bug for non-input-cell cell (0,0)
                    userBoard[r][c] = 0;
                    cellButton.setText("0");
                    cellButton.setStyle("-fx-background-color: #d3d3d3");
                }

                event = new CellEventHandler(cellButton);

                cellButton.setOnAction(event);
            }
        }

        //for debugging purpose
        System.out.println("Solution:\n" + GameLogic.printBoard(board));
        System.out.println("User's Board:\n" + GameLogic.printBoard(userBoard));
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

    private void initializeGameOptions() {

        EventHandler<ActionEvent> event;

        checkPuzzle = (Button)gridOptions.getChildren().get(0);
        newPuzzle = (Button)gridOptions.getChildren().get(1);
        reset = (Button)gridOptions.getChildren().get(2);

        event = new GameOptionHandler();
        checkPuzzle.setOnAction(event);
        newPuzzle.setOnAction(event);
        reset.setOnAction(event);

    }

    /**
     * A private helper class that handles the logic behind each button on puzzle board
     */
    private class CellEventHandler implements EventHandler<ActionEvent> {

        private final Button b;
        private Button prevBtn;     //previous button

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

            prevBtn = (Button)gridBoard.getChildren().get(selectedCell); //using selectedCell to reference to the index of the previous button
            prevBtn.setStyle("-fx-background-color: #d3d3d3");

            //store the row and column indices in r and c
            r = (gridBoard.getRowIndex(b) == null) ? 0 : gridBoard.getRowIndex(b);
            c = (gridBoard.getColumnIndex(b) == null) ? 0 : gridBoard.getColumnIndex(b);

            //if input is allowed for current cell then update selectedCell value
            //and highlight the current selected cell
            if(inputCells[r][c]) {
                selectedCell = r * SIZE + c;    //update index to current button
                b.setStyle("-fx-background-color: yellow"); //highlight current button

                //for debugging purpose
                System.out.println("Selected Cell:\t" + r + " " + c);
            } else {
                //for debugging purpose
                System.out.println("Cell clicked:\t" + r + " " + c + "\nCell edit not allowed... :(");
            }

        }
    }

    /**
     * A private helper class that handles the logic behind each keypad button
     */
    private class KeyEventHandler implements EventHandler<ActionEvent> {

        private final Button b;
        private Button temp;    //temporary button for changing value of a cell on gridBoard

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

            //if inputCells returns true stores inputKey value in current cell location
            //otherwise send "Unable to edit current cell" prompt and do nothing
            if(inputCells[r][c]) {

                userBoard[r][c] = inputKey;
                System.out.println(GameLogic.printBoard(userBoard));
                temp = (Button) gridBoard.getChildren().get(selectedCell);
                temp.setText(String.valueOf(inputKey));

            }
            else {

                //for debugging purpose
                System.out.println("Unable to edit current cell");
                System.out.println("Selected Number: " + inputKey);
                System.out.println(GameLogic.printBoard(userBoard));

            }


        }
    }

    /**
     * A private helper class that handles the logic behind each game option button
     */
    private class GameOptionHandler implements EventHandler<ActionEvent> {

        public GameOptionHandler() {}

        @Override
        public void handle(ActionEvent actionEvent) {
            if(actionEvent.getSource() == checkPuzzle) {

                btnCheckPuzzle();

            } else if (actionEvent.getSource() == newPuzzle) {

                System.out.println("New puzzle button pressed");        // for debugging purpose
                btnNewPuzzle();

                //for debugging purpose
                System.out.println("New Solution\n" + GameLogic.printBoard(board));
                System.out.println();
                System.out.println("New User's Board\n" + GameLogic.printBoard(userBoard));

            } else if (actionEvent.getSource() == reset) {

                System.out.println("Puzzle reset");       //for debugging purpose
                btnReset();

            }
        }

    }

    /**
     * Helper method - used when "Check Puzzle" button is clicked
     * Checks whether the puzzle is completed and sends the appropriate alert
     */
    private void btnCheckPuzzle() {
        if(GameLogic.comparePuzzleBoard(userBoard, board)) {
            System.out.println("CONGRATULATIONS, YOU SOLVED THE PUZZLE!");      //for debugging purpose
            Alert over = new Alert(Alert.AlertType.CONFIRMATION, "CONGRATULATIONS, YOU SOLVED THE PUZZLE!");
            over.setTitle("Sudoku (v21.09.13) by Youmeng Hin");
            over.setHeaderText("Result:");
            over.showAndWait();
        } else {
            System.out.println("PUZZLE IS INCOMPLETE :(");
            Alert notOver = new Alert(Alert.AlertType.CONFIRMATION, "PUZZLE IS INCOMPLETE :(");     //for debugging purpose
            notOver.setTitle("Sudoku (v21.09.13) by Youmeng Hin");
            notOver.setHeaderText("Result:");
            notOver.showAndWait();
        }
    }

    /**
     * Helper method - used when "New Puzzle" button is clicked
     * Initialize variables and virtual and non-virtual components of the puzzle
     */
    public void btnNewPuzzle() {
        //reset miscellaneous variables
        inputKey = 0;
        r = 0;
        c = 0;
        selectedCell = 0;

        //reinitialize everything
        puzzle.createNew();
        initializeGrid();
        initializeKeyPad();
        initializeGameOptions();
    }

    /**
     * Helper method - used when "Reset" button is clicked
     */
    public void btnReset() {
        initializeGrid();
    }

}
