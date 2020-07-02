package model;

import controller.BuildingRuleChecker;
import controller.PhaseResult;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.Arrays;

/**
 * A class that simulates a default building phases, i.e. the player builds only one time
 * @author Fumagalli
 */

public class DefaultBuildingPhase implements BuildingPhase{

    Board board;
    BuildingRuleChecker checker;
    DefaultBuildingLosingCondition loose;

    /**
     * Constructor
     * @param board is the board where it have to build
     * @param checker is the building rules checker associated with the player
     */
    public DefaultBuildingPhase(Board board, BuildingRuleChecker checker, DefaultBuildingLosingCondition loose){
        this.board=board;
        this.checker=checker;
        this.loose = loose;
    }

    /**
     * A method that models the default building phase
     * @param worker is the worker that must build
     * @return VICTORY if the player won, DEFEAT if was defeated, NEXT otherwise
     */
    @Override
    public PhaseResult doBuild(BoardWorker worker) {
        BuildingAction action;

        //checks if the worker can build
        if(loose.doCheckRule(checker, worker)) {
            getOwner().getView().loserMessage();
            return PhaseResult.DEFEAT;
        }

        //gets the action from the player
        do {
            try{
                action = getOwner().getView().buildLocationAndTypeQuery();
            }catch (IOException e){
                return PhaseResult.DISCONNECTED;
            }
        }while (!checker.doCheckRules(worker, action));

        //does the actual build
        if (action.isForceBuildDome())
            board.addDomeTo(action.getDestination());
        else
            board.addFloorTo(action.getDestination());
        return PhaseResult.NEXT;
    }

    /**
     * A method that simulate a default building phase where the player can't build on a square
     * @param worker is the worker that must build
     * @param here is the square where the player can't build for a specific reason
     * @return VICTORY if the player won, DEFEAT if was defeated, NEXT otherwise
     */

    public PhaseResult doBuildNotHere(BoardWorker worker, int[] here) {
        BuildingAction action;

        //checks if the player can build with the chosen worker
        if(loose.doCheckRule(checker, worker)) {
            getOwner().getView().loserMessage();
            return PhaseResult.DEFEAT;
        }

        //gets a legal action from the player
        do {
            try{
            action = getOwner().getView().buildLocationAndTypeQuery();
            }catch (IOException e){
                return PhaseResult.DISCONNECTED;
            }
        }while (!checker.doCheckRules(worker, action)|| Arrays.equals(action.getDestination(),here));

        //does the actual build
        if (action.isForceBuildDome())
            board.addDomeTo(action.getDestination());
        else
            board.addFloorTo(action.getDestination());

        return PhaseResult.NEXT;
    }



    @Override
    public Player getOwner(){return checker.getOwner();}

    /**
     * @return the building rule checker used by this phase
     */
    @Override
    public BuildingRuleChecker getChecker(){return checker;}

    @Override
    public Board getBoard(){return board;}

    /**
     * A method intended for testing that simulates the input from users
     * @return a building action
     */
    @Deprecated
    private BuildingAction getFromPlayer(){
        return TestActionProvider.getProvider().getNextBuild();
    }
}
