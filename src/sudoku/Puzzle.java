package sudoku;

/**
 * @author Youmeng Hin
 * @version 21.08.04
 */
public class Puzzle {
    private int[][] board;          //digits on puzzle board
    private boolean[][] inputCells; //where on the board inputs are allowed (true = allowed)
    private final int SIZE = 9;     //size constant used to contruct and work with puzzle

    public Puzzle(){
        board = new int[SIZE][SIZE];
        inputCells = new boolean[SIZE][SIZE];
    }
}
