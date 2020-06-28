package controller;

import model.Board;
import model.Player;

import java.util.ArrayList;

/**
 * @author Fumagalli
 * a class that manages the game turns
 */

public class TurnManager {

    private Turn[] turn;

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
        for (int j=1;j<=2; j++) {
            for (int i = 1; i <= getBoard().numberPlayers(); i++){
                getBoard().getPlayer(i).getWorker()[j-1].forced(getBoard().getPlayer(i).getView().initialPositionQuery(i,j));
            }
        }

        int i=0;
        do{
            currentResult=turn[i].doTurn();

            if(currentResult==PhaseResult.DEFEAT){
                if(turn.length>2){
                    getBoard().removePlayerFromList(turn[i].getOwner());
                    removeTurnFromList(turn[i]);
                    if (getPlayer().length==1){
                        getPlayer()[0].getView().winnerMessage();
                        break;
                    }
                }
            }

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


    /**
     * removes selected turn from turn list when the associated player looses
     * @param deleteMe is the turn of the looser
     */
    private void removeTurnFromList(Turn deleteMe) {

        ArrayList<Turn> result = new ArrayList<>();

        for(Turn item : turn)
            if(!deleteMe.equals(item))
                result.add(item);

        Turn[] updatedTurn = new Turn[1];
        turn = result.toArray(updatedTurn);
    }
}

