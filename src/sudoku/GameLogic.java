package sudoku;

/**
 * Manages the logical component of the game by validating puzzles and checking for completion
 */
public class GameLogic {

//    public static boolean checkCompletion(int[][] board) {
//        if(checkRows(board) && checkCols(board) && checkSquares(board))
//            return true;
//        return false;
//    }

    public static boolean checkRows(int[][] board) {
        for(int r = 0; r < board.length; r++) {
            if(checkDuplicates(board[r]))
                return false;
        }
        return true;
    }
}
