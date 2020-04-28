package model;

import java.util.ArrayList;

/**
 * A class modelling a worker in Santorini gameboard
 * @author Fumagalli
 */


public class BoardWorker implements Subject{


    private int[] position;

    private int[] oldPosition;

    private ArrayList<Observer> observer;

    private boolean wasMoved;



    /**
     * Class constructor
     */
    public BoardWorker(){

        position = new int[]{0,0};

        observer = new ArrayList<Observer>();

        oldPosition = position;

        wasMoved=false;
    }


    /**
     * Moves the worker to an other square, takes track of older position, calls observers
     * @param newPosition is the destination of the move
     */
    public void move(int[] newPosition){
        wasMoved=true;
        oldPosition = position;
        position = newPosition;
        notifyObservers();
        wasMoved=false;
    }

    /**
     * Forced move that doesn't call observers
     * @param newPosition is the destination of the forced move
     */
    public void forced(int[] newPosition){
        position = newPosition;
    }

    /**
     * getter for the worker's position
     * @return the position array
     */
    public int[] getPosition(){
        return position;
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


    public int[] getOldPosition(){
        return oldPosition;
    }

    public boolean isWasMoved() {
        return wasMoved;
    }
}