package sudoku;

/**
 * @author Youmeng Hin
 * @version 21.08.04
 */
public class SudokuGame {
    private Puzzle puzzle;
    private boolean completed;

    /**
     * Constructor
     */
    public SudokuGame() {
        completed = false;
        puzzle = new Puzzle(false);
        run(puzzle);
    }

    
}
