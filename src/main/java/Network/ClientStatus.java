package Network;
/**
 * @author Nieto
 *
 *
 */

public interface ClientStatus  {

    void close();

    void addObs(Observer<String> observer);

    void asyncSend(Object message);

    boolean isActive();

    String getName();
}
