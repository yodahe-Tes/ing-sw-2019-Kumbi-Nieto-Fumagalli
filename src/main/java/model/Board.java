package model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Fumagalli
 * A class that implements the Santorini boardgame's board status
 */

public class Board{

    private final Square[][] checkerboard;
    private Player[] player;

    private final int rowNumber = 5;
    private final int columnNumber = 5;
    private ArrayList<Observer> observer;

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
        observer = new ArrayList<Observer>();
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
     * checks if the chosen square is empty
     * @param destination represents the coordinates of the destination
     * @return true if on the destination square there aren't any workers or domes
     */
    public boolean isEmpty(int[] destination){
        if(squareHasDome(destination))
            return false;
        for(int i=1;i<=numberPlayers();i++){
            for(int j=1; j<3; j++){
                if(Arrays.equals(getPlayer(i).workerPosition(j), destination))
                    return false;
            }
        }
        return true;
    }

    /**
     * simple getter for player
     * @param playerNumber player's number ID (1 or 2 in a normal 2 players game)
     */
    public Player getPlayer(int playerNumber) {
            return player[playerNumber - 1];
    }


    /**
     * @return the number of players on the board
     */
    public int numberPlayers(){
        return player.length;
    }

    /**
     * removes selected player from player list when the associated player looses
     * @param deleteMe is the looser
     */
    public void removePlayerFromList(Player deleteMe) {

        ArrayList<Player> result = new ArrayList<>();

        for(Player item : player)
            if(!deleteMe.equals(item))
                result.add(item);

        Player[] updatedPlayers = new Player[1];
        player = result.toArray(updatedPlayers);
    }

}

