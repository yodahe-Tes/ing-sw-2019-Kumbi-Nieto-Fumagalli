package model;

import controller.BuildingRuleChecker;
import controller.PhaseResult;

import java.util.Arrays;

public class DefaultBuildingPhase implements BuildingPhase{

    Board board;
    BuildingRuleChecker checker;
    DefaultBuildingLosingCondition loose;

    /**
     * constructor
     * @param board is the board where it have to build
     * @param checker is the building rules checker associated with the player
     */
    public DefaultBuildingPhase(Board board, BuildingRuleChecker checker, DefaultBuildingLosingCondition loose){
        this.board=board;
        this.checker=checker;
        this.loose = loose;
    }

    /**
     * a method that models the default building phase
     * @param worker is the worker that must build
     * @return VICTORY if the player won, DEFEAT if was defeated, NEXT otherwise
     */
    @Override
    public PhaseResult doBuild(BoardWorker worker) {
        BuildingAction action;

        if(loose.doCheckRule(checker, worker)) {
            getOwner().getView().loserMessage();
            return PhaseResult.DEFEAT;
        }

        do {
            action = getOwner().getView().buildLocationAndTypeQuery();
        }while (!checker.doCheckRules(worker, action));

        if (action.isForceBuildDome())
            board.addDomeTo(action.getDestination());
        else
            board.addFloorTo(action.getDestination());
        return PhaseResult.NEXT;
    }

    /**
     * a method that models a default building phase where the player can't build on a square
     * @param worker is the worker that must build
     * @param here is the square where the player can't build for a specific reason
     * @return VICTORY if the player won, DEFEAT if was defeated, NEXT otherwise
     */

    public PhaseResult doBuildNotHere(BoardWorker worker, int[] here) {
        BuildingAction action;

        if(loose.doCheckRule(checker, worker)) {
            getOwner().getView().loserMessage();
            return PhaseResult.DEFEAT;
        }

        do {
            action = getOwner().getView().buildLocationAndTypeQuery();
        }while (!checker.doCheckRules(worker, action)|| Arrays.equals(action.getDestination(),here));

        if (action.isForceBuildDome())
            board.addDomeTo(action.getDestination());
        else
            board.addFloorTo(action.getDestination());
        return PhaseResult.NEXT;
    }



    @Override
    public Player getOwner(){return checker.getOwner();}

    @Override
    public BuildingRuleChecker getChecker(){return checker;}

    @Override
    public Board getBoard(){return board;}

    @Deprecated
    private BuildingAction getFromPlayer(){
        return TestActionProvider.getProvider().getNextBuild();
    }
}
