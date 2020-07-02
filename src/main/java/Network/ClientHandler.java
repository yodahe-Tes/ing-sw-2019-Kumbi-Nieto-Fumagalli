package Network;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 * A class that handles the input from the clients
 * @author Nieto
 */
public class ClientHandler extends Observable<String> implements ClientStatus, Runnable {
    private final Socket socket;
    private ObjectOutputStream out;
    private final ServerSide server;
    private String name;
    private int numberPlayer;
    Lock lock = new ReentrantLock();

    private boolean active = true;

    /**
     * Constructor
     * @param socket client socket
     * @param server server connection
     */
    public ClientHandler(Socket socket, ServerSide server)  {
        this.socket = socket;
        this.server = server;
    }

    public synchronized boolean isActive(){
        return active;
    }

    /***
     * A method to send messages via the socket
     * @param message information to send
     */
    private synchronized void send(Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * A method that closes the socket connection
     */

    private void closeConnection() {
        send("Connection closed!");
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
    }


    @Override
    public void asyncSend(final Object message) {

        new Thread(() -> send(message)).start();

    }

    @Override
    public synchronized void close() {
        closeConnection();
        System.out.println("Deregistering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }


    @Override
    public void run() {
        lock.lock();
        Scanner in;

        try{
            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            String read = in.nextLine();
            name = read;
            lock.unlock();
            numberPlayer = 0;
            while (numberPlayer==0){
                try{
                    read=in.nextLine();
                    numberPlayer=Integer.parseInt(read);
                }
                catch (Exception e ){
                    numberPlayer=0;
                }
            }

            if (numberPlayer == 2) {
                server.room(this, name, socket);
            }else {
                server.room2(this, name, socket);
            }
            while (isActive()) {
                read = in.nextLine();
                if(read.equals("disconnect")) {
                    close();
                    return;
                }
                notify(read);
                System.err.println("notified" + read);
            }

        } catch (NoSuchElementException | IOException e) {
            active=false;
            System.out.println("active set false");
            notify("disconnect");
            System.err.println("Error!" + e.getMessage());

        } finally {
                close();
        }
    }

    @Override
    public String getName(){
        lock.lock();
        lock.unlock();
        return name;
    }
}