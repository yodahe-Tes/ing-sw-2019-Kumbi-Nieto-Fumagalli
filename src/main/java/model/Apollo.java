package model;

import controller.MovementRuleChecker;

import java.util.Arrays;

import static java.lang.Math.abs;

/**
 * A class for the deity Apollo
 * @author Fumagalli
 */

public class Apollo implements Deity, MovementRule {

    Board board;
    Player owner;

    public Apollo(Board board, Player owner) {
        this.board = board;
        this.owner = owner;
    }

    /**
     * informs that the god takes action in the player phase
     *
     * @return the instance PLAYER
     */
    @Override
    public GodType type() {
        return GodType.PLAYER;
    }

    /**
     * checks if the chosen action fulfills the Apollo's rules
     *
     * @param action is the movement action (worker + destination)
     * @return true if the rules are fulfilled
     */
    @Override
    public boolean doCheckRule(MovementAction action) {

        BoardWorker worker = action.getWorker();
        int[] destination = action.getDestination();

        if(destination[0]<=5 && destination[0]>=1 && destination[1]<=5 && destination[1]>=1) {
            if (oneSquareDistance(worker, destination)) {
                if (isNotTooHigh(worker, destination)) {
                    if (destinationIsEmpty(destination))
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * a side-method that checks if the worker and the destination are one next to another
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
     * checks if the destination is low enough for worker to reach
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
     * checks if the destination square is empty, i.e. there are no domes or friendly workers
     * @param destination represents the coordinates of the destination
     * @return true if on the destination square there aren't any domes or any friendly workers
     */
    private boolean destinationIsEmpty(int[] destination) {
        if (board.squareHasDome(destination))
            return false;
        for (int i=1;i<=2;i++){
            if (owner.getWorker(i).getPosition()[0] == destination[0] && owner.getWorker(i).getPosition()[1] == destination[1]){
                return false;
            }
        }
        return true;
    }

    /**
     * if needed force the opponent's worker to the will-be former worker's square
     * @param action is the action that would cause the forced move
     */
    @Override
    public void doForced(MovementAction action){
        BoardWorker worker = action.getWorker();
        int[] destination = action.getDestination();

        for(Player player : board.getPlayer()){
            for(BoardWorker opponentWorker : player.getWorker()){
                if(Arrays.equals(opponentWorker.getPosition(),destination)){
                    opponentWorker.forced(worker.getPosition());
                    return;
                }
            }
        }
    }

}