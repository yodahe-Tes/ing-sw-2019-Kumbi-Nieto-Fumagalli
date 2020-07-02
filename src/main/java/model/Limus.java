package model;

/**
 * A class that implements the deity Limus
 * @author Fumagalli
 */
public class Limus implements Deity, BuildingRule {

    private final Board board;
    private final Player owner;

    /**
     * @return the god description in a string form
     */
    @Override
    public String desc(){
        return ("LIMUS"+System.lineSeparator()+"Opponent’s Turn: Opponent Workers cannot build on spaces neighboring your Workers, unless building a dome to create a Complete Tower.");
    }

    /**
     * Constructor
     *
     * @param board is the board where the game is played
     */
    public Limus(Board board, Player owner) {
        this.board = board;
        this.owner=owner;
    }

    /**
     * The method that check if building in a square is legal for the chosen worker
     *
     * @param action is the action that must be checked
     * @return true if the worker can build in the chosen square
     */
    @Override
    public boolean doCheckRule(BoardWorker worker, BuildingAction action) {
        return (completeTower(action) || !nextToOwnerWorker(action));
    }

    /**
     * A private method that checks if the action will build a complete tower
     *
     * @param action is the building action to check
     * @return true if the action will complete a tower
     */
    private boolean completeTower(BuildingAction action){
        return(board.getFloorFrom(action.getDestination())==3);
    }

    /**
     * Checks if the action will target a square next to the owner's workers
     *
     * @param action is the action to check
     * @return true if the target square is next to an owner's worker
     */
    private boolean nextToOwnerWorker(BuildingAction action){
        for(int i=1;i<=2;i++){
            if(Math.abs(action.getDestination()[0] - owner.getWorker(i).getPosition()[0]) <= 1  &&  Math.abs(action.getDestination()[1] - owner.getWorker(i).getPosition()[1]) <= 1)
                return true;
        }
        return false;
    }

    /**
     * A method for initialization purpose, states that this is a rule that affects only opponents' phases
     * @return true
     */
    @Override
    public boolean isOpponent(){return true;}
}