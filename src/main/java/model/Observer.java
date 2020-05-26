package model;

/**
 * @author Fumagalli
 * An interface that models the observer pattern's observer part
 */


public interface Observer {

    /**
     *A method called by the subject class to notify that something changed
     */
    void update();
}
