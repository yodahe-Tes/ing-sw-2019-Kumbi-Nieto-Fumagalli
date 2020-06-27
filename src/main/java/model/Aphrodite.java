package model;

public class Aphrodite implements Deity, MovementRule {

    Board board;
    Player owner;

    public Aphrodite(Board board,Player owner){
        this.board=board;
        this.owner=owner;
    }

    @Override
    public String desc(){
        return ("APHRODITE"+System.lineSeparator()+"Any Move: If an opponent Worker starts its turn neighboring one of your Workers, its last move must be to a space neighboring one of your Workers.");
    }

    @Override
    public boolean doCheckRule(MovementAction action){
        boolean conditionFulfilled=false;

        for(int i=1;i<=2;i++){
            if(Math.abs(action.getWorker().getPosition()[0]-owner.getWorker(i).getPosition()[0])<=1 && Math.abs(action.getWorker().getPosition()[1]-owner.getWorker(i).getPosition()[1])<=1)
                conditionFulfilled=true;
        }
        if(conditionFulfilled) {
            for (int i = 1; i <= 2; i++){
                if (Math.abs(action.getDestination()[0] - owner.getWorker(i).getPosition()[0]) <= 1  &&  Math.abs(action.getDestination()[1] - owner.getWorker(i).getPosition()[1]) <= 1)
                    return true;
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean isOpponent(){return false;}

    @Override
    public void doForced(MovementAction action){
    }
}
