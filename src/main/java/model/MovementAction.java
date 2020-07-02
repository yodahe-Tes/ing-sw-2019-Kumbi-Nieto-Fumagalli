package model;

/**
 * A class that models a movement worker action
 * @Author Fumagalli
 */

public class MovementAction {

    final BoardWorker worker;
    final int[] destination;

    /**
     * Constructor
     * @param worker is the worker that is going to move
     * @param destination is the destination where the worker is going
     */
    public MovementAction(BoardWorker worker, int[] destination) {
        this.worker = worker;
        this.destination = destination;
    }

    /**
     * Getter for the worker moving
     * @return the worker
     */
    public BoardWorker getWorker() {
        return worker;
    }

    /**
     * Getter for the destination of the movement
     * @return
     */
    public int[] getDestination() { return destination; }

}

