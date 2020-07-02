package controller;

import model.*;

/**
 * A class to manage move and build phases
 * @author Fumagalli
 */

public class Turn {

    private final MovementPhase move;
    private final BuildingPhase build;

    /**
     * Constructor
     * @param move the movement phase of the turn
     * @param build the build phase of the turn
     */

    public Turn(MovementPhase move, BuildingPhase build) {
        this.move=move;
        this.build=build;
    }

    /**
     * A method that models the turn execution
     * @return a PhaseResult enum that express the turn's outcome: VICTORY, DEFEAT or nothing (so NEXT turn)
     */
    public PhaseResult doTurn(){

        MovementPhaseResult moved = move.doMovement();

        PhaseResult result = moved.getResult();

        if(result!=PhaseResult.NEXT){
            //notify user of the game's end
            return result;
        }
        if(result==PhaseResult.NEXT)
            build.doBuild(moved.getWorker());
        if(result==PhaseResult.VICTORY) {
            return result;
        }

        getOwner().getView().notYourTUrnMessage();
        return result;
    }

    /**
     * A method to obtain the owner of a chosen worker
     * @return the player's name
     */
    public Player getOwner(){
        return build.getOwner();
    }

    public Board getBoard(){
        return build.getBoard();
    }

    public BuildingPhase getBuild() {
        return build;
    }

    public MovementPhase getMove() {
        return move;
    }
}
