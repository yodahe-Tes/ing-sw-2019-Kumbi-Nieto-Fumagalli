package model;

/**
 *
 * A class for the deity Apollo
 * @author Nieto
 */


/**
 *Your Move: Your Worker may
 * move into an opponent Worker’s
 * space by forcing their Worker to
 * the space yours just vacated.
 */

public class Apollo implements Deity, MovementRule{



    int[] newPosition;
    int[] myPosition;
    int[] oppWorkerPosition1;
    int[] oppWorkerPosition2;
    int[] oldPosition;

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
   }

   public boolean doCheckRule(BoardWorker worker, int[] position) {
       Square position = new Square();
       Square myPosition = new Square();

       if (((position.getFloor())-(myPosition.getFloor()))>=2){
            return false;
       }else if (position.hasDome()){
            return false;
       } else if (newPosition == oppWorkerPosition1){
            return true;
        } else if (newPosition == oppWorkerPosition2) {
            return true;
       }else {
            oldPosition = newPosition;
            return true;
       }
   }
}
