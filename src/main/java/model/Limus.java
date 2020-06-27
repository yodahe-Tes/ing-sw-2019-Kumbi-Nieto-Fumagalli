package model;

import static java.lang.Math.abs;

/**
 * a class that implements the usual building rules
 * @author Fumagalli
 */
public class Limus implements Deity, BuildingRule {

    private final Board board;
    private final Player owner;

    @Override
    public String desc(){
        return ("LIMUS"+System.lineSeparator()+"Opponent’s Turn: Opponent Workers cannot build on spaces neighboring your Workers, unless building a dome to create a Complete Tower.");
    }

    /**
     * constructor
     *
     * @param board is the board where the game is played
     */
    public Limus(Board board, Player owner) {
        this.board = board;
        this.owner=owner;
    }

    /**
     * the method that check if building in a square is legal for the chosen worker
     *
     * @param action is the action that must be checked
     * @return true if the worker can build in the chosen square
     */
    @Override
    public boolean doCheckRule(BoardWorker worker, BuildingAction action) {
        return (completeTower(action) && !nextToOwnerWorker(action));
    }

    private boolean completeTower(BuildingAction action){
        return(board.getFloorFrom(action.getDestination())==3);
    }

    private boolean nextToOwnerWorker(BuildingAction action){
        for(int i=1;i<=2;i++){
            if(Math.abs(action.getDestination()[0] - owner.getWorker(i).getPosition()[0]) <= 1  &&  Math.abs(action.getDestination()[1] - owner.getWorker(i).getPosition()[1]) <= 1)
                return true;
        }
        return false;
    }

    @Override
    public boolean isOpponent(){return true;}
}