package Network;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

/***
 * A class that handles the input from the clients
 * @author Nieto
 */
public class ClientHandler extends Observable<String> implements ClientStatus, Runnable {
    private final Socket socket;
    private ObjectOutputStream out;
    private final ServerSide server;


    private boolean active = true;

    public ClientHandler(Socket socket, ServerSide server)  {
        this.socket = socket;
        this.server = server;
    }

    public synchronized boolean isActive(){
        return active;
    }

    private synchronized void send(Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch(IOException e){
            System.err.println(e.getMessage());
        }
    }


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

        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();

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
        Scanner in;
        String name;

        int numberOfPlayers;
        try{
            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            //Ask to the new connected player what kind of game he wants to play
            //  send("Choose if 2 or 3 players");
            //    String pick = in.nextLine();
            //    System.out.println(pick);
         /*    numberOfPlayers = Integer.parseInt(pick);
            if (numberOfPlayers == 3) {
                send("Welcome!\nWhat is your name?");
                String read = in.nextLine();
                name = read;
                server.room2(this, name);
                while (isActive()) {
                    read = in.nextLine();
                    notify(read);
                }
            } else {*/


            String read = in.nextLine();
            name = read;
            int players = 0;
            while (players==0){
                try{
                    read=in.nextLine();
                    players=Integer.parseInt(read);
                }
                catch (Exception e ){
                    players=0;
                }
            }

            System.out.println(players);

            server.room(this,name, socket);

            while (isActive()) {
                read = in.nextLine();
                if(read.equals("disconnect"))
                    throw new IOException();
                notify(read);
                System.err.println("notified" + read);
            }

        } catch (NoSuchElementException | IOException e) {
            active=false;
            System.out.println("active set falseZ");
            System.err.println("Error!" + e.getMessage());
        } finally {
                close();
        }
    }

}