package model;

import controller.BuildingRuleChecker;

/**A class that check if the player can't build
 * @Author Fumagalli
 */

public class DefaultBuildingLosingCondition {

    private final Board board;

    /**
     * Constructor
     * @param board is the board where the condition will be checked
     */
    public DefaultBuildingLosingCondition(Board board) {
        this.board = board;
    }


    /**
     * Checks if player can't build
     * @param checker the building rule checker that allows this method to establish if the player can build in a square or not
     * @param worker the worker that is going to build
     * @return true if player can't build with this worker
     */

    public boolean doCheckRule(BuildingRuleChecker checker, BoardWorker worker){

        int[] checkBuildingIn = new int[2];
        BuildingAction checkBuilding;

        for(int i= 1; i<=5; i++){

            for(int j= 1; j<=5; j++){

                checkBuildingIn = new int[] {i,j};

                checkBuilding= new BuildingAction(checkBuildingIn, false);

                if (checker.doCheckRules(worker, checkBuilding))
                    return false;
            }
        }
        return true;

    }
}
