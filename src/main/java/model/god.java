package model;

import java.util.ArrayList;
import java.util.Observer;

/**Every god implements their own rule based on the choose made from the GodType class
 * and also any of them implements the method power which describes what they can or can't do
 * @author Nieto
 */
abstract class Atlas implements Deity, BuildingRule{
    /**
     *Your Build: Your Worker may
     * build a dome at any level.
     */
    public abstract String type();
    public void doCheckRule() {


    }
}
abstract class Demetra implements Deity, BuildingPhase{
    /**
     *Your Build: Your Worker may
     * build one additional time, but not
     * on the same space.
     */
    public abstract String type();
    public void doCheckRule() {

    }
}
abstract class Artemis implements Deity, MovementPhase{
    /**
     *Your Move: Your Worker may
     * move one additional time, but not
     * back to its initial space.
     */
    public abstract String type();
    public void doCheckRule(worker, movementRulesCollection) {
        if movementRulesCollection == true
                worker.move();


    }
}
abstract class Apollo implements Deity, MovementRule{
    /**
     *Your Move: Your Worker may
     * move into an opponent Worker’s
     * space by forcing their Worker to
     * the space yours just vacated.
     */
    public abstract String type();
    public void doCheckRule() {

    }
}
abstract class Athena implements Deity, MovementRule, Observer {
    /**
     *Opponent’s Turn: If one of your
     * Workers moved up on your last
     * turn, opponent Workers cannot
     * move up this turn
     */
    public abstract String type();
    private boolean conditionFulfilled;
    public String playerOwner;

    public void doCheckRule() {
    }

    public void update();

}


/** This set of interfaces helps to implements the class power
 */

interface BuildingRule {
    public void doCheckRule();
}
interface BuildingPhase {
    public void doCheckRule();
}
interface MovementRule {
    public void doCheckRule();
}
interface MovementPhase {
    public void doCheckRule();
}



/**
 *This set of classes helps looks on the checkRules class and confirm the capability to use or not the
 * power of a god
 */
class DefaultBuildingRule implements BuildingRule{
    public void doCheckRule() {
    }
}

class DefaultBuildingPhase implements BuildingPhase{
    public void doCheckRule() {
    }
}

class DefaultMovementRule implements MovementRule{
    public void doCheckRule() {
    }
}

class DefaultMovementPhase implements MovementPhase{
    public void doCheckRule() {
    }
}
/**interface that is implemented by the every
 *class and owns the method to check the rules
 */
interface RulesChecker{
    public void doCheckRule();
}
class MasterRulesChecker {
    /**
     * A class that contains the arrylist of rules
     * and contains a for cycle that uses the method checkRules
     * for every element of the array
     */
    private ArrayList<RulesChecker> listaDiCheck;
    //regole da soddisfare
    public void addCheck(RulesChecker){
        listaDiCheck.add(RulesChecker);

    }
    public void checkRules(){
        for (RulesChecker var : listaDiCheck) {
            var.checkRules();
        }
    }
    MasterRulesChecker.addCheck(BuildingRuleChecker);
    MasterRulesChecker.addCheck(MovementRulesChecker);

}
class BuildingRuleChecker implements RulesChecker{
    private ArrayList<RulesChecker> listaDiCheck;

    public void checkRules(){
        for (listaDiCheck:RulesChecker){
            listaDiCheck.checkRules();
        }
    }

}

class MovementRulesChecker implements RulesChecker{
    private ArrayList<RulesChecker> listaDiCheck;

    public void checkRules(){
        for (listaDiCheck:RulesChecker){
        listaDiCheck.checkRules();
        }
    }

}


