package controller;

import model.MovementAction;
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
     * @param action is the action that is needed to check
     * @return true if every condition is fulfilled
     */
    public boolean doCheckRule(MovementAction action){
        for(MovementRule check : rule){
            if(!check.doCheckRule(action))
                return false;
        }
        return true;
    }

    public Player getOwner(){
        return owner;
    }
}