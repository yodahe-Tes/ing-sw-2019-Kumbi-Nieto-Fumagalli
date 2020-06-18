package model;


import controller.Turn;

import java.util.ArrayList;

/**
 * @author Fumagalli
 * A class that implements the Santorini boardgame's board status
 */

public class Board {

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
        notifyObservers();
    }


    /**
     * Adds a floor on the square specified
     * @param position is the array that identifies the coordinates
     */
    public void addFloorTo(int[] position){
            checkerboard[position[0] - 1][position[1] - 1].addFloor();
            notifyObservers();
    }


    /**
     * Adds a dome on the square specified
     * @param position is the array that identifies the coordinates
     */
    public void addDomeTo(int[] position){
            checkerboard[position[0] - 1][position[1] - 1].addDome();
            notifyObservers();
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

    /**
     * Adds o to the observer arraylist
     * @param o is the observer that needs to be notified
     */
    @Override
    public void attach(Observer o) {

        observer.add(o);

    }



    /**
     *Deletes reference to o in the observer arraylist
     * @param o is an observer that doesn't need anymore updates
     */
    @Override
    public void detach(Observer o) {
        observer.remove(o);
    }



    /**
     *notifies every observer that position is change
     */
    @Override
    public void notifyObservers() {
        for (Observer toBeNotified : observer) {
            toBeNotified.update();
        }
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

