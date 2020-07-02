package model;

import controller.BuildingRuleChecker;
import controller.PhaseResult;

/**
 * An interface that defines the building phase
 * @author Fumagalli
 */

public interface BuildingPhase {

    /**
     * The method that do the actual building phase
     * @param worker is the worker used for the moving phase
     * @return DEFEAT if the player loose, VICTORY if he/she won
     */
    PhaseResult doBuild(BoardWorker worker);

    /**
     * Getter for the plwyer owner of this phase
     * @return the player that has this building phase as its
     */
    Player getOwner();

    /**
     * Getter for the board
     * @return the board where the game is played
     */
    Board getBoard();

    /**
     * Getter for the checker that this phase uses
     * @return the checker
     */
    BuildingRuleChecker getChecker();
}
