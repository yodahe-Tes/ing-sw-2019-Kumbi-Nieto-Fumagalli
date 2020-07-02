package model;

import java.util.Arrays;

import static java.lang.Math.abs;

/**
 * A class for the deity Apollo
 * @author Fumagalli
 */

public class Apollo implements Deity, MovementRule {

    private final Board board;
    private final Player owner;

    public Apollo(Board board, Player owner) {
        this.board = board;
        this.owner = owner;
    }

    /**
     * A method that gives the description of the god
     * @return a string that represents the god's name and a short description of its power
     */
    @Override
    public String desc() {
        return "APOLLO"+System.lineSeparator()+"Your Move: Your Worker may move into an opponent Worker’s space by forcing their Worker to the space yours just vacated.";
    }

    /**
     * Checks if the chosen action fulfills the Apollo's rules
     *
     * @param action is the movement action (worker + destination)
     * @return true if the rules are fulfilled
     */
    @Override
    public boolean doCheckRule(MovementAction action) {

        BoardWorker worker = action.getWorker();
        int[] destination = action.getDestination();

        if(board.isInside(destination)) {
            if (oneSquareDistance(worker, destination)) {
                if (isNotTooHigh(worker, destination)) {
                    if (isEmpty(destination))
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * A side-method that checks if the worker and the destination are one next to another
     *
     * @param worker      the worker that is going to move
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
     *
     * @param worker      the worker that is going to move
     * @param destination represents the coordinates of the destination
     * @return true if the destination square is at most one layer higher than the worker's
     */
    private boolean isNotTooHigh(BoardWorker worker, int[] destination) {
        if (board.getFloorFrom(worker.getPosition()) >= (board.getFloorFrom(destination) - 1))
            return true;
        return false;
    }

    /**
     * Checks if there are any domes or owner's workers in the destination
     * @param destination are the coordinates of a square
     * @return true if there aren't any domes or owner's workers
     */
    private boolean isEmpty(int[] destination){
        if(board.squareHasDome(destination))
            return false;
        for(int j=1; j<3; j++){
            if(Arrays.equals(owner.workerPosition(j), destination))
                return false;
        }
        return true;
    }


    /**
     * If needed force the opponent's worker to the will-be former worker's square
     * @param action is the action that would cause the forced move
     */
    @Override
    public void doForced(MovementAction action){
        BoardWorker worker = action.getWorker();
        int[] destination = action.getDestination();

        for(int i=1; i<=board.numberPlayers();i++){
            for(BoardWorker opponentWorker : board.getPlayer(i).getWorker()){
                if(Arrays.equals(opponentWorker.getPosition(),destination)){
                    opponentWorker.forced(worker.getPosition());
                    return;
                }
            }
        }
    }

    /**
     * For initialization purpose, this method states that this god is a rule for the owner, not for the opponents
     * @return false
     */
    @Override
    public boolean isOpponent(){return false;}

}