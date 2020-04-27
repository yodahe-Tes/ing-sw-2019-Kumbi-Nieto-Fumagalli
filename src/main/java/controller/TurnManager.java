package controller;

/**
 * @author Fumagalli
 * a class that manages the game turns
 */

public class TurnManager {

    private final Turn[] turn;

    /**
     * constructor
     * @param turn is the array of turns
     */
    public TurnManager(Turn[] turn){
        this.turn = turn;
    }


    /**
     * a method that handle the turns' cycle
     */
    public void startGame(){
        PhaseResult currentResult = PhaseResult.DEFEAT;
        int i=0;
        do{
            currentResult=turn[i].doTurn();
            if(i<turn.length-1)
                i++;
            else
                i=0;
        }while(currentResult == PhaseResult.NEXT);
    }
}
