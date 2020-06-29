package Network;


public interface ClientStatus  {
    /**
     * The method that close the connection from a chosen server
     */
    void closeConnection();

    void addObs(Observer<String> observer);

    /**
     * A method that overide then send method and send a message
     * @param message contains the message
     */
    void asyncSend(Object message);

}
