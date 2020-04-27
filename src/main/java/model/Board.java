package model;


/**
 * @author Fumagalli
 * A class that implements the Santorini boardgame's board status
 */

public class Board {

    private final Square[][] checkerboard;
    private final Player[] player;

    private final int rowNumber = 5;
    private final int columnNumber = 5;


    /**
     * Class constructor
     */
    public Board(Player[] player){

        checkerboard = new Square[rowNumber][columnNumber];

        for(int i=0; i<rowNumber;i++){
            for(int j=0; j<columnNumber; j++){
                checkerboard[i][j] = new Square();
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
            checkerboard[row - 1][column - 1].addFloor();
    }


    /**
     * Adds a dome on the square specified
     * @param row is the row of the square
     * @param column is the column of the square
     */
    public void addDomeTo(int row, int column){
            checkerboard[row - 1][column - 1].addDome();
    }


    /**
     * check the floor of a chosen square inside the gameboard
     * @param row is the row of the square
     * @param column is the column of the square
     * @return the floor of the chosen square
     */
    public int getFloorFrom(int row, int column){
            return checkerboard[row - 1][column - 1].getFloor();
    }


    /**
     * check if the square has a dome
     * @param row is the row of the square
     * @param column is the column of the square
     * @return true if the chosen square has a dome
     */
    public boolean squareHasDome(int row, int column){
            return checkerboard[row][column].hasDome();
    }


    /**
     * simple getter for player
     * @param playerNumber player's number ID (1 or 2 in a normal 2 players game)
     */
    public Player getPlayer(int playerNumber) {
            return player[playerNumber - 1];
    }
    


}
