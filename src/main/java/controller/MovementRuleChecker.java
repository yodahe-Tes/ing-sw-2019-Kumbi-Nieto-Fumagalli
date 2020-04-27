package controller;

import model.BoardWorker;
import model.MovementRule;
import model.Player;

/**
 * a class that checks if the movement requested is legal
 * @Author Fumagalli
 */

public class MovementRuleChecker {

    private final MovementRule[] rule;
    private final Player owner;

    /**
     * constructor
     * @param rule the array of rules the player have to obey to
     * @param owner the player which the rules refers to
     */
    public MovementRuleChecker(MovementRule[] rule, Player owner) {
        this.rule = rule;
        this.owner = owner;
    }

    /**
     * a method that checks if every rule is fulfilled
     * @param worker the worker that is going to move
     * @param row the row of the destination
     * @param column the column of the destination
     * @return true if every condition is fulfilled
     */
    public boolean doCheckRule(BoardWorker worker, int row, int column){
        for(MovementRule check : rule){
            if(!check.doCheckRule(worker, row, column))
                return false;
        }
        return true;
    }
}
