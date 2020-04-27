package model;

/**
 * @author Fumagalii
 * An interface that describes a rule applied in deciding if a worker can build on a square or not
 */

public interface BuildingRule {

    public boolean doCheckRule(BoardWorker worker, int row, int column);
}
