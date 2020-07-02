package controller;

import model.BoardWorker;
import model.VictoryCondition;

/**
 * A class that checks the situation of every worker to find the winner
 */
public class VictoryConditionChecker {
    private final VictoryCondition[] condition;

    /**
     * Constructor
     * @param rule the array of victory conditions that represents the possible ways for the player to win
     */
    public VictoryConditionChecker(VictoryCondition[] rule) {
        this.condition = rule;
    }

    /**
     * A method that checks if every rule is fulfilled
     * @param worker is the worker that moved last
     * @return true if one condition is fulfilled
     */
    public boolean doCheckRule(BoardWorker worker){
        for(VictoryCondition check : condition){
            if(check.doCheckCondition(worker))
                return true;
        }
        return false;
    }
}
