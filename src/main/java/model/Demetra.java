package model;
import static model.BuildingPhase.doBuildingPhase.*;

/**
 * A class for the deity Demetra
 * @author Nieto
 */

/**
 *Your Build: Your Worker may
 * build one additional time, but not
 * on the same space.
 */
class Demetra implements Deity, BuildingPhase {

    int[] newPosition;
    int[] myPosition;
    int[] oppWorkerPosition1;
    int[] oppWorkerPosition2;
    int[] oldPosition;

    /**
     * class constructor
     *
     * @param myWorker is the selected worker that is going to move
     * @param position is the position selected to move
     * @param player   is the actual player moving
     */

    public Demetra(BoardWorker myWorker, int[] position, Board player) {
        newPosition = position;
        myPosition = myWorker.getPosition();
        int i = 0;

        if (myPosition == (player.getPlayer(1)).workerPosition(1)) {
            i = 1;
        } else if (myPosition == (player.getPlayer(1)).workerPosition(2)) {
            i = 1;
        } else {
            i = 2;
            oppWorkerPosition1 = (player.getPlayer(i)).workerPosition(1);
            oppWorkerPosition2 = (player.getPlayer(i)).workerPosition(2);
        }
    }

    public GodType type() {
        return GodType.Player;
    }

    public doBuildingPhase doBuildingPhase(BoardWorker worker, int[] position) {
        Square position = new Square();
        Square myPosition = new Square();

        if (position.hasDome()) {
            return DEFEAT;
        } else if (newPosition == oppWorkerPosition1) {
            return DEFEAT;
        } else if (newPosition == oppWorkerPosition2) {
            return DEFEAT;
        } else if (newPosition==oldPosition){
            return DEFEAT;
        } else {
            oldPosition=newPosition;
            return PROSSIMO;
        }
    }
}
