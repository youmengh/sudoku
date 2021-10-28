package sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Simulates a Sudoku puzzle board and relevant features
 *
 * @author Youmeng Hin
 * @version 21.10.28
 */
public class Puzzle {
    private int[][] board;       //digits on puzzle board
    private boolean[][] inputCells; //represent locations inputs are allowed on the puzzle board (true = allowed)
    //    private int[][] user;           //the puzzle board displayed to the user that can be altered by the user
    private int[] puzzleNums;       //puzzle grid in 1D int array
    private int inputCellSize;      //the number of input cells on the puzzle board
    private final int SIZE = 9;     //size constant used to contruct and work with puzzle

    /**
     * Constructor
     */
    public Puzzle() {
        createNew();
    }


    /**
     * Helper method - resets all values and generate a new puzzle
     */
    public void createNew() {
        //set up and place random zeros (30-60 zeros)
        inputCells = new boolean[SIZE][SIZE];
        Random rand = new Random();

        //rand.nextInt(31) generates a random number 0-30
        //we later add 30 making the range of the number of input cells between 30-60
        inputCellSize = rand.nextInt(31) + 30;

//        inputCellSize = 1;      //for debugging purpose

        setInputCells(inputCellSize);

        //initialize and generate arrays
        board = new int[SIZE][SIZE];
        puzzleNums = generateGrid();

        int gridIndex = 0;
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                board[r][c] = puzzleNums[gridIndex];
                gridIndex++;
            }
        }
    }

    /**
     * Enable input cells at random coordinates on a 9x9 boolean array by setting each to true
     *
     * @param numInputs the number of empty cells that will be enabled for inputs
     */
    private void setInputCells(int numInputs) {
        Random rand = new Random();
        int r, c;                   //random row and column numbers
        while (numInputs > 0) {
            r = rand.nextInt(9);    //generates a random integer for row number (0-8)
            c = rand.nextInt(9);    //generates a random integer for row number (0-8)

            //if current location is not already an input cell then sets inputCell[r][c] to true
            //then decrement numInputs by 1
            if (!inputCells[r][c]) {
                inputCells[r][c] = true;
                numInputs--;
            }
        }
//        for (int i = 0; i < numInputs; i++) {
//
//        }
//        for (int j = 0; j < SIZE; j++)
//            System.out.println(Arrays.toString(inputCells[j]));
    }

    /**
     * Generates a valid 9 by 9 Sudoku grid with 1 through 9 appearing only once in every box, row, and column
     *
     * @return An array of size 81 containing the grid
     * @author Mark Fredrick Graves, Jr.
     * @version 17.03.01
     */
    private int[] generateGrid() {

        ArrayList<Integer> arr = new ArrayList<Integer>(9);
        int[] grid = new int[81];
        for (int i = 1; i <= 9; i++) arr.add(i);

        //loads all boxes with numbers 1 through 9
        for (int i = 0; i < 81; i++) {
            if (i % 9 == 0) Collections.shuffle(arr);
            int perBox = ((i / 3) % 3) * 9 + ((i % 27) / 9) * 3 + (i / 27) * 27 + (i % 3);
            grid[perBox] = arr.get(i % 9);
        }

        //tracks rows and columns that have been sorted
        boolean[] sorted = new boolean[81];

        for (int i = 0; i < 9; i++) {
            boolean backtrack = false;
            //0 is row, 1 is column
            for (int a = 0; a < 2; a++) {
                //every number 1-9 that is encountered is registered
                boolean[] registered = new boolean[10]; //index 0 will intentionally be left empty since there are only number 1-9.
                int rowOrigin = i * 9;
                int colOrigin = i;

                ROW_COL:
                for (int j = 0; j < 9; j++) {
                    //row/column stepping - making sure numbers are only registered once and marking which cells have been sorted
                    int step = (a % 2 == 0 ? rowOrigin + j : colOrigin + j * 9);
                    int num = grid[step];

                    if (!registered[num]) registered[num] = true;
                    else //if duplicate in row/column
                    {
                        //box and adjacent-cell swap (BAS method)
                        //checks for either unregistered and unsorted candidates in same box,
                        //or unregistered and sorted candidates in the adjacent cells
                        for (int y = j; y >= 0; y--) {
                            int scan = (a % 2 == 0 ? i * 9 + y : i + 9 * y);
                            if (grid[scan] == num) {
                                //box stepping
                                for (int z = (a % 2 == 0 ? (i % 3 + 1) * 3 : 0); z < 9; z++) {
                                    if (a % 2 == 1 && z % 3 <= i % 3)
                                        continue;
                                    int boxOrigin = ((scan % 9) / 3) * 3 + (scan / 27) * 27;
                                    int boxStep = boxOrigin + (z / 3) * 9 + (z % 3);
                                    int boxNum = grid[boxStep];
                                    if ((!sorted[scan] && !sorted[boxStep] && !registered[boxNum])
                                            || (sorted[scan] && !registered[boxNum] && (a % 2 == 0 ? boxStep % 9 == scan % 9 : boxStep / 9 == scan / 9))) {
                                        grid[scan] = boxNum;
                                        grid[boxStep] = num;
                                        registered[boxNum] = true;
                                        continue ROW_COL;
                                    } else if (z == 8) //if z == 8, then break statement not reached: no candidates available
                                    {
                                        //Preferred adjacent swap (PAS)
                                        //Swaps x for y (preference on unregistered numbers), finds occurence of y
                                        //and swaps with z, etc. until an unregistered number has been found
                                        int searchingNo = num;

                                        //noting the location for the blindSwaps to prevent infinite loops.
                                        boolean[] blindSwapIndex = new boolean[81];

                                        //loop of size 18 to prevent infinite loops as well. Max of 18 swaps are possible.
                                        //at the end of this loop, if continue or break statements are not reached, then
                                        //fail-safe is executed called Advance and Backtrack Sort (ABS) which allows the
                                        //algorithm to continue sorting the next row and column before coming back.
                                        //Somehow, this fail-safe ensures success.
                                        for (int q = 0; q < 18; q++) {
                                            SWAP:
                                            for (int b = 0; b <= j; b++) {
                                                int pacing = (a % 2 == 0 ? rowOrigin + b : colOrigin + b * 9);
                                                if (grid[pacing] == searchingNo) {
                                                    int adjacentCell = -1;
                                                    int adjacentNo = -1;
                                                    int decrement = (a % 2 == 0 ? 9 : 1);

                                                    for (int c = 1; c < 3 - (i % 3); c++) {
                                                        adjacentCell = pacing + (a % 2 == 0 ? (c + 1) * 9 : c + 1);

                                                        //this creates the preference for swapping with unregistered numbers
                                                        if ((a % 2 == 0 && adjacentCell >= 81)
                                                                || (a % 2 == 1 && adjacentCell % 9 == 0))
                                                            adjacentCell -= decrement;
                                                        else {
                                                            adjacentNo = grid[adjacentCell];
                                                            if (i % 3 != 0
                                                                    || c != 1
                                                                    || blindSwapIndex[adjacentCell]
                                                                    || registered[adjacentNo])
                                                                adjacentCell -= decrement;
                                                        }
                                                        adjacentNo = grid[adjacentCell];

                                                        //as long as it hasn't been swapped before, swap it
                                                        if (!blindSwapIndex[adjacentCell]) {
                                                            blindSwapIndex[adjacentCell] = true;
                                                            grid[pacing] = adjacentNo;
                                                            grid[adjacentCell] = searchingNo;
                                                            searchingNo = adjacentNo;

                                                            if (!registered[adjacentNo]) {
                                                                registered[adjacentNo] = true;
                                                                continue ROW_COL;
                                                            }
                                                            break SWAP;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        //begin Advance and Backtrack Sort (ABS)
                                        backtrack = true;
                                        break ROW_COL;
                                    }
                                }
                            }
                        }
                    }
                }

                if (a % 2 == 0)
                    for (int j = 0; j < 9; j++) sorted[i * 9 + j] = true; //setting row as sorted
                else if (!backtrack)
                    for (int j = 0; j < 9; j++) sorted[i + j * 9] = true; //setting column as sorted
                else //reseting sorted cells through to the last iteration
                {
                    backtrack = false;
                    for (int j = 0; j < 9; j++) sorted[i * 9 + j] = false;
                    for (int j = 0; j < 9; j++) sorted[(i - 1) * 9 + j] = false;
                    for (int j = 0; j < 9; j++) sorted[i - 1 + j * 9] = false;
                    i -= 2;
                }
            }
        }

        if (!isPerfect(grid)) throw new RuntimeException("ERROR: Imperfect grid generated.");

        return grid;
    }

    /**
     * Tests an int array of length 81 to see if it is a valid Sudoku grid. i.e. 1 through 9 appearing once each in every row, column, and box
     *
     * @param grid An array with length 81 to be tested
     * @return A boolean representing if the grid is valid
     * @author Mark Fredrick Graves, Jr.
     * @version 17.03.01
     */
    private boolean isPerfect(int[] grid) {
        if (grid.length != 81)
            throw new IllegalArgumentException("The grid must be a single-dimension grid of length 81");

        //tests to see if the grid is perfect

        //for every box
        for (int i = 0; i < 9; i++) {
            boolean[] registered = new boolean[10];
            registered[0] = true;
            int boxOrigin = (i * 3) % 9 + ((i * 3) / 9) * 27;
            for (int j = 0; j < 9; j++) {
                int boxStep = boxOrigin + (j / 3) * 9 + (j % 3);
                int boxNum = grid[boxStep];
                registered[boxNum] = true;
            }
            for (boolean b : registered)
                if (!b) return false;
        }

        //for every row
        for (int i = 0; i < 9; i++) {
            boolean[] registered = new boolean[10];
            registered[0] = true;
            int rowOrigin = i * 9;
            for (int j = 0; j < 9; j++) {
                int rowStep = rowOrigin + j;
                int rowNum = grid[rowStep];
                registered[rowNum] = true;
            }
            for (boolean b : registered)
                if (!b) return false;
        }

        //for every column
        for (int i = 0; i < 9; i++) {
            boolean[] registered = new boolean[10];
            registered[0] = true;
            int colOrigin = i;
            for (int j = 0; j < 9; j++) {
                int colStep = colOrigin + j * 9;
                int colNum = grid[colStep];
                registered[colNum] = true;
            }
            for (boolean b : registered)
                if (!b) return false;
        }

        return true;
    }

    /**
     * Accessor method for solution board array
     *
     * @return A 2D int array of the solution board
     */
    public int[][] getPuzzleArray() {
        return board;
    }

    /**
     * Accessor method for array of input cells
     *
     * @return A 2D int array of showing where input cells locate
     */
    public boolean[][] getInputCells() {
        return inputCells;
    }

    /**
     * Accessor method for number of input cells on puzzle grid
     *
     * @return An integer representing the number of input cells
     */
    public int getInputCellSize() {
        return inputCellSize;
    }

}
