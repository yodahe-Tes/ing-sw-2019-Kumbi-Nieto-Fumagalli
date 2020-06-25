package Network;

public interface Observer<T> {

    /**
     *
     * updates boardimage after notification from Model
     */
    void updateCli(T message);
}
