package controller;

import model.BuildingRule;
import model.MovementAction;
import model.MovementRule;
import model.Player;

/**
 * a class that checks if the movement requested is legal
 * @author Fumagalli
 */

public class MovementRuleChecker {

    private MovementRule[] rule;
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
        if (action==null)
            return false;
        for(MovementRule check : rule){
            if(!check.doCheckRule(action))
                return false;
        }
        return true;
    }

    /**
     * does the forced move for the rules
     * @param action the movement action that causes the forced move execution
     */
    public void checkForcedMove(MovementAction action){
        for(MovementRule particularRule : rule){
            particularRule.doForced(action);
        }
    }

    public void removeLooser(MovementRule looserGod){
        if(rule.length>1){
            int len = rule.length;
            MovementRule[] newRules = new MovementRule[len];
            int j=0;
            for(int i=0;i<len;i++){
                if(rule[i]!=looserGod){
                    newRules[j]=rule[i];
                    j++;
                }
            }
            rule=newRules;

        }
    }

    public Player getOwner(){
        return owner;
    }
}
