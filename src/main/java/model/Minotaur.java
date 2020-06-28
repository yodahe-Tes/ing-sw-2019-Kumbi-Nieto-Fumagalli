package model;

import java.util.Arrays;

import static java.lang.Math.abs;

/**
 * A class for the deity Minotaur
 * @author Fumagalli
 */

public class Minotaur implements Deity, MovementRule {

    private final Board board;
    private final Player owner;

    public Minotaur(Board board, Player owner) {
        this.board = board;
        this.owner = owner;
    }

    /**
     * a method that gives the description of the god
     * @return a string that represents the god's name and a short description of its power
     */
    @Override
    public String desc() {
        return "MINOTAUR"+System.lineSeparator()+"Your Move: Your Worker may move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.";
    }

    /**
     * checks if the chosen action fulfills the Minotaur's rules
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
                    if (board.isEmpty(destination))
                        return true;
                    //checks if the destination has a worker and if the next square is empty
                    if (destinationHasOpponentWorker(destination) && board.isEmpty(new int[]{2*destination[0]-worker.getPosition()[0], 2*destination[1]-worker.getPosition()[1]}))
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
     * checks if there are no domes or friendly workers on the destination square
     * @param destination represents the coordinates of the destination
     * @return true if on the destination square there aren't any domes or any friendly workers
     */
    private boolean destinationHasOpponentWorker(int[] destination) {
        if (board.squareHasDome(destination))
            return false;
        for (int i=1;i<=board.numberPlayers();i++){
            if (owner.getWorker(i).getPosition()[0] == destination[0] && owner.getWorker(i).getPosition()[1] == destination[1]){
                return false;
            }
        }
        return true;
    }

    /**
     * if needed pushes the opponent's worker to the next square
     * @param action is the action that would cause the forced move
     */
    @Override
    public void doForced(MovementAction action){
        BoardWorker worker = action.getWorker();
        int[] destination = action.getDestination();

        for(int i=1; i<=board.numberPlayers();i++){
            for(BoardWorker opponentWorker : board.getPlayer(i).getWorker()){
                if(Arrays.equals(opponentWorker.getPosition(),destination)){
                    opponentWorker.forced(new int[]{2*destination[0]-worker.getPosition()[0], 2*destination[1]-worker.getPosition()[1]});
                    return;
                }
            }
        }
    }

    @Override
    public boolean isOpponent(){return false;}

}