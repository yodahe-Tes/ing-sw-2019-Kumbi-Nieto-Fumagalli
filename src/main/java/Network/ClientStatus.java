package Network;
/**
 * @author Nieto
 *
 *An interface that helps the handling of the connection during the whole communication
 */

public interface ClientStatus  {

    void close();

    void addObs(Observer<String> observer);

    void asyncSend(Object message);

    boolean isActive();

    String getName();
}
