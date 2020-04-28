package controller;

import model.BoardWorker;
import model.BuildingPhase;
import model.MovementPhase;
import model.MovementPhaseResult;

/**
 * A class to manage move and build phases
 * @author Fumagalli
 */

public class Turn {

    private final MovementPhase move;
    private final BuildingPhase build;

    /**
     * construtor
     * @param move the movement phase of the turn
     * @param build the build phase of the turn
     * @param movCheck the movement rules checker of the player
     * @param buiCheck the building rules checker of the player
     */

    public Turn(MovementPhase move, BuildingPhase build, MovementRuleChecker movCheck,BuildingRuleChecker buiCheck) {
        this.move=move;
        this.build=build;
    }

    public PhaseResult doTurn(){
        
        MovementPhaseResult moved = move.doMovement();

        PhaseResult result = moved.getResult();

        if(result==PhaseResult.NEXT)
            build.doBuild(moved.getWorker());
        if(result==PhaseResult.VICTORY) {
            //notify users of the game's end
        }
        return result;
    }



}
