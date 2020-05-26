package model;

//TODO: Javadoc

/**
 * a class used during tests to simulate the user's input
 */

public class TestActionProvider {

    private static TestActionProvider singleton = new TestActionProvider();

    private BuildingAction[] build;
    int buildActionsCounter;

    private int[][] move;
    int moveActionsCounter;

    private boolean[] booleanRequest;
    int requestsCounter;

    /**
     * constructor: initializes the singleton
     */
    private TestActionProvider(){
        buildActionsCounter=0;
        moveActionsCounter=0;
        requestsCounter=0;
    }

    /**
     * public getter for the singleton
     * @return
     */
    public static TestActionProvider getProvider(){
        return singleton;
    }

    /**
     * set the movement actions that will be sent in the array's order to the caller
     * @param actions is the array representing the movement actions
     */
    public void setMovingActions(int[][] actions){
        move=actions;
        moveActionsCounter=0;
    }

    /**
     * set the building actions that will be sent in the array's order to the caller
     * @param actions is the array representing the building actions
     */
    public void setBuildingActions(BuildingAction[] actions){
        build=actions;
        buildActionsCounter=0;
    }

    /**
     * set the boolean feedback that will be sent in the array's order to the caller
     * @param requests is the array that contains the booleans
     */
    public void setBooleanRequest(boolean[] requests){
        booleanRequest=requests;
        requestsCounter =0;
    }

    /**
     * provides to the caller a building action previously chosen
     * @return last never used building action in the build array
     */
    public BuildingAction getNextBuild(){
        buildActionsCounter= buildActionsCounter+1;
        return build[(buildActionsCounter-1)];
    }
    /**
     * provides to the caller a movement action previously chosen
     * @return last never used movement action in the move array
     */
    public int[] getNextMove(){
        moveActionsCounter++;
        return move[(moveActionsCounter-1)];
    }

    /**
     * provides to the caller a boolean previously chosen
     * @return last never used boolean in the booleanRequest array
     */
    public boolean getNextAnswer(){
        requestsCounter++;
        return booleanRequest[(requestsCounter-1)];
    }

}
