package model;

interface MovementRule {
    public boolean doCheckRule(BoardWorker worker, int [] position);
}
