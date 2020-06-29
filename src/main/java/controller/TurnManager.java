package controller;

import model.*;

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

        //gets the workers' starting positions for every player
        for (int j=1;j<=2; j++) {
            for (int i = 1; i <= getBoard().numberPlayers(); i++){
                int[] newPosition;
                do{
                     newPosition = turn[i].getOwner().getView().initialPositionQuery(j);
                }while(!(getBoard().isEmpty(newPosition)));
                getBoard().getPlayer(i).getWorker()[j-1].forced(newPosition);
            }
        }

        //starts the turn cycle
        int turnNumber=0;
        do{
            // does the actual turn
            currentResult=turn[turnNumber].doTurn();

            if(currentResult==PhaseResult.DEFEAT){
                //if someone loose
                if(turn.length>2){
                    //delete god's power on other players
                    if(turn[turnNumber].getOwner().getDeity() instanceof MovementRule){
                        for(Turn opponentTurn : turn){
                            if(opponentTurn!=turn[turnNumber]){
                                opponentTurn.getMove().getChecker().removeLooser((MovementRule) turn[turnNumber].getOwner().getDeity());
                            }
                        }
                    }
                    if(turn[turnNumber].getOwner().getDeity() instanceof BuildingRule){
                        for(Turn opponentTurn : turn){
                            if(opponentTurn!=turn[turnNumber]){
                                opponentTurn.getBuild().getChecker().removeLooser((BuildingRule) turn[turnNumber].getOwner().getDeity());
                            }
                        }
                    }
                    //delete player
                    getBoard().removePlayerFromList(turn[turnNumber].getOwner());
                    removeTurnFromList(turn[turnNumber]);
                    turnNumber--;
                }
                else{
                    //the only remaining player wins
                    getPlayer()[turnNumber].getView().loserMessage();
                    currentResult=PhaseResult.VICTORY;
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
     * simulates user's input in tests cases
     * @return an user's action
     */
    @Deprecated
    private int[] getFromPlayer(){
        return TestActionProvider.getProvider().getNextMove();
    }
}

