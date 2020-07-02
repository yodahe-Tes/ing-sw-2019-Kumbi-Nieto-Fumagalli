package model;

import controller.BuildingRuleChecker;
import controller.PhaseResult;

import java.io.IOException;

/**
 * A class that implements the deity Hephaestus
 * @author Fumagalli
 */


public class Hephaestus implements BuildingPhase, Deity{

    Board board;
    BuildingRuleChecker checker;
    DefaultBuildingLosingCondition loose;

    /**
     * Constructor
     * @param board is the board where it have to build
     * @param checker is the building rules checker associated with the player
     */
    public Hephaestus(Board board, BuildingRuleChecker checker, DefaultBuildingLosingCondition loose){
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

        //checks if the player can't build
        if(loose.doCheckRule(checker, worker)) {
            getOwner().getView().loserMessage();
            return PhaseResult.DEFEAT;
        }
        //gets the action from the user
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

        //second construction

        if(!(board.getFloorFrom(action.getDestination())>=3) && (!board.squareHasDome(action.getDestination()))){
            boolean buildAgain=false;
            try{
                buildAgain=getOwner().getView().buildAgainQuery();
            }catch (IOException e){
                return PhaseResult.DISCONNECTED;
            }
            if (buildAgain) {
                board.addFloorTo(action.getDestination());
            }
        }

        return PhaseResult.NEXT;
    }

    /**
     * A method that gives the description of the god
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

    /**
     * A testing method for getting a simulated user's input for phase
     * @return the build
     */
    @Deprecated
    private BuildingAction getFromPlayer(){
        return TestActionProvider.getProvider().getNextBuild();
    }

    /**
     * A testing method for getting a simulated user's input for phase
     * @return a boolean
     */
    @Deprecated
    private boolean getBoolFromPlayer(){
        return TestActionProvider.getProvider().getNextAnswer();
    }
}
