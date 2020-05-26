package model;

/**
 * @author Fumagalli
 * An interface that describes a rule applied in deciding if a worker can move on a square or not
 */

public interface MovementRule {

    /**
     * the method that checks the rule
     * @param action is the movement action (worker + destination)
     * @return true if the condition is fulfilled
     */

    boolean doCheckRule(MovementAction action);

    /**
     * does a forced move on a worker according to rules if necessary
     * @param action is the action that would cause the forced move
     */
    void doForced(MovementAction action);

}
