package model;

/**
 * an interface that defines a victory condition
 */
public interface VictoryCondition {

    /**the method that checks if the condition is fulfilled or not
     *
     * @param worker the worker that last moved
     * @return true if the player won the game
     */
    boolean doCheckCondition(BoardWorker worker);
}
