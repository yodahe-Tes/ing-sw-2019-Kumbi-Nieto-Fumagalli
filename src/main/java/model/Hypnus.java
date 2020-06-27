package model;

import java.util.Arrays;

/**
 * A class for the deity Athena
 * @author Fumagalli
 */

/*
 *Opponent’s Turn: If one of your
 * Workers moved up on your last
 * turn, opponent Workers cannot
 * move up this turn
 */

public class Hypnus implements Deity, MovementRule {

    private final Board board;
    private final Player owner;

    public Hypnus (Board board, Player owner){
        this.owner=owner;
        this.board=board;
    }

    /**
     * a method that gives the description of the god
     * @return a string that represents the god's name and a short description of its power
     */
    @Override
    public String desc(){ return "HYPNUS"+System.lineSeparator()+"Start of Opponent’s Turn: If one of your opponent’s Workers is higher than all of their others, it cannot move.";}

    /**
     * checks if Hypnus's restrictions are fulfilled
     * @param action is the movement action (worker + destination)
     * @return true if move is legal
     */
    @Override
    public boolean doCheckRule(MovementAction action) {
        if(!(board.getFloorFrom(owner.workerPosition(1))==board.getFloorFrom(owner.workerPosition(2)))){
            if(board.getFloorFrom(owner.workerPosition(1))>board.getFloorFrom(owner.workerPosition(2)))
                return(!(action.getWorker()==owner.getWorker(1)));
            else
                return(!(action.getWorker()==owner.getWorker(2)));
        }
        return true;
    }

    /**
     * Hypnus doesn't force move any worker, so this method does nothing
     * @param action is the action that would cause the forced move
     */
    @Override
    public void doForced(MovementAction action){}

    @Override
    public boolean isOpponent(){return true;}
}
