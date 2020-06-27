package Network;



import Network.Observer;


public interface ClientStatus  {

    void closeConnection();

    void addObs(Observer<String> observer);

    void asyncSend(Object message);


}
