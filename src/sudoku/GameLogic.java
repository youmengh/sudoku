package sudoku;

import java.util.HashSet;
import java.util.Set;

/**
 * Manages the logical component of the game by validating puzzles and checking for completion
 */
public class GameLogic {

//    public static boolean checkCompletion(int[][] board) {
//        if(checkRows(board) && checkCols(board) && checkSquares(board))
//            return true;
//        return false;
//    }

    /**
     * Checks whether every column in the puzzle board is valid
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
     * Checks whether every row in the puzzle board is valid
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
     * Takes in an array of 9 integers and check whether duplicates can be found
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
