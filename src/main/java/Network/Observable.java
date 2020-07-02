package Network;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nieto
 *
 * A Class that models the observer pattern's observer part
 */
public class Observable<T> {

    private final List<Observer<T>> obss = new ArrayList<>();

    public void addObs(Observer<T> observer) {
        synchronized (obss) {
            obss.add(observer);
        }
    }

    public void removeObs(Observer<T> observer) {
        synchronized (obss) {
            obss.remove(observer);
        }
    }

    /**
     * A method called by the CLiView class to notify that something changed
     */
    public void notify(T message) {
        synchronized (obss) {
            for (Observer<T> observer : obss) {
                observer.updateCli(message);
            }
        }
    }
}
