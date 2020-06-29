package model;

/**
 * a class that models a building worker action
 */
public class BuildingAction {
    private final int[] destination;
    private final boolean forceBuildDome;

    /**
     * a constructor for a standard building action
     * @param destination is the destination where the worker is going to build
     */
    public BuildingAction(int[] destination){
        this.destination=destination;
        forceBuildDome = false;
    }

    /**
     * a constructor that specify if the player wants to force-build a dome
     * @param destination is the destination where the worker is going to build
     * @param forceBuildDome represents if the player wants to build a dome
     */

    public BuildingAction(int[] destination, boolean forceBuildDome) {
        this.destination = destination;
        this.forceBuildDome = forceBuildDome;
    }

    public int[] getDestination() {
        return destination;
    }

    public boolean isForceBuildDome() {
        return forceBuildDome;
    }
}
