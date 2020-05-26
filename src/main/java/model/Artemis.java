package model;

import controller.MovementRuleChecker;
import controller.PhaseResult;

import java.util.Arrays;

/**
 * A class implementing the deity Artemis
 * @author Fumagalli
 */

/*POWER
 *Your Move: Your Worker may
 * move one additional time, but not
 * back to its initial space.
 */

public class Artemis implements Deity, MovementPhase{

    private final MovementRuleChecker checker;
    private final DefaultMovingLosingCondition defeated;
    private final DefaultVictoryCondition win;

    public Artemis(MovementRuleChecker checker, DefaultMovingLosingCondition condition, DefaultVictoryCondition win){
        this.checker = checker;
        defeated = condition;
        this.win = win;
    }

    /**
     * represents if the god activates during player or opponent's phase
     * @return PLAYER phase
     */
    @Override
    public  GodType type(){
        return GodType.PLAYER;
    }

    /**
     * the actual movement phase
     * @return the worker moved and the result of the phase packed
     */
    @Override
    public MovementPhaseResult doMovement() {

        //checking if the player can move
        if(defeated.DoCheckRule(checker))
            return new MovementPhaseResult(checker.getOwner().getWorker(1),PhaseResult.DEFEAT);

        //gets and validates the first move

        int[] action;
        MovementAction destination;

        do {
            action = getFromPlayer(); //TODO get from view
            destination = interpretAction(action);

        }while(!checker.doCheckRule(destination));

        int[] startingSquare = destination.getWorker().getPosition();

        //checks if a forced move is needed and does it
        checker.checkForcedMove(destination);


        destination.getWorker().move(destination.getDestination());

        //checks if won

        if(win.doCheckCondition(destination.getDestination()))
            return new MovementPhaseResult(destination.getWorker(), PhaseResult.VICTORY);


        //second movement
        if(canMoveFurther(destination.getWorker(), startingSquare)){

            if(getBoolFromPlayer()/*TODO gets from player*/){

                MovementAction secondDestination;

                do{
                    action = getFromPlayer(); //TODO insert here player input
                    secondDestination = interpretAction(action);
                }while(!checker.doCheckRule(secondDestination) || Arrays.equals(secondDestination.getDestination(), startingSquare));

                checker.checkForcedMove(secondDestination);
                destination.getWorker().move(secondDestination.getDestination());

                if(win.doCheckCondition(secondDestination.getDestination())){
                    return new MovementPhaseResult(destination.getWorker(), PhaseResult.VICTORY);
                }
            }
         }
        return new MovementPhaseResult(destination.getWorker(), PhaseResult.NEXT);
    }

    /**
     * a private method that checks if the worker can move after the first
     * @param worker the worker moved with the standard move
     * @param previousAction the starting position of the first move
     * @return true if the worker can move again without returning in the starting square
     */
    private boolean canMoveFurther(BoardWorker worker, int[] previousAction) {
        MovementAction action;
        for(int i=1; i<=5; i++){
            for(int j=1; j<=5; j++){
                if(previousAction[0]!=i || previousAction[1]!=j){
                    action = new MovementAction(worker, new int[]{i,j});
                    if(checker.doCheckRule(action))
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * a private method that gets the chosen worker reference and packs it with the movement's coordinates
     * @param action is the action given by the view. The first int represents the worker ID, while the other two are the coordinates of the destination
     * @return an Action that contains the worker references
     */

    private MovementAction interpretAction(int[] action){
        BoardWorker worker = this.checker.getOwner().getWorker(action[0]);
        int[] destination = new int[]{action[1],action[2]};
        return new MovementAction(worker, destination);
    }

    /**
     * a testing method for getting the input for phase
     * @return the move
     */
    @Deprecated
    private int[] getFromPlayer(){
        return TestActionProvider.getProvider().getNextMove();
    }

    @Deprecated
    private boolean getBoolFromPlayer(){
        return TestActionProvider.getProvider().getNextAnswer();
    }
}
