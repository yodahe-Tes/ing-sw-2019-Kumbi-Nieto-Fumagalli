package controller;

import model.*;

import java.util.ArrayList;

/**
 * @author Fumagalli
 * a class that manages the game turns
 */

public class TurnManager {

    private Turn[] turn;
    PhaseResult currentResult = PhaseResult.DEFEAT;
    int turnNumber=0;


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

        //informs the players about their and opponent's god cards and places the workers on the board

        gettingStarted();


        //starts the turn cycle

        do{
            // does the actual turn
            currentResult=turn[turnNumber].doTurn();

            if(currentResult==PhaseResult.DEFEAT){
                //if someone loose
                playerLost(turn[turnNumber]);
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
            if(turnNumber<turn.length-1)
                turnNumber++;
            else
                turnNumber=0;
            //until someone wins
        }while(currentResult != PhaseResult.VICTORY);
}


    /**
     * method for getting the players
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
     * removes selected turn from turn list when the associated player looses
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
     * removes a player from the game
     * @param looser is the turn associated with the looser player
     */
    private void playerLost(Turn looser){

        if(turn.length>2){
            //if there are more than two player delete god's power on other players
            for(Turn opponentTurn : turn) {
                if (opponentTurn != looser){
                    opponentTurn.getOwner().getView().aPlayerHasLostmessage(looser.getOwner());
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
        }
        currentResult=PhaseResult.VICTORY;
    }

    private void gettingStarted(){
        //informs every player of gods choice
        for(Turn player : turn){
            player.getOwner().getView().assignedGodMessage();
            for(Turn opponent : turn){
                if(opponent != player){
                    player.getOwner().getView().otherPlayersGod();
                }
            }
            player.getOwner().getView().informType();
        }

        //gets the workers' starting positions for every player
        for (int j=1;j<=2; j++) {
            for (int i = 1; i <= getBoard().numberPlayers(); i++){
                int[] newPosition;
                do{
                    newPosition = turn[i-1].getOwner().getView().initialPositionQuery(j);
                    System.out.println(newPosition[0]+","+newPosition[1]);

                }while(getBoard().isInside(newPosition) && !(getBoard().isEmpty(newPosition)));
                getBoard().getPlayer(i).getWorker()[j-1].forced(newPosition);
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

