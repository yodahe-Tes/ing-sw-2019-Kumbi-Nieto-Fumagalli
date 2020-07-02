package model;

import java.util.Arrays;

/**
 * A class that implements the deity Hypnus
 * @author Fumagalli
 */


public class Hypnus implements Deity, MovementRule {

    private final Board board;
    private final Player owner;

    /**
     * Constructor
     * @param board where it has to move
     * @param owner is the player that got this god
     */
    public Hypnus (Board board, Player owner){
        this.owner=owner;
        this.board=board;
    }

    /**
     * A method that gives the description of the god
     * @return a string that represents the god's name and a short description of its power
     */
    @Override
    public String desc(){ return "HYPNUS"+System.lineSeparator()+"Start of Opponent’s Turn: If one of your opponent’s Workers is higher than all of their others, it cannot move.";}

    /**
     * Checks if Hypnus's restrictions are fulfilled
     * @param action is the movement action (worker + destination)
     * @return true if move is legal
     */
    @Override
    public boolean doCheckRule(MovementAction action) {
        BoardWorker movingWorker = action.getWorker();
        Player movingPlayer=activePlayer(movingWorker);



        if(!(board.getFloorFrom(movingPlayer.workerPosition(1))==board.getFloorFrom(movingPlayer.workerPosition(2)))){
            if(board.getFloorFrom(movingPlayer.workerPosition(1))>board.getFloorFrom(movingPlayer.workerPosition(2)))
                return(!(movingWorker==movingPlayer.getWorker(1)));
            else
                return(!(movingWorker==movingPlayer.getWorker(2)));
        }
        return true;
    }

    /**
     * A method that gets the player that is actually playing
     * @param worker that is going to be checked
     * @return the player if it's active
     */
    private Player activePlayer(BoardWorker worker){
        for(int i=1; i<=board.numberPlayers();i++){
            for(int j=1;j<=2;j++){
                if(board.getPlayer(i).getWorker(j)==worker)
                    return board.getPlayer(i);
            }
        }
        return null;
    }

    /**
     * Hypnus doesn't force move any worker, so this method does nothing
     * @param action is the action that would cause the forced move
     */
    @Override
    public void doForced(MovementAction action){}

    /**
     * states for initialization purpose that this god will affect only opponent's phases
     * @return
     */
    @Override
    public boolean isOpponent(){return true;}
}
