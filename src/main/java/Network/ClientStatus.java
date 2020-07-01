package Network;


public interface ClientStatus  {

    void close();

    void addObs(Observer<String> observer);

    void asyncSend(Object message);

    boolean isActive();

}
