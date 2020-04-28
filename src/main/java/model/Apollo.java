package model;

import controller.MovementRuleChecker;
import controller.PhaseResult;

import static java.lang.Math.abs;

/**
 *
 * A class for the deity Apollo
 * @author Fumagalli
 */

public class Apollo implements Deity, MovementPhase, MovementRule {

    Board board;
    DefaultMovingLosingCondition loose;
    MovementRuleChecker check;
    DefaultVictoryCondition win;

    public Apollo(Board board){
        this.board=board;
    }

    /**
     * informs that the god takes action in the player phase
     * @return the instance PLAYER
     */
    @Override
    public GodType type() { return GodType.PLAYER;}

    /**
     * checks if the chosen action fulfills the Apollo's rules
     * @param action is the movement action (worker + destination)
     * @return true if the rules are fulfilled
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
     * @return true if on the destination square there aren't any domes
     */
    private boolean destinationIsEmpty(int[] destination){
        if(board.squareHasDome(destination))
            return false;
        return true;
    }


    /**
     * simulate the movement phase
     * @return the worker moved and the result of the phase packed
     */

    @Override
    public MovementPhaseResult doMovement() {


        //checks loosing condition

        if(loose.DoCheckRule(check)){
            return new MovementPhaseResult(check.getOwner().getWorker(1), PhaseResult.DEFEAT );
        }

        //moves worker

        int[] action;
        MovementAction destination;

        do {
            action = //get from view
                    destination = interpretateAction(action);

        }while(!check.doCheckRule(destination));

        if(hasWorker(destination.getDestination()))
            forceMove(destination);
        destination.getWorker().move(destination.getDestination());

        //Checks victory condition
        if(win.doCheckCondition(destination.getDestination())){
            return new MovementPhaseResult(destination.getWorker(), PhaseResult.VICTORY);
        }


        return new MovementPhaseResult(destination.getWorker(), PhaseResult.NEXT);

    }

    /**
     * a private method that gets the chosen worker reference and packs it with the movement's coordinates
     * @param action is the action given by the view. The first int represents the worker ID, while the other two are the coordinates of the destination
     * @return an Action that contains the worker references
     */

    private MovementAction interpretateAction(int[] action){
        BoardWorker worker = this.check.getOwner().getWorker(action[0]);
        int[] destination = new int[]{action[1],action[2]};
        return new MovementAction(worker, destination);
    }

    /**
     * checks if the square has a worker on it
     * @param square the square do check for workers
     * @return true if the square has a worker on it
     */
    private boolean hasWorker(int[] square) {
        for (int playerNum = 1; playerNum <= 2; playerNum++) {
            for(int workerNum=1; workerNum<=5; workerNum++){
                if(board.getPlayer(playerNum).getWorker(workerNum).getPosition()==square && board.getPlayer(playerNum)!=check.getOwner())
                    return true;
            }
        }
        return false;
    }

    /**
     * returns the worker on a square
     * @param square the square that must be checked
     * @return the worker on the square or null if there aren't any
     */
    private BoardWorker getWorkerFromSquare(int[] square) {
        for (int playerNum = 1; playerNum <= 2; playerNum++) {
            for(int workerNum=1; workerNum<=5; workerNum++){
                if(board.getPlayer(playerNum).getWorker(workerNum).getPosition()==square && board.getPlayer(playerNum)!=check.getOwner())
                    return board.getPlayer(playerNum).getWorker(workerNum);
            }
        }
        return null;
    }

    /**
     * moves away the worker on the movement destination's square
     * @param action represents the movement the worker wants to do
     */
    private void forceMove(MovementAction action){

        BoardWorker opponent = getWorkerFromSquare(action.destination);

        int addToRow= action.destination[0]-action.getWorker().getPosition()[0];
        int addToColumn = action.destination[1] - action.getWorker().getPosition()[1];

        opponent.forced(new int[] {opponent.getPosition()[0]+addToRow, opponent.getPosition()[1]+addToColumn});
    }
}
