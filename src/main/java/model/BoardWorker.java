package model;

import java.util.ArrayList;

/**
 * A class modelling a worker in Santorini gameboard
 * @author Fumagalli
 */
public class BoardWorker implements Subject{


    private int[] position;

    private ArrayList<Observer> observer;




    /**
     * Class constuctor
     */
    public BoardWorker(){

        position = new int[]{0,0};

        observer = new ArrayList<Observer>();

    }


    /**
     * Move the worker to an other square
     * @param row is the row of the ending square
     * @param column is the column of the ending square
     */
    public void move(int row, int column){
        position[0] = row;
        position[1] = column;
        notifyObservers();
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
    public void notifyObservers(){
        for(Observer toBeNotified : observer){
            toBeNotified.update();
        }
    }
}
