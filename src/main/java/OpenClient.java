import Network.ClientSide;
/**
 * @author Nieto
 *
 *
 */
public class OpenClient
{


    public static void main(String[] args){

        System.out.println("Welcome to Santorini!");

        ClientSide client = new ClientSide();
            client.run();
    }
}