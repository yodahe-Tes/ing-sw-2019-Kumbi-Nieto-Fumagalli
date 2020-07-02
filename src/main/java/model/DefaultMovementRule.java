package model;

import java.util.Arrays;

import static java.lang.Math.abs;

/**
 * Class that implements the default movement rule
 * @author Fumagalli
 */

public class DefaultMovementRule implements MovementRule {

    private final Board board;

    /**
     * Constructor
     * @param board the game board where the game is played
     */

    public DefaultMovementRule(Board board){
        this.board = board;
    }

    /**
     * The method that checks if the rule is respected
     * @param action the action to check if is legal
     * @return true if the movement submitted is legal
     */
    @Override
    public boolean doCheckRule(MovementAction action) {

        BoardWorker worker = action.getWorker();
        int[] destination = action.getDestination();

        if(board.isInside(destination)) {
            if (oneSquareDistance(worker, destination)) {
                if (isNotTooHigh(worker, destination)) {
                    if (board.isEmpty(destination))
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * A side-method that checks if the worker and the destination are one next to another
     * @param worker the worker that is going to move
     * @param destination represents the coordinates of the destination
     * @return true if the square is next to the worker
     */
    private boolean oneSquareDistance(BoardWorker worker, int[] destination){
        int row = destination[0];
        int column = destination [1];
        if (worker.getPosition()[0]!=row || worker.getPosition()[1]!=column){
            if (abs(worker.getPosition()[0]-row)<=1){
                if(abs(worker.getPosition()[1]-column)<=1)
                    return true;
            }
        }
        return false;
    }

    /**
     * Checks if the destination is low enough for worker to reach
     * @param worker the worker that is going to move
     * @param destination represents the coordinates of the destination
     * @return true if the destination square is at most one layer higher than the worker's
     */
    private boolean isNotTooHigh(BoardWorker worker, int[] destination){
        if (board.getFloorFrom(worker.getPosition())  >=  (board.getFloorFrom(destination)-1)  )
            return true;
        return false;
    }

    /**
     * The default movement phase doesn't force-move any worker, so this method does nothing
     * @param action is the action that would cause the forced move
     */
    @Override
    public void doForced(MovementAction action) {}

    /**
     * States that this rule is for owner's phase
     * @return false
     */
    @Override
    public boolean isOpponent(){return false;}
}
