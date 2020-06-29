package model;

import controller.BuildingRuleChecker;
import controller.MovementRuleChecker;
import controller.PhaseResult;
import controller.VictoryConditionChecker;


/**
 * A class implementing the deity Prometheus
 * @author Fumagalli
 */

public class Prometheus implements Deity, MovementPhase{

    private final MovementRuleChecker checker;
    private final DefaultMovingLosingCondition defeated;
    private final VictoryConditionChecker win;
    private final DefaultBuildingPhase buildingPhase;
    private final Board board;

    /**
     * constructor that initializes this class and an inner DefaultBuildingPhase for simulate the optional build
     * @param checker is the movement rule checker used for check if the move is legal
     * @param condition is the default loosing condition in the movement phase
     * @param win is the victory condition checker
     * @param buildCheck is the building rule chacker used by the inner DefaultBuildingPhase
     * @param board is the board where the game is played
     */
    public Prometheus(MovementRuleChecker checker, DefaultMovingLosingCondition condition, VictoryConditionChecker win, BuildingRuleChecker buildCheck,Board board){
        this.checker = checker;
        defeated = condition;
        this.win = win;
        this.board = board;
        buildingPhase = new DefaultBuildingPhase(board, buildCheck, new DefaultBuildingLosingCondition(board));
    }

    /**
     * a method that gives the description of the god
     * @return a string that represents the god's name and a short description of its power
     */
    @Override
    public String desc() {
        return "PROMETHEUS"+System.lineSeparator()+"Your Turn: If your Worker does not move up, it may build both before and after moving.";
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

        //gets and validates move

        getOwner().getView().yourTUrnMessage();
        int[] action;
        MovementAction destination;

        do {
            action = getOwner().getView().moveLocationQuery();
            destination = interpretAction(action);

        }while(!checker.doCheckRule(destination));

        //builds if the worker doesn't go up before moving
        if(!workerGoesUp(destination) && getOwner().getView().buildAgainQuery()){
            buildingPhase.doBuildNotHere(destination.getWorker(),destination.destination);
        }


        int[] startingSquare = destination.getWorker().getPosition();

        //checks if a forced move is needed and does it
        checker.checkForcedMove(destination);


        destination.getWorker().move(destination.getDestination());

        //checks if won

        if(win.doCheckRule(destination.getWorker()))
            return new MovementPhaseResult(destination.getWorker(), PhaseResult.VICTORY);

        return new MovementPhaseResult(destination.getWorker(), PhaseResult.NEXT);
    }

    /**
     * checks if the worker wants to go up or not
     * @param action the movement action
     * @return
     */
    private boolean workerGoesUp(MovementAction action){
        return board.getFloorFrom(action.getWorker().getPosition())<board.getFloorFrom(action.getDestination());
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
