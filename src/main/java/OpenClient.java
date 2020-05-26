import Network.clientSide;

import java.io.IOException;

public class OpenClient
{

    public static void main(String[] args){
        clientSide client = new clientSide("127.0.0.1", 12345);
        try{
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
