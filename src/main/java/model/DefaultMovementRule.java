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
     * @param worker the worker that is going to move
     * @param row the row of the destination
     * @param column the column of the destination
     * @return true if the movement submitted is legal
     */
    @Override
    public boolean doCheckRule(BoardWorker worker, int row, int column) {
        if(oneSquareDistance(worker,row,column)){
            if(isNotTooHigh(worker,row,column)) {
                if (destinationIsEmpty(row, column))
                    return true;
            }
        }
        return false;
    }

    /**
     * a side-method that checks if the worker and the destination are one next to another
     * @param worker the worker that is going to move
     * @param row the row of the destination
     * @param column the column of the destination
     * @return true if the square is next to the worker
     */
    private boolean oneSquareDistance(BoardWorker worker, int row, int column){
        if (abs(worker.getPosition()[0]-row)==1){
            if(abs(worker.getPosition()[1]-column)==1)
                return true;
        }
        return false;
    }

    /**
     * checks if the destination is low enough for worker to reach
     * @param worker the worker that is going to move
     * @param row the row of the destination
     * @param column the column of the destination
     * @return true if the destination square is at most one layer higher than the worker's
     */
    private boolean isNotTooHigh(BoardWorker worker, int row, int column){
        if (board.getFloorFrom(  worker.getPosition()[0],worker.getPosition()[1]  )  >=  (board.getFloorFrom(  row, column  )-1)  )
            return true;
    }

    /**
     * checks if the destination square is empty
     * @param row the destination's row
     * @param column the destination's column
     * @return true if on the destination square there aren't any workers or domes
     */
    private boolean destinationIsEmpty(int row, int column){
        if(board.squareHasDome(row,column))
            return false;
        for(int i=1;i<3;i++){
            for(int j=1; j<3; j++){
                if((board.getPlayer(i).workerPosition(j)[0]==row)&&(board.getPlayer(i).workerPosition(j)[1]==column))
                    return false;
            }
        }
        return true;
    }
}
