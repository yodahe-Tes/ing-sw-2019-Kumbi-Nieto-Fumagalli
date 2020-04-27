package controller;


import model.BoardWorker;
import model.BuildingRule;
import model.Player;

/**
 * a class that collects rules affecting the construction rules
 * @author Fumagalli
 */

public class BuildingRuleChecker {

    private final BuildingRule[] rule;
    private final Player owner;

    /**
     * constructor
     * @param rule the array of rules the player have to obey at
     * @param owner the player which the rules refer to
     */
    public BuildingRuleChecker(BuildingRule[] rule, Player owner) {
        this.rule = rule;
        this.owner = owner;
    }

    /**
     * the method that validates every rule for requested move
     * @param worker the worker is going to move
     * @param row the row of the destination
     * @param column the column of the destination
     * @return true if every condition is fulfilled
     */
    public boolean doCheckRules(BoardWorker worker, int row, int column){
        for (BuildingRule check : rule){
            if(!check.doCheckRule(worker, row, column))
                return false;
        }
        return true;
    }
}
