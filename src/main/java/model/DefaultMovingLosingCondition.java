package model;

import controller.MovementRuleChecker;

/**
 * A class to check default losing condition
 * @author Fumagalli
 */

public class DefaultMovingLosingCondition {

    private final Board board;

    /**
     * Constructor
     * @param board is the board where it will control the condition
     */

    public DefaultMovingLosingCondition(Board board) {
        this.board = board;
    }

    /**
     * Checks if player can't move any worker
     * @param checker the movement rule checker that allows this method to establish if the player can move in a square or not
     * @return true if player can't move any worker
     */

    public boolean DoCheckRule(MovementRuleChecker checker){

        int[] checkMovementIn = new int[2];
        MovementAction checkMovement;

        for(int workerNum=1; workerNum<=2; workerNum++){

            for(int i= 1; i<=5; i++){

                for(int j= 1; j<=5; j++){

                    checkMovementIn = new int[] {i,j};

                    checkMovement= new MovementAction(checker.getOwner().getWorker(workerNum), checkMovementIn);

                    if (checker.doCheckRule(checkMovement))
                        return false;
                }
            }
        }
        return true;
    }

    }