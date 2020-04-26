package model;

/** This set of interfaces helps to implements the class power
 */

interface BuildingRule {
    public boolean doCheckRule (BoardWorker worker,int[] position);
}
