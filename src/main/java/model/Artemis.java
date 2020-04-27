package model;
import static model.MovementPhase.doMovementPhase.*;

/**
 * A class for the deity Artemis
 * @author Nieto
 */

/**
 *Your Move: Your Worker may
 * move one additional time, but not
 * back to its initial space.
 */

class Artemis implements Deity, MovementPhase{

    int[] newPosition;
    int[] myPosition;
    int[] oppWorkerPosition1 ;
    int[] oppWorkerPosition2 ;
    int[] oldPosition;

    /**
     * class constructor
     * @param myWorker is the selected worker that is going to move
     * @param position is the position selected to move
     * @param player is the actual player moving
     */

    public Artemis (BoardWorker myWorker, int [] position, Board player){
        newPosition = position;
        myPosition = myWorker.getPosition();
        int i;

        if (myPosition ==(player.getPlayer(1)).workerPosition(1)){
            i=1;
        } else if (myPosition == (player.getPlayer(1)).workerPosition(2)){
            i=1;
        } else {
            i=2;
            oppWorkerPosition1 = (player.getPlayer(i)).workerPosition(1);
            oppWorkerPosition2 = (player.getPlayer(i)).workerPosition(2);
        }
    }


    public  GodType type(){
        return GodType.Player;
    }

    public doMovementPhase doMovementPhase(BoardWorker worker,int[] position) {

        Square position = new Square();
        Square myPosition = new Square();

        if (((position.getFloor())-(myPosition.getFloor()))>=2){
            return DEFEAT;
        } else if (position.hasDome()){
            return DEFEAT;
        } else if (newPosition == oppWorkerPosition1){
            return DEFEAT;
        } else if (newPosition == oppWorkerPosition2) {
            return DEFEAT;
        } else if (newPosition==oldPosition){
            return DEFEAT;
        } else if ((position.getFloor())==3){
            return VITTORIA;
        } else{
            oldPosition= newPosition;
            return PROSSIMO;

        }
    }
}
