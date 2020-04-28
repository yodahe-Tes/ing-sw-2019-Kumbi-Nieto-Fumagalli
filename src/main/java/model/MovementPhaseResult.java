package model;

import controller.PhaseResult;

/**
 * a class that collects the values returned by a MovementPhase
 * @author Fumagalli
 */

public class MovementPhaseResult{
    BoardWorker worker;
    PhaseResult result;

    /**
     * constructor
     * @param worker a reference to a worker
     * @param result the result of the phase
     */
    protected MovementPhaseResult(BoardWorker worker, PhaseResult result){
        this.worker = worker;
        this.result = result;
    }

    public BoardWorker getWorker(){ return worker;
    }

    public PhaseResult getResult(){return result;}
}