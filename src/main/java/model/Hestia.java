package model;
import controller.BuildingRuleChecker;
import controller.PhaseResult;

import java.io.IOException;

/**
 * A class that implements the deity Hestia
 * @author Fumagalli
 */

public class Hestia implements Deity, BuildingPhase {

    private final DefaultBuildingLosingCondition loose;
    private final BuildingRuleChecker checker;
    private final Board board;

    /**
     * Constructor
     * @param board where it has to move
     * @param loose checks the default building losing condition
     * @param checker is the building rules checker associated with the player
     */
    public Hestia(Board board, DefaultBuildingLosingCondition loose, BuildingRuleChecker checker) {
        this.checker = checker;
        this.loose = loose;
        this.board = board;
    }

    /**
     * A method that gives the description of the god
     * @return a string that represents the god's name and a short description of its power
     */
    @Override
    public String desc() {
        return "HESTIA"+System.lineSeparator()+"Your Build: Your Worker may build one additional time, but this cannot be on a perimeter space.";
    }

    /**
     * The actual building phase
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
            try{
                action = getOwner().getView().buildLocationAndTypeQuery();
            }catch (IOException e){
                return PhaseResult.DISCONNECTED;
            }
        } while (!checker.doCheckRules(worker, action));

        board.build(action);

        //second building action

        if (canBuildFurther(worker)) {
            boolean buildAgain=false;
            try{
                buildAgain=getOwner().getView().buildAgainQuery();
            }catch (IOException e){
                return PhaseResult.DISCONNECTED;
            }
            if (buildAgain) {
                do {
                    try{
                        action = getOwner().getView().buildLocationAndTypeQuery();
                    }catch (IOException e){
                        return PhaseResult.DISCONNECTED;
                    }
                } while (!checker.doCheckRules(worker, action) || action.getDestination()[0]==1 || action.getDestination()[0]==5 || action.getDestination()[1]==1 || action.getDestination()[1]==5);
                board.build(action);
            }
        }

        return PhaseResult.NEXT;
    }

    /**
     * A private method that checks if the player can build more with the chosen worker
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

    /**
     * A testing method for getting a simulated user's input for phase
     * @return the build
     */
    @Deprecated
    private BuildingAction getFromPlayer() {
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
