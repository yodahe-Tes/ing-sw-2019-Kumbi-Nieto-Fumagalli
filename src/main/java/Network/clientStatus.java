package Network;

import jdk.internal.event.Event;

public interface clientStatus {
    void closeConnection();

    void addObs(Observer<String> observer);

    void asyncSend(Event message);

}
