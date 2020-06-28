package Network;


public interface ClientStatus  {

    void closeConnection();

    void addObs(Observer<String> observer);

    void asyncSend(Object message);


}
