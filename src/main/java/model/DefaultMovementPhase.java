package model;

import controller.MovementRuleChecker;
import controller.PhaseResult;
import controller.VictoryConditionChecker;

import java.io.IOException;

/**
 * A class that implements the default movement phase
 * @author Fumagalli
 */
public class DefaultMovementPhase implements MovementPhase{

    private final MovementRuleChecker check;
    private final DefaultMovingLosingCondition loose;
    private final VictoryConditionChecker win;

    /**
     * Constructor
     * @param check is the rule checker of the phase
     * @param loose is the loosing condition
     */

    public DefaultMovementPhase(MovementRuleChecker check, DefaultMovingLosingCondition loose, VictoryConditionChecker win){
        this.check = check;
        this.loose = loose;
        this.win = win;
    }

    /**
     * The method that implements the default movement phase
     * @return a structure that contains the chosen worker for moving, and the phase result
     */

    @Override
    public MovementPhaseResult doMovement() {


        //checks loosing condition

        if(loose.DoCheckRule(check)){
            getOwner().getView().noMovesLeftMessage();
            getOwner().getView().loserMessage();
            return new MovementPhaseResult(check.getOwner().getWorker(1), PhaseResult.DEFEAT );
        }

        //moves worker

        int[] action;
        MovementAction destination;

        do {
            try {
                action = getOwner().getView().moveQuery();
                destination = interpretAction(action);
            }catch (IOException e){
                return new MovementPhaseResult(getOwner().getWorker(1),PhaseResult.DISCONNECTED);
            }

        }while(!check.doCheckRule(destination));

        //checks if a forced move is needed
        check.checkForcedMove(destination);

        //does the actual move
        destination.getWorker().move(destination.getDestination());

        //Checks victory condition
        if(win.doCheckRule(destination.getWorker())){
            getOwner().getView().winnerMessage();
            return new MovementPhaseResult(destination.getWorker(), PhaseResult.VICTORY);
        }


        return new MovementPhaseResult(destination.getWorker(), PhaseResult.NEXT);

    }

    /**
     * A private method that gets the chosen worker reference and packs it with the movement's coordinates
     * @param action is the action given by the view. The first int represents the worker ID, while the other two are the coordinates of the destination
     * @return an Action that contains the worker references
     */

    private MovementAction interpretAction(int[] action){
        BoardWorker worker = this.check.getOwner().getWorker(action[0]);
        int[] destination = new int[]{action[1],action[2]};
        return new MovementAction(worker, destination);
    }


    public Player getOwner(){return check.getOwner();}


    /**
     * A testing method for getting the input for phase
     * @return the move
     */
    @Deprecated
    private int[] getFromPlayer(){
        return TestActionProvider.getProvider().getNextMove();
    }

    @Override
    public MovementRuleChecker getChecker(){return check;}
}
