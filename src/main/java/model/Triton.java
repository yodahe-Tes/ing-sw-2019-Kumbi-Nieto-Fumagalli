package model;

import controller.MovementRuleChecker;
import controller.PhaseResult;
import controller.VictoryConditionChecker;

import java.util.Arrays;

/**
 * A class implementing the deity Triton
 * @author Fumagalli
 */

public class Triton implements Deity, MovementPhase{

    private final MovementRuleChecker checker;
    private final DefaultMovingLosingCondition defeated;
    private final VictoryConditionChecker win;

    public Triton(MovementRuleChecker checker, DefaultMovingLosingCondition condition, VictoryConditionChecker win){
        this.checker = checker;
        defeated = condition;
        this.win = win;
    }

    /**
     * a method that gives the description of the god
     * @return a string that represents the god's name and a short description of its power
     */
    @Override
    public  String desc(){
        return "TRITON"+System.lineSeparator()+"Your Move: Each time your Worker moves into a perimeter space, it may immediately move again.";
    }

    /**
     * the actual movement phase
     * @return the worker moved and the result of the phase packed
     */
    @Override
    public MovementPhaseResult doMovement() {

        //checking if the player can move
        if(defeated.DoCheckRule(checker)){
            getOwner().getView().noMovesLeftMessage();
            return new MovementPhaseResult(checker.getOwner().getWorker(1),PhaseResult.DEFEAT);
        }

        //gets and validates the first move

        int[] action;
        MovementAction destination;

        do {
            action = getOwner().getView().moveLocationQuery();
            destination = interpretAction(action);

        }while(!checker.doCheckRule(destination));

        int[] startingSquare = destination.getWorker().getPosition();

        //checks if a forced move is needed and does it
        checker.checkForcedMove(destination);


        destination.getWorker().move(destination.getDestination());

        //checks if won

        if(win.doCheckRule(destination.getWorker()))
            return new MovementPhaseResult(destination.getWorker(), PhaseResult.VICTORY);


        //next movements

        BoardWorker movingWorker=destination.getWorker();

        while(canMoveFurther(destination.getWorker(), startingSquare) && (destination.getDestination()[0]==1 || destination.getDestination()[0]==5 || destination.getDestination()[1]==1 || destination.getDestination()[1]==5) && getOwner().getView().moveAgainQuery()){

            do{
                action = getOwner().getView().moveLocationQuery();
                destination = new MovementAction(movingWorker, action);
            }while(!checker.doCheckRule(destination));

            checker.checkForcedMove(destination);
            destination.getWorker().move(destination.getDestination());

            if(win.doCheckRule(destination.getWorker())){
                return new MovementPhaseResult(destination.getWorker(), PhaseResult.VICTORY);
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
                if(checker.doCheckRule(new MovementAction(worker,new int[]{i,j})))
                    return true;
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

    public Player getOwner(){return checker.getOwner();}

    /**
     * a testing method for getting the input for phase
     * @return the move
     */
    @Deprecated
    private int[] getFromPlayer(){
        return TestActionProvider.getProvider().getNextMove();
    }

    @Override
    public MovementRuleChecker getChecker(){return checker;}

    /**
     * a testing method for getting a simulated user's input for phase
     * @return a boolean
     */
    @Deprecated
    private boolean getBoolFromPlayer(){
        return TestActionProvider.getProvider().getNextAnswer();
    }
}