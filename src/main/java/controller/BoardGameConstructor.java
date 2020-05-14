package controller;

import model.*;

import java.util.ArrayList;

/**
 * @author Fumagalli
 * Class that constructs the model and turn-related classes for a new game
 */
public class BoardGameConstructor {


    /**
     * this method constructs all model's and turn related classes for starting a new game, with the TurnConstruction's help
     * @param playerName are the players' nicknames
     * @return the class that will manage the turns' order and allows to start the game
     */

    public static TurnManager construct(String[] playerName){

        Player[] players = new Player[playerName.length];
        for(int i=0; i<playerName.length; i++)
            players[i]=new Player(playerName[i]);

        //creates board
        Board board=new Board(players);

        //Chooses gods
        TurnConstruction construct = new TurnConstruction(players, board);


        return turnConstruct(construct);

    }

    /**
     * a debug method that allows to start the game with chosen gods
     * @param playerName the nickname of the players
     * @param playerGods the IDs of the chosen gods
     * @return the Turn manager class that manages the turns of the initialized board game
     */
    public static TurnManager construct(String[] playerName, int[] playerGods){

        //Initializes players and workers
        ArrayList<Player> playerList = new ArrayList<Player>();
        for(String playerNick : playerName)
            playerList.add(new Player(playerNick));
        Player[] players = new Player[1];
        players = playerList.toArray(players);

        //creates board
        Board board=new Board(players);

        //passes gods to TurnConstructor
        TurnConstruction construction=new TurnConstruction(players, board, playerGods);

        return turnConstruct(construction);
    }

    /**
     * inner method that initializes the turns
     * @param construct the constructor that has memorized the game's gods and players
     * @return the Turn Manager of the completed game
     */

    private static TurnManager turnConstruct(TurnConstruction construct){

        //initialises players' movement phase
        MovementPhase[] mPhases = construct.createMovementPhase();

        //initializes players' building phase
        BuildingPhase[] bPhases = construct.createBuildingPhase();

        //creates turns
        ArrayList<Turn> turns = new ArrayList<Turn>();
        for(int i=0; i<bPhases.length; i++){
            turns.add(new Turn(mPhases[i], bPhases[i]));
        }

        //creates turn manager
        Turn[] finishedTurns = new Turn[1];

        finishedTurns  = turns.toArray(finishedTurns);

        return new TurnManager(finishedTurns);
    }
}



