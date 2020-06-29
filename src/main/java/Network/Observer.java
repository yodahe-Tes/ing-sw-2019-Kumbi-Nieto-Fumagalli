package Network;

/**
 * @author Nieto
 * An interface that models the observer pattern's observer part
 */

public interface Observer<T> {

    /**
     * updates boardimage after notification from Model
     */
    void updateCli(T message);

}
