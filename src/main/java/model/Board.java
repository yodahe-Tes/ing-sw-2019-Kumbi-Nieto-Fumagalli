package model;


import controller.Turn;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Fumagalli
 * A class that implements the Santorini boardgame's board status
 */

public class Board implements Subject {

    private final Square[][] checkerboard;
    private Player[] player;

    private final int rowNumber = 5;
    private final int columnNumber = 5;
    private ArrayList<Observer> observer;

    /**
     * Class constructor, initializes a blank board
     *
     * @param player is the array of players that will play the game
     */
    public Board(Player[] player) {

        checkerboard = new Square[rowNumber][columnNumber];

        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < columnNumber; j++) {
                checkerboard[i][j] = new Square();
            }
        }

        this.player = player;
        observer = new ArrayList<Observer>();
    }


    /**
     * Simulates the actual building action
     *
     * @param action the desired action
     */

    public void build(BuildingAction action) {
        if (action.isForceBuildDome())
            addDomeTo(action.getDestination());
        else addFloorTo(action.getDestination());
        notifyObservers();
    }


    /**
     * Adds a floor on the square specified
     *
     * @param position is the array that identifies the coordinates
     */
    public void addFloorTo(int[] position) {
        checkerboard[position[0] - 1][position[1] - 1].addFloor();
        notifyObservers();
    }


    /**
     * Adds a dome on the square specified
     *
     * @param position is the array that identifies the coordinates
     */
    public void addDomeTo(int[] position) {
        checkerboard[position[0] - 1][position[1] - 1].addDome();
        notifyObservers();
    }


    /**
     * Checks the floor of a chosen square inside the gameboard
     *
     * @param position is the array that identifies the coordinates
     * @return the floor of the chosen square
     */
    public int getFloorFrom(int[] position) {
        return checkerboard[position[0] - 1][position[1] - 1].getFloor();
    }


    /**
     * Checks if the square has a dome
     *
     * @param position is the array of int that identifies the coordinates
     * @return true if the chosen square has a dome
     */
    public boolean squareHasDome(int[] position) {
        return checkerboard[position[0] - 1][position[1] - 1].hasDome();
    }

    /**
     * Checks if the chosen square is empty
     *
     * @param destination represents the coordinates of the destination
     * @return true if on the destination square there aren't any workers or domes
     */
    public boolean isEmpty(int[] destination) {
        if (squareHasDome(destination))
            return false;
        for (int i = 1; i <= numberPlayers(); i++) {
            for (int j = 1; j < 3; j++) {
                if (Arrays.equals(getPlayer(i).workerPosition(j), destination))
                    return false;
            }
        }
        return true;
    }

    /**
     * Checks if the coordinates provided are valid
     *
     * @param square the coordinates to check
     * @return if the coordinates provided are correct
     */
    public boolean isInside(int[] square) {
        for (int i = 0; i < 2; i++) {
            if (square[i] > 5 && square[i] < 1)
                return false;
        }
        return true;
    }

    /**
     * Simple getter for player
     *
     * @param playerNumber player's number ID (1 or 2 in a normal 2 players game)
     */
    public Player getPlayer(int playerNumber) {
        return player[playerNumber - 1];
    }

    /**
     * Adds o to the observer arraylist
     *
     * @param o is the observer that needs to be notified
     */
    @Override
    public void attach(Observer o) {

        observer.add(o);

    }

    /**
     * Deletes reference to o in the observer arraylist
     *
     * @param o is an observer that doesn't need anymore updates
     */
    @Override
    public void detach(Observer o) {
        observer.remove(o);
    }

    /**
     * Notifies every observer that position is change
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
    public int numberPlayers() {
        return player.length;
    }

    /**
     * removes selected player from player list when the associated player looses
     *
     * @param deleteMe is the looser
     */
    public void removePlayerFromList(Player deleteMe) {

        ArrayList<Player> result = new ArrayList<>();

        for (int i = 0; i < player.length; i++){
            if(!deleteMe.equals(player[i]))
            result.add(player[i]);
        }

        Player[] updatedPlayers = new Player[1];
        deleteMe.closeConnection();

        player = result.toArray(updatedPlayers);
    }
}

