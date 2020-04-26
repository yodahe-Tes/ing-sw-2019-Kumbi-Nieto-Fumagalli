package model;

import java.util.Observer;

/**
 * A class for the deity Athena
 * @author Nieto
 */

/**
 *Opponent’s Turn: If one of your
 * Workers moved up on your last
 * turn, opponent Workers cannot
 * move up this turn
 */

class Athena implements Deity, MovementRule, Observer {

    int[] newPosition;
    int[] myPosition;
    int[] oppWorkerPosition1 ;
    int[] oppWorkerPosition2 ;

    /**
     * class constructor
     * @param myWorker is the selected worker that is going to move
     * @param position is the position selected to move
     * @param player is the actual player moving
     */

    public Apollo (BoardWorker myWorker, int [] position, Board player){
        newPosition = position;
        myPosition = myWorker.getPosition();
        int i=0;

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
    };
    boolean conditionFulfilled;

    public boolean doCheckRule(BoardWorker worker, int[] position) {
        //updating...

    }

    public void update();

}
