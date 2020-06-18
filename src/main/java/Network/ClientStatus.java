package Network;


import java.io.ObjectInputStream;

public interface ClientStatus {
    void closeConnection();

    void addObs(Observer<String> observer);

    void asyncSend(Object message);

    ObjectInputStream getInputStream();
}
