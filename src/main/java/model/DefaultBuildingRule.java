package model;

import static java.lang.Math.abs;

/**
 * a class that implements the usual building rules
 * @author Fumagalli
 */
public class DefaultBuildingRule implements BuildingRule{

    private final Board board;

    /**
     * constructor
     * @param board is the board where the game is played
     */
    public DefaultBuildingRule(Board board){
        this.board = board;
    }

    /**
     * the method that check if building in a square is legal for the chosen worker
     * @param worker the chosen worker
     * @param row the row of the building action's destination square
     * @param column the column of the building action destination square
     * @return true if the worker can build in the chosen square
     */
    @Override
    public boolean doCheckRule(BoardWorker worker, int row, int column) {
        if(oneSquareDistance(worker,row,column)){
            if(destinationIsEmpty(row,column))
                return true;
        }
        return false;
    }

    /**
     * a side-method that checks if the worker and the destination of building action are one next to another
     * @param worker the worker that is going to build
     * @param row the row of the destination
     * @param column the column of the destination
     * @return true if the square is next to the worker
     */
    private boolean oneSquareDistance(BoardWorker worker, int row, int column){
        if (abs(worker.getPosition()[0]-row)==1){
            if(abs(worker.getPosition()[1]-column)==1)
                return true;
        }
        return false;
    }

    /**
     * checks if the destination square is empty
     * @param row the building square's row
     * @param column the building square's column
     * @return true if on the destination square there aren't any workers or domes
     */
    private boolean destinationIsEmpty(int row, int column){
        if(board.squareHasDome(row,column))
            return false;
        for(int i=1;i<3;i++){
            for(int j=1; j<3; j++){
                if((board.getPlayer(i).workerPosition(j)[0]==row)&&(board.getPlayer(i).workerPosition(j)[1]==column))
                    return false;
            }
        }
        return true;
    }
}
