package model;

class DefaultMovementPhase implements MovementPhase{
    public enum doMovementPhase(BoardWorker worker,int[] position){

        Square position = new Square();
        Square myPosition = new Square();

        if (((position.getFloor())-(myPosition.getFloor()))>=2){
            return DEFEAT;
        } else if (position.hasDome()){
            return DEFEAT;
        }else if (newPosition == oppWorkerPosition1){
            return DEFEAT;
        }else if (newPosition == oppWorkerPosition2) {
            return DEFEAT;
        }else{
            return PROSSIMO;
        }
    }
}
