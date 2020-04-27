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
     * @param worker the worker that moved in the last turn
     * @return true if the victory condition is satisfied
     */
    public boolean doCheckCondition(BoardWorker worker){
        return((board.getFloorFrom(worker.getPosition()[0], worker.getPosition()[1])) == 3);
    }
}
