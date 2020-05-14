package model;

import static java.lang.Math.abs;

/**
 * class that implements the default movement rule
 * @author Fumagalli
 */

public class DefaultMovementRule implements MovementRule {

    private final Board board;

    /**
     * constructor
     * @param board the game board where the game is played
     */

    public DefaultMovementRule(Board board){
        this.board = board;
    }

    /**
     * the method that checks if the rule is respected
     * @param action the action to check if is legal
     * @return true if the movement submitted is legal
     */
    @Override
    public boolean doCheckRule(MovementAction action) {

        BoardWorker worker = action.getWorker();
        int[] destination = action.getDestination();

        if(oneSquareDistance(worker,destination)){
            if(isNotTooHigh(worker,destination)) {
                if (destinationIsEmpty(destination))
                    return true;
            }
        }
        return false;
    }

    /**
     * a side-method that checks if the worker and the destination are one next to another
     * @param worker the worker that is going to move
     * @param destination represents the coordinates of the destination
     * @return true if the square is next to the worker
     */
    private boolean oneSquareDistance(BoardWorker worker, int[] destination){
        int row = destination[0];
        int column = destination [1];
        if (abs(worker.getPosition()[0]-row)==1){
            if(abs(worker.getPosition()[1]-column)==1)
                return true;
        }
        return false;
    }

    /**
     * checks if the destination is low enough for worker to reach
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
     * checks if the destination square is empty
     * @param destination represents the coordinates of the destination
     * @return true if on the destination square there aren't any workers or domes
     */
    private boolean destinationIsEmpty(int[] destination){
        if(board.squareHasDome(destination))
            return false;
        for(int i=1;i<3;i++){
            for(int j=1; j<3; j++){
                if((board.getPlayer(i).workerPosition(j)[0]==destination[0])&&(board.getPlayer(i).workerPosition(j)[1]==destination[1]))
                    return false;
            }
        }
        return true;
    }
}