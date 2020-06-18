package model;

import static java.lang.Math.abs;

/**
 * @author Fumagalli
 */

/*
 *Your Build: Your Worker may
 * build a dome at any level
 */
public class Atlas implements Deity, BuildingRule{

    private final Board board;

    public Atlas (Board board){
        this.board = board;
    }

    /**
     * represents if the god activates during player or opponent's phase
     * @return PLAYER phase
     */
    @Override
    public  GodType type(){
        return GodType.PLAYER;
    }

    /**
     * checks if the rules are fulfilled
     * @param worker is the worker used in the moving phase
     * @param action is the building action that the player wants to do
     * @return true if the condition is fulfilled
     */
    @Override
    public boolean doCheckRule(BoardWorker worker, BuildingAction action) {

        int[] destination = action.getDestination();

        if(destination[0]<=5 && destination[0]>=1 && destination[1]<=5 && destination[1]>=1) {
            if (oneSquareDistance(worker, destination)) {
                if (destinationIsEmpty(destination)) {
                    return true;
                }
            }
        }
            return false;
    }

    /**
    * a side-method that checks if the worker and the destination of building action are one next to another
    * @param worker the worker that is going to build
    * @param destination represents the destination's coordinates
    * @return true if the square is next to the worker
    */
    private boolean oneSquareDistance(BoardWorker worker, int[] destination){
        int row = destination[0];
        int column = destination [1];
        if (worker.getPosition()[0]!=row || worker.getPosition()[1]!=column){
            if (abs(worker.getPosition()[0]-row)<=1){
                if(abs(worker.getPosition()[1]-column)<=1)
                    return true;
            }
        }
        return false;
    }

    /**
     * checks if the destination square is empty
     * @param destination represents the coordinates fo the destination
     * @return true if on the destination square there aren't any workers or domes
     */
    private boolean destinationIsEmpty(int[] destination){
        if(board.squareHasDome(destination))
            return false;
        for(int i=1;i<= board.numberPlayers();i++){
            for(int j=1; j<3; j++){
                if((board.getPlayer(i).workerPosition(j)[0]==destination[0])&&(board.getPlayer(i).workerPosition(j)[1]==destination[1]))
                    return false;
            }
        }
        return true;
    }
}