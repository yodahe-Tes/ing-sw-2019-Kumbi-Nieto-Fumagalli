package controller;

import model.BuildingPhase;
import model.MovementPhase;

/**
 * A class to manage move and build phases
 * @author Fumagalli
 */

public class Turn {

    private final MovementPhase move;
    private final BuildingPhase build;
    private final MovementRuleChecker movCheck;
    private final BuildingRuleChecker buiCheck;

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
        this.movCheck = movCheck;
        this.buiCheck = buiCheck;
    }

    public PhaseResult doTurn(){
        
        move.doMovement();

        build.doBuild();

        return PhaseResult.NEXT;
    }



}
