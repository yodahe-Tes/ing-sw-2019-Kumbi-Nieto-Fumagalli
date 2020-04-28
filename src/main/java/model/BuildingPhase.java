package model;

import controller.PhaseResult;

/**
 * an interface that defines the building phase
 * @Author Fumagalli
 */

public interface BuildingPhase {

    /**
     * the method that do the actual building phase
     * @param worker is the worker used for the moving phase
     * @return DEFEAT if the player loose, VICTORY if he/she won
     */
    public PhaseResult doBuild(BoardWorker worker);
}
