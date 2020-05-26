package controller;

import model.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Fumagalli
 * a class that at the game's start choose a random god and initialize all of turn gods-related parts
 */

public class TurnConstruction {

    /*
            GODS TABLE

            ID  GOD         PHASE       EXTENDS

            0:  ATLAS       PLAYER      BUILDING RULE
            1:  DEMETRA     PLAYER      BUILDING PHASE
            2:  ARTEMIS     PLAYER      MOVEMENT PHASE
            3:  APOLLO      PLAYER      MOVEMENT RULE
            4:  ATHENA      OPPONENT    MOVEMENT PHASE
     */

    //this constant memorize the number of gods implemented
    private static final int godSet = 5   ;

    //constants that create a bond between gods'names and their ID
    private static final int atlas = 0;
    private static final int demetra = 1;
    private static final int artemis = 2;
    private static final int apollo = 3;
    private static final int athena = 4;


    //actual variables used for initializing phases
    private final Player[] player;

    private final Board board;

    private ArrayList<Integer> pickedGods;


    /**
     * constructor
     * @param player are the player involved in the game
     * @param board the board where the play will be played
     */
    public TurnConstruction(Player[] player, Board board){

        this.player=player;
        this.board=board;


        pickedGods = new ArrayList<Integer>();
        int newGod=-1;
        boolean godIsDifferent=true;


       for(int i=0; i< player.length;i++){
           newGod=pickARandomGod();
           do{
               for(int j=0;j<i;j++) {
                   godIsDifferent=godIsDifferent && (newGod!=pickedGods.get(j));
               }
           }while(!godIsDifferent);
           pickedGods.add(newGod);
       }

    }

    /**
     * a constructor that sets the gods inside the 'gods' param as a chosen gods, for test use
     * @param player are the players involved in the game
     * @param board is the board where the game will be played
     * @param gods  are the id of the chosen gods
     */
    public TurnConstruction(Player[] player, Board board, int[] gods){

        this.player=player;
        this.board=board;
        pickedGods = new ArrayList<Integer>();

        for(int element : gods){
            pickedGods.add(element);
        }

    }

    /**
     * side method that randomly choose a god id
     * @return an int between 0 and godSet
     */
    private int pickARandomGod(){
        Random rand = new Random();
        return rand.nextInt(godSet);
    }

    /**
     * this method creates the movement rules at which players will obey and put them inside a player specific rule checker
     * @return an array of movement rule checker which indexes are consistent with the array pickedGods
     */
    private MovementRuleChecker[] createMovementRuleChecker() {

        ArrayList<MovementRule>[] playerRules= new ArrayList[pickedGods.size()];
        for(int i=0;i<playerRules.length;i++)
            playerRules[i] = new ArrayList<MovementRule>();


        for(int i=0; i<pickedGods.size();i++) {

            switch (pickedGods.get(i)) {

                case ( apollo): {
                    Apollo apolloGod = new Apollo(board, player[i]);
                    playerRules[i].add(apolloGod);
                    player[i].setDeity(apolloGod);
                    break;
                }

                case (athena): {
                    Athena athenaGod = new Athena(board, player[i]);
                    player[i].setDeity(athenaGod);

                    for (int j = 0; j < pickedGods.size(); j++) {
                        if (i != j) {
                            playerRules[j].add(athenaGod);
                        }
                    }
                }

                default: {
                    playerRules[i].add(new DefaultMovementRule(board));
                }
            }
        }

        MovementRuleChecker[] newCheckers = new MovementRuleChecker[pickedGods.size()];
        MovementRule[] rul;

        for(int i = 0; i<pickedGods.size(); i++){
            rul = new MovementRule[1];
            rul = playerRules[i].toArray(rul);
            newCheckers[i] = new MovementRuleChecker(rul, player[i]);
        }



        return newCheckers;
    }


    /**
     * this method creates the building rules at which players will obey and put them inside a player specific rule checker
     * @return an array of building rule checker which indexes are consistent with the array pickedGods
     */

    private BuildingRuleChecker[] createBuildingRuleChecker(){

        ArrayList<ArrayList<BuildingRule>> playerRules = new ArrayList<ArrayList<BuildingRule>>();

        for(int i=0;i<player.length;i++){

            playerRules.add(new ArrayList<BuildingRule>());

            switch (pickedGods.get(i)) {

                case (atlas): {
                    Atlas atlasGod = new Atlas(board);
                    player[i].setDeity(atlasGod);
                    playerRules.get(i).add(atlasGod);
                    break;
                }

                default:{
                    playerRules.get(i).add(new DefaultBuildingRule(board));
                }

            }
        }


        ArrayList<BuildingRuleChecker> newCheckers = new ArrayList<BuildingRuleChecker>();
        BuildingRule[] addRules = new BuildingRule[1];

        for(int i = 0; i<pickedGods.size(); i++){
            addRules = playerRules.get(i).toArray(addRules);
            newCheckers.add(new BuildingRuleChecker(addRules , player[i]));
        }

        BuildingRuleChecker[] returnChecker = new BuildingRuleChecker[1];
        returnChecker = newCheckers.toArray(returnChecker);
        return returnChecker;
    }


    /**
     * this method creates the movement phases for every player.
     * @return the array, which indexes are consistent with pickedGod's, that contains initialized movement phases
     */

    public MovementPhase[] createMovementPhase(){

        MovementRuleChecker[] movementCheckers = createMovementRuleChecker();
        ArrayList<MovementPhase> phases= new ArrayList<MovementPhase>();
        DefaultMovingLosingCondition loose = new DefaultMovingLosingCondition(board);
        DefaultVictoryCondition win = new DefaultVictoryCondition(board);

        for(int i = 0; i<player.length;i++){
            switch(pickedGods.get(i)){
                case (artemis):{
                 Artemis artemisGod = new Artemis(movementCheckers[i], loose, win);
                 player[i].setDeity(artemisGod);
                 phases.add(artemisGod);
                 break;
                }
                default:{
                    phases.add(new DefaultMovementPhase(movementCheckers[i], loose, win));
                }
            }
        }

        MovementPhase[] array = new MovementPhase[1];
        array = phases.toArray(array);

        return array;
    }

    /**
     * this method creates the building phases for every player.
     * @return the array, which indexes are consistent with pickedGod's, that contains initialized building phases
     */

    public BuildingPhase[] createBuildingPhase(){
        BuildingRuleChecker[] buildingCheckers = createBuildingRuleChecker();
        ArrayList<BuildingPhase> phases = new ArrayList<BuildingPhase>();
        DefaultBuildingLosingCondition loose = new DefaultBuildingLosingCondition(board);

        for(int i=0; i<player.length; i++){
            switch (pickedGods.get(i)){
                case (demetra):{
                    Demetra demetraGod = new Demetra(board, loose, buildingCheckers[i]);
                    player[i].setDeity(demetraGod);
                    phases.add(demetraGod);
                    break;
                }
                default:{
                    phases.add(new DefaultBuildingPhase(board, buildingCheckers[i], loose));
                }
            }
        }
        BuildingPhase[] returnPhases = new BuildingPhase[1];
        returnPhases = phases.toArray(returnPhases);
        return returnPhases;
    }

}
