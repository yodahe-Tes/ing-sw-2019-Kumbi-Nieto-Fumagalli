package model;

import model.Board;
import model.MovementRulesChecker;

/**
 * A class to check default losing condition
 * @author Fumagalli
 */

public class DefaultMovingLosingCondition {

    private final Board board;

    /**
     * constructor
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

    public boolean DoCheckRule(MovementRulesChecker checker){

        int[] checkMovementIn = new int[2];

        for(int workerNum=1; workerNum<=2; workerNum++){

            for(int i= 1; i<=5; i++){

                for(int j= 1; j<=5; j++){

                    checkMovementIn = new int[] {i,j};

                    if (checker.checkRules(workerNum, checkMovementIn))
                        return false;
                }
            }
        }
        return true;
    }

    }