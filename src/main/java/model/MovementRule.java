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

    public boolean doCheckRule(MovementAction action);

}
