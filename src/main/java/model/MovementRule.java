package model;

/**
 * @author Fumagalli
 * An interface that describes a rule applied in deciding if a worker can move on a square or not
 */

public interface MovementRule {

    /**
     * The method that checks the rule
     * @param action is the movement action (worker + destination)
     * @return true if the condition is fulfilled
     */

    boolean doCheckRule(MovementAction action);

    /**
     * A method that does a forced move on a worker according to rules if necessary
     * @param action is the action that would cause the forced move
     */
    void doForced(MovementAction action);

    /**
     * A method that states for initialization purpose, if this rule will affect owner's phase or opponents' phases
     * @return true if the rule will affect only opponents' phases
     */
    boolean isOpponent();
}
