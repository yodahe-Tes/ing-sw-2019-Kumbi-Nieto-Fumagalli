package controller;

import model.Board;
import model.Player;

import java.util.ArrayList;

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

    /**
     * method for debug use
     * @return the players involved in the game
     */
    public Player[] getPlayer(){
        ArrayList<Player> players = new ArrayList<Player>();
        for(Turn playerTurn : turn){
            players.add(playerTurn.getOwner());
        }

        Player[] returnPlayer = new Player[1];
        returnPlayer = players.toArray(returnPlayer);
        return returnPlayer;
    }

    public Board getBoard(){
        return turn[0].getBoard();
    }

    public Turn[] getTurn(){return turn;}
}

