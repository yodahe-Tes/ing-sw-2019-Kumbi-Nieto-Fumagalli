package model;

import controller.MovementRuleChecker;
import controller.PhaseResult;

/**
 * a class that implements the default movement phase
 * @author Fumagalli
 */
public class DefaultMovementPhase implements MovementPhase{

    MovementRuleChecker check;

    public DefaultMovementPhase(MovementRuleChecker check){
        this.check = check;
    }

    @Override
    public MovementPhaseResult doMovement() {

        boolean moveIsLegal=false;
        Movement destination;

        do {
            destination = ;// chiede al player di inserire la posizione del worker
            moveIsLegal = check.doCheckRule(destination);

        }while(!moveIsLegal);

        destination.getWorker.move(destination.getDestination());



        return new MovementPhaseResult(Movement.getWorker,


        return null;
    }
}
