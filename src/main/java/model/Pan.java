package model;

public class Pan implements Deity,VictoryCondition{

    private final Board board;

    public Pan(Board board){
        this.board = board;
    }


    /**
     * a method that gives the description of the god
     * @return a string that represents the god's name and a short description of its power
     */
    @Override
    public String desc() {
        return "PAN"+System.lineSeparator()+"Win Condition: You also win if your Worker moves down two or more levels.";
    }

    @Override
    public boolean doCheckCondition(BoardWorker worker){
        return(board.getFloorFrom(worker.getOldPosition())-board.getFloorFrom(worker.getPosition()) >=2);
    }

}
