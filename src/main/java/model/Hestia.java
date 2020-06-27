package model;
/**
 * A class for the deity Hestia
 * @author Fumagalli
 */

import controller.BuildingRuleChecker;
import controller.PhaseResult;


public class Hestia implements Deity, BuildingPhase {

    private final DefaultBuildingLosingCondition loose;
    private final BuildingRuleChecker checker;
    private final Board board;

    public Hestia(Board board, DefaultBuildingLosingCondition loose, BuildingRuleChecker checker) {
        this.checker = checker;
        this.loose = loose;
        this.board = board;
    }

    /**
     * a method that gives the description of the god
     * @return a string that represents the god's name and a short description of its power
     */
    @Override
    public String desc() {
        return "HESTIA"+System.lineSeparator()+"Your Build: Your Worker may build one additional time, but this cannot be on a perimeter space.";
    }

    /**
     * the actual building phase
     *
     * @param worker is the worker used for the moving phase
     * @return if the player was DEFEATed or if the game can go to the NEXT phase
     */
    @Override
    public PhaseResult doBuild(BoardWorker worker) {

        //checks if defeated
        if (loose.doCheckRule(checker, worker)) {
            getOwner().getView().loserMessage();
            return PhaseResult.DEFEAT;
        }

        //first build

        BuildingAction action;

        do {
            action = getOwner().getView().buildLocationQuery();
        } while (!checker.doCheckRules(worker, action));

        board.build(action);

        //second building action

        if (canBuildFurther(worker)) {
            if (getOwner().getView().buildAgainQuery()) {
                do {
                    action = getOwner().getView().buildLocationQuery();
                } while (!checker.doCheckRules(worker, action) || action.getDestination()[0]==1 || action.getDestination()[0]==5 || action.getDestination()[1]==1 || action.getDestination()[1]==5);
                board.build(action);
            }
        }

        return PhaseResult.NEXT;
    }

    /**
     * a side method that checks if the player can build more with the chosen worker
     * @param worker the worker that is going to build
     * @return true if the condition is fulfilled
     */
    private boolean canBuildFurther(BoardWorker worker) {
        for (int i = 2; i <= 4; i++) {
            for (int j = 2; j <= 4; j++) {
                    if (checker.doCheckRules(worker, new BuildingAction(new int[]{i,j})))
                        return true;
                }
            }
        return false;
    }

    @Override
    public Player getOwner() {
        return checker.getOwner();
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public BuildingRuleChecker getChecker(){return checker;}

    @Deprecated
    private BuildingAction getFromPlayer() {
        return TestActionProvider.getProvider().getNextBuild();
    }

    @Deprecated
    private boolean getBoolFromPlayer(){
        return TestActionProvider.getProvider().getNextAnswer();
    }
}
