package model;

import static java.lang.Math.abs;

/**
 * A class that implements the usual building rules
 * @author Fumagalli
 */
public class DefaultBuildingRule implements BuildingRule {

    private final Board board;

    /**
     * Constructor
     *
     * @param board is the board where the game is played
     */
    public DefaultBuildingRule(Board board) {
        this.board = board;
    }

    /**
     * The method that check if building in a square is legal for the chosen worker
     *
     * @param action is the action that must be checked
     * @return true if the worker can build in the chosen square
     */
    @Override
    public boolean doCheckRule(BoardWorker worker, BuildingAction action) {

        int[] destination = action.getDestination();
        boolean buildDome = action.isForceBuildDome();

        if (destination[0] <= 5 && destination[0] >= 1 && destination[1] <= 5 && destination[1] >= 1) {
            if (oneSquareDistance(worker, destination)) {
                if (board.isEmpty(destination)) {
                    if (!buildDome)
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * A private method that checks if the worker and the destination of building action are one next to another
     *
     * @param worker      the worker that is going to build
     * @param destination represents the destination's coordinates
     * @return true if the square is next to the worker
     */
    private boolean oneSquareDistance(BoardWorker worker, int[] destination) {
        int row = destination[0];
        int column = destination[1];
        if (worker.getPosition()[0] != row || worker.getPosition()[1] != column) {
            if (abs(worker.getPosition()[0] - row) <= 1) {
                if (abs(worker.getPosition()[1] - column) <= 1)
                    return true;
            }
        }
        return false;
    }

    /**
     * For initialization purpose states that this rule acts only in player phase
     * @return false
     */
    @Override
    public boolean isOpponent(){return false;}
}
