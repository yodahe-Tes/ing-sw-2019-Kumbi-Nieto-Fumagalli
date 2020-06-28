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

        for (int j=1;j<=2; j++) {
            for (int i = 1; i <= getBoard().numberPlayers(); i++){
                int[] newPosition;
                do{
                     newPosition = getFromPlayer();
                }while(!(getBoard().isEmpty(newPosition)));
                getBoard().getPlayer(i).getWorker()[j-1].forced(newPosition);
            }
        }

        //TODO: aggiungere messaggio "è morto tizio"
        //TODO: aggiungere possibilità di richiedere la descrizione
        //TODO: ricontrollare i worker nel caso di più move nella stessa phase
        //TODO: modalità a 3 giocatori, test della sconfitta di un giocatore (con god di tipo opponent rule e senza)
        //TODO: controllare javadoc
        //TODO: controllare come ho usato le parti della cliview

        int i=0;
        do{
            currentResult=turn[i].doTurn();

            if(currentResult==PhaseResult.DEFEAT){
                if(turn.length>2){
                    //delete god's power on other players
                    if(turn[i].getOwner().getDeity() instanceof MovementRule){
                        for(Turn opponentTurn : turn){
                            if(opponentTurn!=turn[i]){
                                opponentTurn.getMove().getChecker().removeLooser((MovementRule) turn[i].getOwner().getDeity());
                            }
                        }
                    }
                    if(turn[i].getOwner().getDeity() instanceof BuildingRule){
                        for(Turn opponentTurn : turn){
                            if(opponentTurn!=turn[i]){
                                opponentTurn.getBuild().getChecker().removeLooser((BuildingRule) turn[i].getOwner().getDeity());
                            }
                        }
                    }
                    //delete player
                    getBoard().removePlayerFromList(turn[i].getOwner());
                    removeTurnFromList(turn[i]);
                }
                else{
                    //getPlayer()[i].getView().loosingMessage();
                    currentResult=PhaseResult.VICTORY;
                }
            }

            if(i<turn.length-1)
                i++;
            else
                i=0;
        }while(currentResult != PhaseResult.VICTORY);
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

    @Deprecated
    private int[] getFromPlayer(){
        return TestActionProvider.getProvider().getNextMove();
    }
}

