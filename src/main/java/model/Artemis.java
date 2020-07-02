package model;

import controller.MovementRuleChecker;
import controller.PhaseResult;
import controller.VictoryConditionChecker;

import java.io.IOException;
import java.util.Arrays;

/**
 * A class implementing the deity Artemis
 * @author Fumagalli
 */

public class Artemis implements Deity, MovementPhase{

    private final MovementRuleChecker checker;
    private final DefaultMovingLosingCondition defeated;
    private final VictoryConditionChecker win;

    public Artemis(MovementRuleChecker checker, DefaultMovingLosingCondition condition, VictoryConditionChecker win){
        this.checker = checker;
        defeated = condition;
        this.win = win;
    }

    /**
     * A method that gives the description of the god
     * @return a string that represents the god's name and a short description of its power
     */
    @Override
    public  String desc(){
        return "ARTEMIS"+System.lineSeparator()+"Your Move: Your Worker may move one additional time, but not back to its initial space.";
    }

    /**
     * The actual movement phase
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

        getOwner().getView().yourTUrnMessage();

        int[] action;
        MovementAction destination;

        do {
            try {
                action = getOwner().getView().moveQuery();
                destination = interpretAction(action);
            }catch (IOException e){
                return new MovementPhaseResult(getOwner().getWorker(1),PhaseResult.DEFEAT);
            }


        }while(!checker.doCheckRule(destination));

        int[] startingSquare = destination.getWorker().getPosition();

        //checks if a forced move is needed and does it
        checker.checkForcedMove(destination);


        destination.getWorker().move(destination.getDestination());

        //checks if won

        if(win.doCheckRule(destination.getWorker()))
            return new MovementPhaseResult(destination.getWorker(), PhaseResult.VICTORY);


        //second movement
        if(canMoveFurther(destination.getWorker(), startingSquare)){

            boolean moveAgain =false;
            try{
                moveAgain = getOwner().getView().moveAgainQuery();
            }catch (IOException e){
                return new MovementPhaseResult(getOwner().getWorker(1),PhaseResult.DISCONNECTED);
            }

            if(moveAgain){

                MovementAction secondDestination;

                do{
                    try {
                        action = getOwner().getView().moveLocationQuery();
                        secondDestination = new MovementAction(destination.getWorker(),action);
                    }catch (IOException e) {
                        return new MovementPhaseResult(getOwner().getWorker(1), PhaseResult.DEFEAT);
                    }
                }while(!checker.doCheckRule(secondDestination) || Arrays.equals(secondDestination.getDestination(), startingSquare));

                checker.checkForcedMove(secondDestination);
                destination.getWorker().move(secondDestination.getDestination());

                if(win.doCheckRule(secondDestination.getWorker())){
                    return new MovementPhaseResult(destination.getWorker(), PhaseResult.VICTORY);
                }
            }
         }
        return new MovementPhaseResult(destination.getWorker(), PhaseResult.NEXT);
    }

    /**
     * A private method that checks if the worker can move after the first
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
     * A private method that gets the chosen worker reference and packs it with the movement's coordinates
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
     * A testing method for getting the input for phase
     * @return the move
     */
    @Deprecated
    private int[] getFromPlayer(){
        return TestActionProvider.getProvider().getNextMove();
    }

    @Override
    public MovementRuleChecker getChecker(){return checker;}

    /**
     * A method intended for testing that simulates the input from users
     * @return a boolean
     */
    @Deprecated
    private boolean getBoolFromPlayer(){
        return TestActionProvider.getProvider().getNextAnswer();
    }
}
