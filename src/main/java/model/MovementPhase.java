package model;

import controller.MovementRuleChecker;

/**
 * Interface that define the movement phase
 */

public interface MovementPhase {

    /**
     * The method that defines what the player have to do in the movement phase
     * @return VICTORY if the player won, DEFEAT if he/she loose, NEXT otherwise
     */
    MovementPhaseResult doMovement();

    /**
     * Getter for the player owner of this phase
     * @return the player that has this building phase as its
     */
    Player getOwner();

    /**
     * @return the checker used by the phase
     */
    MovementRuleChecker getChecker();
}
