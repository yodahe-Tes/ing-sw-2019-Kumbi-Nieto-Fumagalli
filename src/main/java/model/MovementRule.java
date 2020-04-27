package model;

/**
 * @author Fumagalii
 * An interface that describes a rule applied in deciding if a worker can move on a square or not
 */

public interface MovementRule {

    public boolean doCheckRule(BoardWorker worker, int row, int column);

}
