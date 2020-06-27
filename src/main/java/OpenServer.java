import Network.ServerSide;


import java.io.IOException;

/**
 * Hello world!
 *
 */
public class OpenServer
{
    public static void main( String[] args )
    {
        ServerSide server;
        try {
            server = new ServerSide();
            System.out.println("SERVER waiting");
            server.run();
        } catch (IOException e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        }
    }
}