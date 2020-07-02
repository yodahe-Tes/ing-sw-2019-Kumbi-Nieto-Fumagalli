package View;

import java.io.Serializable;

/**
 *
 * @author Kumbi
 * Class for visual representation of the Board
 *
 */

public class BoardView implements Serializable {
    public BoardView(String[][] board) {
        this.boardView = board;
    }

    private final String[][] boardView;
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String RESET = "\u001B[0m";
    public static final String BORDER = "\u2591";


    /**
     * @param myString string
     * @author Kumbi
     * Used to provide the right indentation for the clear representation
     * of each square
     */

    private String indent(String myString, int n) {
        return String.format("%-" + n + "s", myString);
    }


    /**
     * @author Kumbi
     * Displays the board
     */

    public void displayBoardView() {


        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(GREEN + "\u2587" + BLUE + BORDER +BORDER + BORDER + BORDER + RED + (j + 1)
                        + RESET + BLUE + BORDER + BORDER + BORDER + BORDER + RESET);     // shows top border of board
            }
            System.out.print(GREEN + "\u2587" + RESET + "\n");

            for (int j = 0; j < 5; j++) {
                System.out.print(BLUE + BORDER + RESET + "         ");                             // shows top layer of first row squares
            }
            System.out.print(BLUE + BORDER + RESET + "  " + "\n");                                        // shows last wall of top layer of first row of square

            for (int j = 0; j < 5; j++) {
                System.out.print(BLUE+BORDER+RESET+" " + indent(boardView[i][j], 8));                        // shows blocks and players row
            }
            System.out.print(RED + (i + 1) + RESET + "\n");                                                         //shows row identifier number

            for (int j = 0; j < 5; j++)
                System.out.print(BLUE + BORDER + RESET + "         ");                            // shows bottom layer of row
            System.out.print(BLUE + BORDER + RESET + "\n");                                        // shows last wall of bottom layer of row
        }

        for (int j = 0; j < 5; j++) {
            System.out.print(GREEN + "\u2587" + BLUE + BORDER +BORDER + BORDER + BORDER + RED + (j + 1)
                    + RESET + BLUE + BORDER + BORDER + BORDER + BORDER + RESET);
        }                                                                                            // shows bottom layer of each row

        System.out.print("\u001B[32m\u2587\u001B[0m");                                             //shows last wall of bottom layer of row
        System.out.print("\n");

            System.out.print("---+---   ---+---   ---+---   ---+---   ---+---  ---+---\n");

    }

}

