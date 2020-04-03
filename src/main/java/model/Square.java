package model;

/**
 * @author Fumagalli
 * Implements a square
 */

public class Square {

    private int floor;

    private boolean dome;




    /**
     * Class constructor
     */
    public Square(){
        floor = 0;
        dome = false;
    }



    /**
     *Adds one floor on the square
     */
    public void addFloor(){
        floor++;
    }


    /**
     *Adds a dome on the floor
     */
    public void addDome(){
        dome=true;
    }


    /**
     * returns the current floor
     * @return floor
     */
    public int getFloor(){
        return floor;
    }


    /**
     * returns true if the square has a dome
     * @return dome
     */
    public boolean hasDome() {
        return dome;
    }
}
