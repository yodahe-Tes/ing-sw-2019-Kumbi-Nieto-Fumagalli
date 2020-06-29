package controller;

import model.BoardWorker;
import model.VictoryCondition;

public class VictoryConditionChecker {
    private final VictoryCondition[] condition;

    /**
     * constructor
     * @param rule the array of victory conditions that represents the possible ways for the player to win
     */
    public VictoryConditionChecker(VictoryCondition[] rule) {
        this.condition = rule;
    }

    /**
     * a method that checks if every rule is fulfilled
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
