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
        try{
            send("Connection closed!");
        }catch (Exception e){
            System.err.println("error in sending close statement to client");
        }

        try {
            socket.close();
        } catch (Exception e) {
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
        System.out.println("Deregistering client...");
        server.deregisterConnection(this);
        closeConnection();
        System.out.println("Done!");
    }


    @Override
    public void run() {
        lock.lock();
        Scanner in;

        try {
            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        }catch (IOException e){
            System.out.println("never got inputstream");
            close();
            return;
        }
        String read = in.nextLine();
        name = read;
        lock.unlock();
        numberPlayer = 0;
        while (numberPlayer==0){
            try{
                read=in.nextLine();
                numberPlayer=Integer.parseInt(read);
                }
            catch (NullPointerException e ){
                notify("disconnect");
                System.err.println("didn't get number player");
                close();
                return;
            }
        }

        if (numberPlayer == 2) {
            server.room(this, name, socket);
        }else {
            server.room2(this, name, socket);
        }
        while (isActive()) {
            try {
                read = in.nextLine();
                if(read.equals("disconnect")) {
                    System.out.println("red disconnect");
                    active=false;
                }
                notify(read);
                System.err.println("notified" + read);
            }catch (NoSuchElementException e){
                active=false;
                notify("disconnect");
                System.err.println("Error!" + e.getMessage());
                close();
                break;
            }
        }
        System.out.println("finally closing socket");
        notify("disconnect");
        close();

    }

    @Override
    public String getName(){
        lock.lock();
        lock.unlock();
        return name;
    }
}