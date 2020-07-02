package controller;

import model.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Fumagalli
 * A class that manages the game turns
 */

public class TurnManager {

    private Turn[] turn;
    PhaseResult currentResult = PhaseResult.DEFEAT;
    int turnNumber=0;


    /**
     * Constructor
     * @param turn is the array of turns
     */
    public TurnManager(Turn[] turn){
        this.turn = turn;
    }


    /**
     * A method that handle the turns' cycle
     */
    public void startGame(){

        //informs the players about their and opponent's god cards and places the workers on the board

        gettingStarted();

        turnNumber=0;

        //starts the turn cycle

        while(currentResult != PhaseResult.VICTORY && turn.length>1 && currentResult!=PhaseResult.DISCONNECTED){
            // does the actual turn
            currentResult=turn[turnNumber].doTurn();

            if(currentResult==PhaseResult.DEFEAT){
                //if someone loose or disconnects
                playerLost(turn[turnNumber]);
            }

            else if(currentResult==PhaseResult.DISCONNECTED){
                for(Turn turns : turn){
                    turns.getOwner().getView().aPlayerHasDisconnectedMessage(turns.getOwner());
                }
            }


            else if(currentResult==PhaseResult.VICTORY){
                //if someone won inform players
                for(Turn otherTurn : turn){
                    if (otherTurn!=turn[turnNumber]){
                        otherTurn.getOwner().getView().aPlayerHasWonMessage(turn[turnNumber].getOwner());
                        otherTurn.getOwner().getView().loserMessage();
                    }
                }
            }

            //restarts the cycle if every user did the turn
            if(currentResult != PhaseResult.VICTORY && turn.length>1 && currentResult!=PhaseResult.DISCONNECTED){
                if(turnNumber<turn.length-1)
                turnNumber++;
                else
                    turnNumber=0;
                }
            //until someone wins
        }
        if(currentResult==PhaseResult.DISCONNECTED){
            Turn disconnected = turn[turnNumber];
            for(Turn remains : turn){
                if(remains!=disconnected)
                    remains.getOwner().getView().aPlayerHasDisconnectedMessage(disconnected.getOwner());
            }
        }

        for(Turn remains : turn){
            getBoard().removePlayerFromList(remains.getOwner());
            removeTurnFromList(remains);
        }
        System.out.println("finished game");
}


    /**
     * Method for getting the players
     * @return the players involved in the game in an array form
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
     * Removes selected turn from turn list when the associated player looses
     * @param deleteMe is the turn of the looser
     */
    private void removeTurnFromList(Turn deleteMe) {

        ArrayList<Turn> result = new ArrayList<>();

        for(int i=0;i<turn.length;i++)
            if(!deleteMe.equals(turn[i]))
                result.add(turn[i]);

        Turn[] updatedTurn = new Turn[1];
        turn = result.toArray(updatedTurn);
    }

    /**
     * Removes a player from the game
     * @param looser is the turn associated with the looser player
     */
    private void playerLost(Turn looser){

        if(turn.length>2){
            //if there are more than two player delete god's power on other players
            for(Turn opponentTurn : turn) {
                if (opponentTurn != looser){

                    turn[turnNumber].getOwner().getView().aPlayerHasLostmessage(looser.getOwner());

                    if (looser.getOwner().getDeity() instanceof MovementRule)
                        opponentTurn.getMove().getChecker().removeLooser((MovementRule) looser.getOwner().getDeity());
                    if (looser.getOwner().getDeity() instanceof BuildingRule)
                        opponentTurn.getBuild().getChecker().removeLooser((BuildingRule) looser.getOwner().getDeity());
                }
            }
            //delete player
            getBoard().removePlayerFromList(looser.getOwner());
            removeTurnFromList(looser);
            turnNumber--;
        }
        else{
            //the only remaining player wins
            int winner;
            if(turnNumber==0)
                winner=1;
            else
                winner=0;

            turn[winner].getOwner().getView().aPlayerHasLostmessage(looser.getOwner());
            turn[winner].getOwner().getView().winnerMessage();
            currentResult=PhaseResult.VICTORY;
        }

    }

    /**
     * A method that initialize the position for every worker of every player
     */
    private void gettingStarted(){
        //informs every player of gods choice
        for(Turn player : turn){
            player.getOwner().getView().assignedGodMessage();
            player.getOwner().getView().otherPlayersGod();
            player.getOwner().getView().informType();
        }

        //gets the workers' starting positions for every player
        for (int j=1;j<=2; j++) {
            for (int i = 1; i <= getBoard().numberPlayers(); i++){
                if(turn.length<=1) {
                    currentResult=PhaseResult.DISCONNECTED;
                    return;
                }
                int[] newPosition = null;
                currentResult=PhaseResult.NEXT;
                do{
                    try {
                        newPosition = turn[i - 1].getOwner().getView().initialPositionQuery(j);
                    }catch (IOException|NullPointerException e){
                        Turn disconnected = turn[i-1];
                        System.out.println("sono nel catch");
                        currentResult=PhaseResult.DISCONNECTED;
                        for(Turn notDisconnected : turn){
                            System.out.println("sono nel for");
                            if(notDisconnected != disconnected){
                                System.out.println("mando il messaggio");
                                notDisconnected.getOwner().getView().aPlayerHasDisconnectedMessage(disconnected.getOwner());
                            }
                            getBoard().removePlayerFromList(notDisconnected.getOwner());
                            removeTurnFromList(notDisconnected);
                        }
                        return;
                    }

                }while(newPosition==null || !(getBoard().isInside(newPosition) && (getBoard().isEmpty(newPosition))));
                if(newPosition!=null)
                    getBoard().getPlayer(i).getWorker()[j-1].forced(newPosition);
                turn[i - 1].getOwner().getView().notYourTUrnMessage();

            }
        }
    }

    /**
     * simulates user's input in tests cases
     * @return an user's action
     */
    @Deprecated
    private int[] getFromPlayer(){
        return TestActionProvider.getProvider().getNextMove();
    }
}

