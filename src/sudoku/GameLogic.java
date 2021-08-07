package sudoku;

import java.util.HashSet;
import java.util.Set;

/**
 * Manages the logical component of the game by validating puzzles and checking for completion
 */
public class GameLogic {

//    public static boolean checkCompletion(int[][] board) {
//        if
//        (
//                checkZeros(board) &&
//                checkRows(board) &&
//                checkCols(board) &&
//                checkSquares(board)
//        )
//            return true;
//        return false;
//    }

    /**
     * Determines if every sub-grid in the puzzle board is valid
     * @param board An array representing the puzzle grid
     * @return True if no duplicates found in all sub-grids false otherwise
     */
    public static boolean checkSquares(int[][] board) {
        boolean duplicateFound = false;
        for(int r = 0; r < board.length; r+=3) {
            for(int c = 0; c < board.length; c+=3) {
                if(!checkOneSquare(board, r, c))   //false if checkSquare() detects a duplicate in one of the sub-grids
                    return false;
            }
        }
        return true;
    }

    /**
     * Determines if one single sub-grid is valid
     * Mainly used as a helper method for checkSquares
     * @param board An array representing the puzzle grid
     * @param startRow The starting row of a sub-grid on the board
     * @param startColumn The starting column of a sub-grid on the board
     * @return True if no duplicates found in all sub-grids false otherwise
     */
    public static boolean checkOneSquare( int[][] board, int startRow, int startColumn) {
        int[] temp = new int[9];
        int tempIndex = 0;
        for(int r = startRow; r < startRow + 3; r++) {
            for(int c = startColumn; c < startColumn + 3; c++) {
                temp[tempIndex] = board[r][c];
                tempIndex++;
            }
            if(checkDuplicates(temp))
                return false;
        }
        return true;
    }

    /**
     * Determins if every column in the puzzle board is valid
     * @param board An array representing the puzzle grid
     * @return True if no duplicates found in all columns false otherwise
     */
    public static boolean checkCols(int[][] board) {
        int[] temp;
        //copies every column to a temporary array and check for duplicates
        for(int c = 0; c < board.length; c++) {
            temp = new int[9];
            for(int r = 0; r < board.length; r++) {
                temp[r] = board[r][c];
            }
            if(checkDuplicates(temp))
                return false;
        }
        return true;
    }

    /**
     * Determines if every row in the puzzle board is valid
     * @param board An array representing the puzzle grid
     * @return True if no duplicates found in all rows false otherwise
     */
    public static boolean checkRows(int[][] board) {
        for(int r = 0; r < board.length; r++) {
            if(checkDuplicates(board[r]))
                return false;
        }
        return true;
    }

    /**
     * Takes in an array of 9 integers and determines if duplicates can be found
     * @param ints An array of 9 integers
     * @return True if duplicates are found false otherwise
     */
    public static boolean checkDuplicates(int[] ints) {
        Set set = new HashSet();
        for (int num : ints) {
            if (set.contains(num)) {
                return true;
            }
            if (num != 0) {
                set.add(num);
            }
        }
        return false;
    }


}
