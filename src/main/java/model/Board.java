package model;


import org.junit.BeforeClass;

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
     * @param player are the players involved in the game
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
     * the building action
     * @param action the desired action
     */

    public void build(BuildingAction action){
        if (action.isForceBuildDome())
            addDomeTo(action.getDestination());
        else addFloorTo(action.getDestination());
    }


    /**
     * Adds a floor on the square specified
     * @param position is the array that identifies the coordinates
     */
    public void addFloorTo(int[] position){
            checkerboard[position[0] - 1][position[1] - 1].addFloor();
    }


    /**
     * Adds a dome on the square specified
     * @param position is the array that identifies the coordinates
     */
    public void addDomeTo(int[] position){
            checkerboard[position[0] - 1][position[1] - 1].addDome();
    }


    /**
     * check the floor of a chosen square inside the gameboard
     * @param position is the array that identifies the coordinates
     * @return the floor of the chosen square
     */
    public int getFloorFrom(int[] position){
            return checkerboard[position[0] - 1][position[1] - 1].getFloor();
    }


    /**
     * check if the square has a dome
     * @param position is the array of int that identifies the coordinates
     * @return true if the chosen square has a dome
     */
    public boolean squareHasDome(int[] position){
            return checkerboard[position[0]-1][position[1]-1].hasDome();
    }


    /**
     * simple getter for player
     * @param playerNumber player's number ID (1 or 2 in a normal 2 players game)
     */
    public Player getPlayer(int playerNumber) {
            return player[playerNumber - 1];
    }

    public Player[] getPlayer(){return player;}
}