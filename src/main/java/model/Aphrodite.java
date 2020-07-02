package model;

/**
 * A class that implements the deity Aphrodite
 */
public class Aphrodite implements Deity, MovementRule {

    Board board;
    Player owner;

    public Aphrodite(Board board,Player owner){
        this.board=board;
        this.owner=owner;
    }

    /**
     * Method that contains the god description
     * @return the string with the god's description for the player to read
     */
    @Override
    public String desc(){
        return ("APHRODITE"+System.lineSeparator()+"Any Move: If an opponent Worker starts its turn neighboring one of your Workers, its last move must be to a space neighboring one of your Workers.");
    }


    /**
     * Checks if the move obeys to the rule
     * @param action is the movement action (worker + destination)
     * @return true if the move is legal
     */
    @Override
    public boolean doCheckRule(MovementAction action){
        boolean conditionFulfilled=false;

        //checks if the chosen worker starts the turn near a worker owned by the Aphrodite's player
        for(int i=1;i<=2;i++){
            if(Math.abs(action.getWorker().getPosition()[0]-owner.getWorker(i).getPosition()[0])<=1 && Math.abs(action.getWorker().getPosition()[1]-owner.getWorker(i).getPosition()[1])<=1)
                conditionFulfilled=true;
        }

        if(conditionFulfilled) {
            //checks if the worker will move next to a worker owned by Aphrodite's player
            for (int i = 1; i <= 2; i++){
                if (Math.abs(action.getDestination()[0] - owner.getWorker(i).getPosition()[0]) <= 1  &&  Math.abs(action.getDestination()[1] - owner.getWorker(i).getPosition()[1]) <= 1)
                    return true;
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean isOpponent(){return true;}

    /**
     * This method does nothing because this rule doesn't allow the player to force move anyone
     * @param action is the action that would cause the forced move
     */
    @Override
    public void doForced(MovementAction action){
    }
}
