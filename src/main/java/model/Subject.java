package model;

/**
 * @author Fumagalli
 * An interface that models the observer pattern's subject part
 */

public interface Subject {

    /**
     * a method that allows the subject to add an observer
     */
    void attach(Observer o);

    /**
     * a method that allows the subject to remove an observer
     */
    void detach(Observer o);

    /**
     * a method that notifies the observers that data were changed
     */
    void notifyObservers();


}
