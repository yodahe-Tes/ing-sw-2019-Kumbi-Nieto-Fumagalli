package model;

import java.util.Observer;

/**Every god implements their own rule based on the choose made from the GodType class
 * and also any of them implements the method power which describes what they can or can't do
 */
abstract class Atlas implements Deity, BuildingRule{
    public abstract String type();
    public void power() {


    }
}
abstract class Demetra implements Deity, BuildingPhase{
    public abstract String type();
    public void power() {

    }
}
abstract class Artemis implements Deity, MovementPhase{
    public abstract String type();
    public void power() {

    }
}
abstract class Apollo implements Deity, MovementRule{
    public abstract String type();
    public void power() {

    }
}
abstract class Athena implements Deity, MovementRule, Observer {
    public abstract String type();
    private boolean conditionFulfilled;
    public String playerOwner;

    public void power() {
    }

    public void update();

}


/** This set of interfaces helps to implements the class power
 */

interface BuildingRule {
    public void power();
}
interface BuildingPhase {
    public void power();
}
interface MovementRule {
    public void power();
}
interface MovementPhase {
    public void power();
}



/**
 *This set of classes helps looks on the checkRules class and confirm the capability to use or not the
 * power of a god
 */
class DefaultBuildingRule implements BuildingRule{
    public void power() {
    }
}

class DefaultBuildingPhase implements BuildingPhase{
    public void power() {
    }
}

class DefaultMovementRule implements MovementRule{
    public void power() {
    }
}

class DefaultMovementPhase {
    public void power() {
    }
}

class BuildingRuleChecker {
    char buildingRules[];
    //regole da soddisfare
    public void checkRules(){
        //controlla tutte le regole in array e controlla se sono state soddisfatte
        //boolean per la costruzione
    }
}

class MovementRulesChecker {

}


