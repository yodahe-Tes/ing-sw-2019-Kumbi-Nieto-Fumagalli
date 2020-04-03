package model;

/**
 * @author Fumagalli
 * An interface that models the observer pattern's subject part
 */

public interface Subject {

    /**
     * a method that allows the subject to add an observer
     */
    public void attach(Observer o);

    /**
     * a method that allows the subject to remove an observer
     */
    public void detach(Observer o);

    /**
     * a method that notifies the observers that data were changed
     */
    public void notifyObservers();

}
