import Network.ClientSide;
/**
 * @author Nieto
 *A class that starts the client
 *
 */
public class OpenClient
{
    public static void main(String[] args){

        System.out.println("Welcome to Santorini!");

        ClientSide client = new ClientSide();
            client.run();
            System.out.println("Now the game is closing.");
    }
}