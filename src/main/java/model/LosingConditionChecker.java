package model;

/**
 * A class to check losing condition
 * @author Kumbi
 */

public class LosingConditionChecker implements LosingCondition {

    public boolean checkCondition() {

    }
}

/**
 * A class to check default losing condition
 * @author Kumbi
 */

public class DefaultLosingCondition implements LosingCondition {


    }



/**
 * A class to check victory condition
 * @author Kumbi
 */

public class DefaultVictoryCondition extends LosingConditionChecker {

    public boolean checkcondition() {

        }

    }
}

/**
 * A class to check losing condition
 * @author Kumbi
 */

interface LosingCondition {

    public boolean checkCondition();

}