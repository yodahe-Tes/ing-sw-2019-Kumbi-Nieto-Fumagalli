package controller;

import model.Turn;

/**
 * @author Fumagalli
 * a class that manages the game turns
 */

public class TurnManager {

    private final Turn[] turn;

    /**
     * constructor
     * @param turn1 is the first to play
     * @param turn2 is the second to play
     */
    public TurnManager(Turn turn1, Turn turn2){
        turn = new Turn[]{turn1, turn2};
    }

    public startGame(){
        for(int i=0; i<=turn.length; i++) {
            turn[i].movePhase();
            turn[i].buildPhase();
            if (i==turn.length)
                    i=0;
        }
    }
}
