package model;

import controller.MovementRuleChecker;
import controller.PhaseResult;

/**
 * a class that implements the default movement phase
 * @author Fumagalli
 */
public class DefaultMovementPhase implements MovementPhase{

    MovementRuleChecker check;
    DefaultMovingLosingCondition loose;
    DefaultVictoryCondition win;

    /**
     * constructor
     * @param check is the rule checker of the phase
     * @param loose is the loosing condition
     */

    public DefaultMovementPhase(MovementRuleChecker check, DefaultMovingLosingCondition loose, DefaultVictoryCondition win){
        this.check = check;
        this.loose = loose;
        this.win = win;
    }

    /**
     * the method that implements the default movement phase
     * @return a structure that contains the chosen worker for moving, and the phase result
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
}
