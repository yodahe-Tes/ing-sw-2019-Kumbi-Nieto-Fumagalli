package model;

/**
 * a class that models a movement worker action
 * @Author Fumagalli
 */

public class MovementAction {

    final BoardWorker worker;
    final int[] destination;

    /**
     * constructor
     * @param worker is the worker that is going to move
     * @param destination is the destination where the worker is going
     */
    public MovementAction(BoardWorker worker, int[] destination) {
        this.worker = worker;
        this.destination = destination;
    }

    public BoardWorker getWorker() {
        return worker;
    }

    public int[] getDestination() { return destination; }

}

