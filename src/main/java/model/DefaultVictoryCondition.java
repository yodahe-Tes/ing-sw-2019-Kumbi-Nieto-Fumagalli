package model;

import model.Board;
import model.BoardWorker;

/**
 * @author Fumagalli
 * a class that models the default victory condition
 */

public class DefaultVictoryCondition {

    private final Board board;

    public DefaultVictoryCondition(Board board){

        this.board = board;
    }

    /**
     * check if the worker moved to a tier 3 square ad so won the game
     * @param justMoved the square where the worker last moved
     * @return true if the victory condition is satisfied
     */
    public boolean doCheckCondition(int[] justMoved){
        return(board.getFloorFrom(justMoved) == 3);
    }
}
