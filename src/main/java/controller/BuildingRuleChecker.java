package controller;


import model.BoardWorker;
import model.BuildingAction;
import model.BuildingRule;
import model.Player;

/**
 * a class that collects rules affecting the construction rules
 * @author Fumagalli
 */

public class BuildingRuleChecker {

    private BuildingRule[] rule;
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
     * @param action is the action to check
     * @return true if every condition is fulfilled
     */
    public boolean doCheckRules(BoardWorker worker, BuildingAction action){
        if (action==null)
            return false;
        for (BuildingRule check : rule){
            if(!check.doCheckRule(worker,action))
                return false;
        }
        return true;
    }

    public void removeLooser(BuildingRule looserGod){
        if(rule.length>1){
            int len = rule.length;
            BuildingRule[] newRules = new BuildingRule[len];
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

    public Player getOwner() {
        return owner;
    }
}
