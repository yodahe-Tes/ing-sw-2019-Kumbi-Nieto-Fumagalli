package model;

/**
 * A class for the deity Demetra
 * @author Fumagalli
 */

import controller.BuildingRuleChecker;
import controller.PhaseResult;

/*
 *Your Build: Your Worker may
 * build one additional time, but not
 * on the same space.
 */

public class Demetra implements Deity, BuildingPhase {

    private final DefaultBuildingLosingCondition loose;
    private final BuildingRuleChecker checker;
    private final Board board;

    public Demetra(Board board, DefaultBuildingLosingCondition loose, BuildingRuleChecker checker) {
        this.checker = checker;
        this.loose = loose;
        this.board = board;
    }

    /**
     * represents if the god activates during player or opponent's phase
     *
     * @return PLAYER phase
     */
    @Override
    public GodType type() {
        return GodType.PLAYER;
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

        if (canBuildFurther(worker, action.getDestination())) {
            if (getOwner().getView().buildAgainQuery()) {
                do {
                    action = getOwner().getView().buildLocationQuery();
                } while (!checker.doCheckRules(worker, action));
                board.build(action);
            }
        }

        getOwner().getView().notYourTUrnMessage();
        return PhaseResult.NEXT;
    }

    /**
     * a side method that checks if the player can build more with the chosen worker
     *
     * @param worker         the worker that is going to build
     * @param previousAction the last built square, where the worker can't build anymore for this turn
     * @return true if the condition is fulfilled
     */
    private boolean canBuildFurther(BoardWorker worker, int[] previousAction) {
        BuildingAction action;
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 5; j++) {
                if (previousAction[0] != i || previousAction[1] != j) {
                    action = new BuildingAction(new int[]{i, j});
                    if (checker.doCheckRules(worker, action))
                        return true;
                }
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

    @Deprecated
    private BuildingAction getFromPlayer() {
        return TestActionProvider.getProvider().getNextBuild();
    }

    @Deprecated
    private boolean getBoolFromPlayer(){
        return TestActionProvider.getProvider().getNextAnswer();
    }
}
