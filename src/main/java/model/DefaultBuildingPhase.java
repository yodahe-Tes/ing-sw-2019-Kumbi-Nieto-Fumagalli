package model;

import controller.BuildingRuleChecker;
import controller.PhaseResult;

class DefaultBuildingPhase implements BuildingPhase{

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

        if(loose.DoCheckRule(checker, worker))
            return PhaseResult.DEFEAT;

        do {
            action = //gets from client
        }while (!checker.doCheckRules(worker, action));

        if (action.isForceBuildDome())
            board.addDomeTo(action.getDestination());
        else
            board.addFloorTo(action.getDestination());
        return PhaseResult.NEXT;
    }
}
