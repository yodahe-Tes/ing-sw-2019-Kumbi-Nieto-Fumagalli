package model;

import controller.BuildingRuleChecker;
import controller.PhaseResult;

/**
 * A class for the deity Hephaestus
 * @author Fumagalli
 */


public class Hephaestus implements BuildingPhase, Deity{

    Board board;
    BuildingRuleChecker checker;
    DefaultBuildingLosingCondition loose;

    /**
     * constructor
     * @param board is the board where it have to build
     * @param checker is the building rules checker associated with the player
     */
    public Hephaestus(Board board, BuildingRuleChecker checker, DefaultBuildingLosingCondition loose){
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

        if(loose.doCheckRule(checker, worker))
            return PhaseResult.DEFEAT;

        do {
            action = getOwner().getView().buildLocationQuery();
        }while (!checker.doCheckRules(worker, action));

        if (action.isForceBuildDome())
            board.addDomeTo(action.getDestination());
        else
            board.addFloorTo(action.getDestination());

        //second construction

        if(!(board.getFloorFrom(action.getDestination())>=3) && (!board.squareHasDome(action.getDestination()))){
            if(getOwner().getView().buildAgainQuery()){
                board.addFloorTo(action.getDestination());
            }
        }

        return PhaseResult.NEXT;
    }

    /**
     * a method that gives the description of the god
     * @return a string that represents the god's name and a short description of its power
     */
    @Override
    public String desc() {
        return "HAEPHESTUS"+System.lineSeparator()+"Your Build: Your Worker may build one additional block (not dome) on top of your first block.";
    }

    @Override
    public Player getOwner(){return checker.getOwner();}

    @Override
    public Board getBoard(){return board;}

    @Override
    public BuildingRuleChecker getChecker(){return checker;}

    @Deprecated
    private BuildingAction getFromPlayer(){
        return TestActionProvider.getProvider().getNextBuild();
    }
}
