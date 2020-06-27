import Network.ClientSide;

import java.io.IOException;

public class OpenClient
{

    public static void main(String[] args){
        ClientSide client = new ClientSide("127.0.0.1", 12345);
        try{
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
