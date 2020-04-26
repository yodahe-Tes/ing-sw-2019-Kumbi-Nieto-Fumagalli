package model;

class DefaultMovementRule implements MovementRule{
    public boolean doCheckRule(BoardWorker worker, int... position) {
        return false;
    }
}
