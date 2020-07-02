package model;

import static java.lang.Math.abs;

/**
 * @author Fumagalli
 * A class that implements the deity Atlas
 */

/*
 *Your Build: Your Worker may
 * build a dome at any level
 */
public class Atlas implements Deity, BuildingRule {

    private final Board board;

    public Atlas(Board board) {
        this.board = board;
    }

    /**
     * A method that gives the description of the god
     * @return a string that represents the god's name and a short description of its power
     */
    @Override
    public String desc() {return "ATLAS"+System.lineSeparator()+"Your Build: Your Worker may build a dome at any level. ";}

    /**
     * Checks if the rules are fulfilled
     *
     * @param worker is the worker used in the moving phase
     * @param action is the building action that the player wants to do
     * @return true if the condition is fulfilled
     */
    @Override
    public boolean doCheckRule(BoardWorker worker, BuildingAction action) {

        int[] destination = action.getDestination();

        if (board.isInside(destination)) {
            if (oneSquareDistance(worker, destination)) {
                if (board.isEmpty(destination)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * A side-method that checks if the worker and the destination of building action are one next to another
     *
     * @param worker      the worker that is going to build
     * @param destination represents the destination's coordinates
     * @return true if the square is next to the worker
     */
    private boolean oneSquareDistance(BoardWorker worker, int[] destination) {
        int row = destination[0];
        int column = destination[1];
        if (worker.getPosition()[0] != row || worker.getPosition()[1] != column) {
            if (abs(worker.getPosition()[0] - row) <= 1) {
                if (abs(worker.getPosition()[1] - column) <= 1)
                    return true;
            }
        }
        return false;
    }

    /**
     * States for initialization purpose that Atlas is a rule that affects only owner's turns
     * @return false
     */
    @Override
    public boolean isOpponent(){return false;}
}