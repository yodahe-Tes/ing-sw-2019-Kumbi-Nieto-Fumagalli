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
            4:  ATHENA      OPPONENT    MOVEMENT RULE
            5:  HAEPHESTUS  PLAYER      BUILDING PHASE
            6:  MINOTAUR    PLAYER      MOVEMENT RULE
            7:  PAN         PLAYER      VICTORY CONDITION
            8:  PROMETHEUS  PLAYER      MOVEMENT PHASE
            9:  APHRODITE   OPPONENT    MOVEMENT RULE
            10: HESTIA      PLAYER      BUILDING PHASE
            11: HYPNUS      OPPONENT    MOVEMENT RULE
            12: LIMUS       OPPONENT    BUILDING RULE
            13: TRITON      PLAYER      MOVEMENT PHASE
     */

    //this constant memorize the number of gods implemented
    private static final int godSet = 14   ;

    //constants that create a bond between gods'names and their ID
    private static final int atlas = 0;
    private static final int demetra = 1;
    private static final int artemis = 2;
    private static final int apollo = 3;
    private static final int athena = 4;
    private static final int haephestus = 5;
    private static final int minotaur = 6;
    private static final int pan = 7;
    private static final int prometheus = 8;
    private static final int aphrodite = 9;
    private static final int hestia = 10;
    private static final int hypnus = 11;
    private static final int limus = 12;
    private static final int triton = 13;



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
        boolean godIsDifferent;


       for(int i=0; i< player.length;i++){
           do{
               newGod=pickARandomGod();
               godIsDifferent=true;
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

        MovementRule rule;

        for(int i=0; i<pickedGods.size();i++) {

            switch (pickedGods.get(i)) {

                case (apollo): {
                    rule = new Apollo(board, player[i]);
                    break;
                }

                case (athena): {
                    rule = new Athena(board, player[i]);
                    break;
                }

                case (aphrodite): {
                    rule = new Aphrodite(board, player[i]);
                    break;
                }

                case (hypnus): {
                    rule = new Hypnus(board, player[i]);
                    break;
                }

                case (minotaur): {
                    rule = new Minotaur(board, player[i]);
                    break;
                }

                default: {
                    rule = new DefaultMovementRule(board);
                    break;
                }
            }




            if(rule instanceof Deity){
                player[i].setDeity((Deity) rule);
            }
            if(rule.isOpponent()){
                for (int j = 0; j < pickedGods.size(); j++) {
                    if (i != j) {
                        playerRules[j].add(rule);
                    }
                }
                playerRules[i].add(new DefaultMovementRule(board));
            }
            else
                playerRules[i].add(rule);
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

    private BuildingRuleChecker[] createBuildingRuleChecker() {

        ArrayList<BuildingRule>[] playerRules = new ArrayList[pickedGods.size()];
        for (int i = 0; i < playerRules.length; i++)
            playerRules[i] = new ArrayList<>();

        for (int i = 0; i < player.length; i++) {

            BuildingRule rule;

            switch (pickedGods.get(i)) {

                case (atlas): {
                    rule = new Atlas(board);
                    break;
                }

                case (limus): {
                    rule = new Limus(board, player[i]);
                    break;
                }

                default: {
                    rule = new DefaultBuildingRule(board);
                    break;
                }
            }


            if (rule instanceof Deity) {
                player[i].setDeity((Deity) rule);
            }
            if (rule.isOpponent()) {
                for (int j = 0; j < pickedGods.size(); j++) {
                    if (i != j) {
                        playerRules[j].add(rule);
                    }
                }
                playerRules[i].add(new DefaultBuildingRule(board));
            } else
                playerRules[i].add(rule);
        }
        BuildingRuleChecker[] newCheckers = new BuildingRuleChecker[pickedGods.size()];
        BuildingRule[] rul;

        for(int i = 0; i<pickedGods.size(); i++){
            rul = new BuildingRule[1];
            rul = playerRules[i].toArray(rul);
            newCheckers[i] = new BuildingRuleChecker(rul, player[i]);
        }

        return newCheckers;
    }

    private VictoryConditionChecker[] createVictoryChecker(){

        VictoryConditionChecker[] checkers = new VictoryConditionChecker[player.length];
        VictoryCondition[] condition;

        for(int i=0;i<player.length;i++){
            if(pickedGods.get(i)==pan){
                condition = new VictoryCondition[2];
                condition[1] = new Pan(board);
                player[i].setDeity((Deity) condition[1]);

            }
            else
                condition = new VictoryCondition[1];

            condition[0]=new DefaultVictoryCondition(board);

            checkers[i]= new VictoryConditionChecker(condition);
        }
        return checkers;
    }

    /**
     * this method creates the movement phases for every player.
     * @return the array, which indexes are consistent with pickedGod's, that contains initialized movement phases
     */

    public Turn[] createTurns(){
        BuildingPhase[] buildingPhases=createBuildingPhase();
        MovementRuleChecker[] movementCheckers = createMovementRuleChecker();
        ArrayList<MovementPhase> phases= new ArrayList<MovementPhase>();
        DefaultMovingLosingCondition loose = new DefaultMovingLosingCondition(board);
        VictoryConditionChecker[] win = createVictoryChecker();

        MovementPhase phase;

        for(int i = 0; i<player.length;i++){
            switch(pickedGods.get(i)) {

                case (artemis): {
                    phase = new Artemis(movementCheckers[i], loose, win[i]);
                    break;
                }

                case (prometheus): {
                    phase = new Prometheus(movementCheckers[i], loose, win[i], buildingPhases[i].getChecker(), board);
                    break;
                }

                case (triton): {
                    phase = new Triton(movementCheckers[i], loose, win[i]);
                    break;
                }

                default: {
                    phase = new DefaultMovementPhase(movementCheckers[i], loose, win[i]);
                    break;
                }
            }
            if (phase instanceof Deity){
                player[i].setDeity((Deity) phase);
            }
            phases.add(phase);

        }

        MovementPhase[] movementPhases = new MovementPhase[1];
        movementPhases = phases.toArray(movementPhases);

        ArrayList<Turn> turns = new ArrayList<Turn>();
        for (int i = 0; i < buildingPhases.length; i++) {
            turns.add(new Turn(movementPhases[i], buildingPhases[i]));
        }

        Turn[] finishedTurns = new Turn[1];
        finishedTurns=turns.toArray(finishedTurns);
        return finishedTurns;
    }

    /**
     * this method creates the building phases for every player.
     * @return the array, which indexes are consistent with pickedGod's, that contains initialized building phases
     */

    public BuildingPhase[] createBuildingPhase(){
        BuildingRuleChecker[] buildingCheckers = createBuildingRuleChecker();
        ArrayList<BuildingPhase> phases = new ArrayList<BuildingPhase>();
        DefaultBuildingLosingCondition loose = new DefaultBuildingLosingCondition(board);
        BuildingPhase phase;

        for(int i=0; i<player.length; i++){
            switch (pickedGods.get(i)){
                case (demetra):{
                    phase = new Demetra(board, loose, buildingCheckers[i]);
                    break;
                }
                case(haephestus):{
                    phase= new Hephaestus(board, buildingCheckers[i], loose);
                    break;
                }
                case (hestia):{
                    phase = new Hestia(board, loose, buildingCheckers[i]);
                    break;
                }
                default:{
                    phase = new DefaultBuildingPhase(board, buildingCheckers[i], loose);
                    break;
                }
            }

            if(phase instanceof Deity)
                player[i].setDeity((Deity) phase);

            phases.add(phase);
        }
        BuildingPhase[] returnPhases = new BuildingPhase[1];
        returnPhases = phases.toArray(returnPhases);
        return returnPhases;
    }

}
