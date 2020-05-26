package model;

/**
 * interface that define the movement phase
 */

public interface MovementPhase {

    /**
     * the method that defines what the player have to do in the movement phase
     * @return VICTORY if the player won, DEFEAT if he/she loose, NEXT otherwise
     */
    MovementPhaseResult doMovement();
}
