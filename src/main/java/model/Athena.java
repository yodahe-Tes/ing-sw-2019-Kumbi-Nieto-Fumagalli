package model;

/**
 * A class for the deity Athena
 * @author Fumagalli
 */


public class Athena implements Deity, MovementRule, Observer {

    private boolean conditionFulfilled;
    private final Board board;
    private final Player owner;

    /**
     * Constructor, it initializes the board and the owner and subscribes to the movement of the owner's workers
     * to checks if they moved up
     * @param board the board  where the game is played
     * @param owner the player owner
     */
    public Athena (Board board, Player owner){
        this.owner=owner;
        this.board=board;

        for(BoardWorker worker : owner.getWorker()){
            worker.attach(this);
        }
    }

    /**
     * A method that gives the description of the god
     * @return a string that represents the god's name and a short description of its power
     */
    @Override
    public String desc(){ return "ATHENA"+System.lineSeparator()+"Opponent’s Turn: If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.";}

    /**
     * Checks if Athena's restrictions are fulfilled
     * @param action is the movement action (worker + destination)
     * @return true if move is legal
     */
    @Override
    public boolean doCheckRule(MovementAction action) {

        //if owner condition not fulfilled opponents condition deactivated
        if(!conditionFulfilled)
            return true;

        //check condition
        return(board.getFloorFrom(action.getWorker().getPosition())>=board.getFloorFrom(action.getDestination()));
    }

    /**
     * Updates the conditionFulfilled with TRUE if the condition for activating power are fulfilled
     */
    @Override
    public void update(){
        for(int i = 1; i<=2;i++){
            if(owner.getWorker(i).isWasMoved()){
                conditionFulfilled=checkConditionFulfilled(owner.getWorker(i));
                break;
            } else {
                conditionFulfilled=false;
            }
        }
    }

    /**
     * A private method that checks if the worker moved up
     * @param worker the last moved worker
     * @return true if waas moved up
     */
    private boolean checkConditionFulfilled(BoardWorker worker){
        return (board.getFloorFrom(worker.getOldPosition())<board.getFloorFrom(worker.getPosition()));
    }

    /**
     * Athena doesn't force move any worker, so this method does nothing
     * @param action is the action that would cause the forced move
     */
    @Override
    public void doForced(MovementAction action){}

    /**
     * A method that states for initialization purpose that athena is a movement phase that acts in opponents' turns
     * @return true
     */
    @Override
    public boolean isOpponent(){return true;}
}
