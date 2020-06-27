import Network.serverSide;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class OpenServer
{
    public static void main( String[] args )
    {
        serverSide server;
        try {
            server = new serverSide();
            System.out.println("SERVER waiting");
            server.run();
        } catch (IOException e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        }
    }
}