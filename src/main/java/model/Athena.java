package model;

/**
 * A class for the deity Athena
 * @author Fumagalli
 */

/*
 *Opponent’s Turn: If one of your
 * Workers moved up on your last
 * turn, opponent Workers cannot
 * move up this turn
 */

public class Athena implements Deity, MovementRule, Observer {

    private boolean conditionFulfilled;
    private final Board board;
    private final Player owner;

    public Athena (Board board, Player owner){
        this.owner=owner;
        this.board=board;

        for(BoardWorker worker : owner.getWorker()){
            worker.attach(this);
        }
    }

    /**
     * represents if the god activates during player or opponent's phase
     * @return OPPONENT phase
     */
    @Override
    public GodType type(){ return GodType.OPPONENT; }

    /**
     * checks if Athena's restrictions are fulfilled
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
     * updates if the condition for activating power are fulfilled
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
     * a side method that checks if the worker moved up
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
}
