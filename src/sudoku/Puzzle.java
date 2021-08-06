package sudoku;

import java.util.Random;

/**
 * @author Youmeng Hin
 * @version 21.08.05
 */
public class Puzzle {
    private int[][] board;          //digits on puzzle board
    private boolean[][] inputCells; //where on the board inputs are allowed (true = allowed)
    private final int SIZE = 9;     //size constant used to contruct and work with puzzle

    /**
     * Constructor
     */
    public Puzzle(){
        board = generatePuzzle();
        inputCells = new boolean[SIZE][SIZE];
    }

    /**
     * Generates a new valid sudoku puzzle represented by a 2D int array
     * @version 21.08.05 - only generates a grid with random numbers
     *                   - no puzzle validation
     * @return A 2D int array representing the puzzle board
     */
    private int[][] generatePuzzle() {
        int[][] newBoard = new int[SIZE][SIZE];
        for(int x  = 0; x < SIZE; x++) {
            for(int y  = 0; y < SIZE; y++) {
                Random rand = new Random();
                newBoard[x][y] = rand.nextInt(9) + 1;
            }
        }
        return newBoard;
    }

    /**
     * A toString method for printing the puzzle grid in text format
     * @return A string representation of the grid
     */
    @Override
    public String toString(){
        String printedBoard = "";
        for(int x  = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                printedBoard += board[x][y] + "\t";
            }
            printedBoard += "\n";
        }
        return printedBoard;
    }

}
