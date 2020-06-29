package model;

import controller.BuildingRuleChecker;
import controller.PhaseResult;

/**
 * an interface that defines the building phase
 * @author Fumagalli
 */

public interface BuildingPhase {

    /**
     * the method that do the actual building phase
     * @param worker is the worker used for the moving phase
     * @return DEFEAT if the player loose, VICTORY if he/she won
     */
    PhaseResult doBuild(BoardWorker worker);

    /**
     * getter for the plwyer owner of this phase
     * @return the player that has this building phase as its
     */
    Player getOwner();

    /**
     * getter for the board
     * @return the board where the game is played
     */
    Board getBoard();

    /**
     * getter for the checker that this phase uses
     * @return the checker
     */
    BuildingRuleChecker getChecker();
}
