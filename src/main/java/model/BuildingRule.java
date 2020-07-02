package model;

/**
 * @author Fumagalii
 * An interface that describes a rule applied in deciding if a worker can build on a square or not
 */

public interface BuildingRule {

    /**
     * The method that implements the actual rule
     * @param worker is the worker used in the moving phase
     * @param action is the building action that the player wants to do
     * @return true if the condition is fulfilled
     */
    boolean doCheckRule(BoardWorker worker, BuildingAction action);

    /**
     * A method that states for initialization purpose if the rule will act in the player phases or in opponents'phases
     * @return true if the rule will act in player's phase
     */
    boolean isOpponent();
}
