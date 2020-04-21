package controller;

import model.Board;
import model.MovementRulesChecker;

/**
 * A class to check default losing condition
 * @author Fumagalli
 */

public class DefaultLosingCondition {

    private final Board board;

    /**
     * constructor method
     * @param board
     */
    public DefaultLosingCondition(Board board){
        this.board = board;
    }

    /**
     * Checks if player can't move any worker
     * @param checker
     * @return true if player can't move any worker
     */

    public boolean DoCheckRule(MovementRulesChecker checker){

        int[] checkMovementIn = new int[2];

        for(int workerNum=1; workerNum<=2; workerNum++){

            for(int i= -1; i<=1; i++){

                for(int j= -1; j<=1; j++){

                    checkMovementIn = checker.getPlayer.workerPosition(workerNum);
                    checkMovementIn[0] = checkMovementIn[0]+i;
                    checkMovementIn[1] = checkMovementIn[1]+j;

                    if (checker.checkRules(workerNum, checkMovementIn))
                        return false;
                }
            }
        }
        return true;
    }

    }