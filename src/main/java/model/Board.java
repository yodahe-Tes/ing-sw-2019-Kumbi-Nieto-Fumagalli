package model;

/* TODO
*   Handle exception 'ArrayIndexOutOfBoundException'
* */


/**
 * @author Fumagalli
 * A class that implements the Santorini boardgame's board status
 */

public class Board {

    private final Square[][] checkboard;
    private final Player[] player;

    private final int rowNumber = 5;
    private final int columnNumber = 5;


    /**
     * Class constructor
     */
    public Board(Player[] player){

        checkboard = new Square[rowNumber][columnNumber];

        for(int i=0; i<rowNumber;i++){
            for(int j=0; j<columnNumber; j++){
                checkboard[i][j] = new Square();
            }
        }

        this.player = player;
    }


    /**
     * Adds a floor on the square specified
     * @param row is the row of the square
     * @param column is the column of the square
     */
    public void addFloorTo(int row, int column){
        try {
            checkboard[row - 1][column - 1].addFloor();
        }catch (ArrayIndexOutOfBoundsException e){}
    }


    /**
     * Adds a dome on the square specified
     * @param row is the row of the square
     * @param column is the column of the square
     */
    public void addDomeTo(int row, int column){
        try {
            checkboard[row - 1][column - 1].addDome();
        }catch(ArrayIndexOutOfBoundsException e){}
    }


    /**
     * check the floor of a chosen square inside the gameboard
     * @param row is the row of the square
     * @param column is the column of the square
     * @return the floor of the chosen square
     */
    public int getFloorFrom(int row, int column){
        try {
            return checkboard[row - 1][column - 1].getFloor();
        }catch (ArrayIndexOutOfBoundsException e){}
    }


    /**
     * check if the square has a dome
     * @param row is the row of the square
     * @param column is the column of the square
     * @return true if the chosen square has a dome
     */
    public boolean squareHasDome(int row, int column){
        try {
            return checkboard[row][column].hasDome();
        }catch(ArrayIndexOutOfBoundsException e){}
    }


    /**
     * simple getter for player
     * @param playerNumber player's number ID (1 or 2 in a normal 2 players game)
     * @return reference to player
     */
    public Player getPlayer(int playerNumber) {
        try {
            return player[playerNumber - 1];
        }catch(ArrayIndexOutOfBoundsException e){}
    }



}
