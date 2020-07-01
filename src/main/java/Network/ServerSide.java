package Network;
import View.CliView;
import controller.BoardGameConstructor;
import controller.TurnManager;
import model.Player;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/***
 * A class that handles the server, managing connections to it and by a method the initialization of the game
 * @author Nieto
 */

public class ServerSide {
    //static ServerSocket variable
    private static ServerSocket listener;
    //socket server port on which it will listen
    private static int port = 12345;
    //A store fo all the threads and a pool to execute them
    public Map<String, ClientStatus> queue = new HashMap<>();
    public ArrayList<Socket> clientSocket = new ArrayList();
    private ArrayList<InputStream> InputStr = new ArrayList();
    private Map<ClientStatus, ClientStatus> gameConnection = new HashMap<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(128);
    int keyNum=0;
    String keyString;

    public ServerSide() throws IOException {
        this.listener = new ServerSocket(port);
    }

    /**
     * A method to deregister the client connection and set free the spot to start
     * a new game
     * @param s client socket
     */

    public synchronized void deregisterConnection(ClientStatus s) {
        ClientStatus opponent = gameConnection.get(s);
        if (opponent != null) opponent.close();
        gameConnection.remove(s);
        gameConnection.remove(opponent);
        Iterator<String> iterator = queue.keySet().iterator();
        while (iterator.hasNext()) {
            if (queue.get(iterator.next()) == s) iterator.remove();
        }
    }

    /*public synchronized void login (ClientHandler s, Socket socket) throws InterruptedException {

          s.send("Welcome!\nWhat is your name?");

          String name = s.in.nextLine();

         clientSocket.add(socket);
         queue.put(name,s);
       if (queue.size()==2){
         Thread t = new Thread(new Runnable() {
            @Override
             public void run() {
                try {
                      room();
                 } catch (InterruptedException e) {
                    e.printStackTrace();
                 }
            }
          });
          t.start();
          }
    }*/

    /**
     * A method that handles the client connected to the server and set everything to start the game
     * @param s client managed by the class ClientHandler
     * @param name the nickname decided from the player
     * @param socket socked used for the connection
     */
    public synchronized void room(ClientHandler s, String name, Socket socket) {
        clientSocket.add(socket);
        keyString = ""+keyNum;
        queue.put(keyString, s);
        keyNum++;
        if (queue.size() == 2) {

            List<String> keys = new ArrayList<>(queue.keySet());
            ClientStatus c1 = queue.get(keys.get(0));
            ClientStatus c2 = queue.get(keys.get(1));

            ClientStatus[] listaC = {c1, c2};

            Set ss = queue.keySet();
            Object[] a = ss.toArray();
            String[] players = new String[a.length];
            for (int i = 0; i < players.length; i++) {
                players[i] = listaC[i].getName();
            }
            final TurnManager newTurnManager = BoardGameConstructor.construct(players);
            Player[] player = newTurnManager.getPlayer();
            CliView jj;
            for (int i = 0; i < (newTurnManager.getPlayer()).length; i++) {
                jj = new CliView(listaC[i], newTurnManager.getBoard());
                (player[i]).addCliView(jj);
            }
            gameConnection.put(c1, c2);
            gameConnection.put(c2, c1);
            queue.clear();


            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    newTurnManager.startGame();
                }
            });
            t.start();

        }
    }


    public  synchronized void room2 (ClientStatus s, String name, Socket socket){
        clientSocket.add(socket);
        keyString = ""+ keyNum;
        queue.put(keyString, s);
        keyNum++;

        if (queue.size()==3){
            List<String> keys = new ArrayList<>(queue.keySet());
            ClientStatus c1 = queue.get(keys.get(0));
            ClientStatus c2 = queue.get(keys.get(1));
            ClientStatus c3 = queue.get(keys.get(2));
            ClientStatus [] listaC = {c1, c2, c3};

            Set gg = queue.keySet ();
            Object[] a = gg.toArray ();
            String[] players = new String[a.length];
            for (int i = 0; i < players.length; i++){
                players[i] = listaC[i].getName();
            }

            final TurnManager newTurnManager = BoardGameConstructor.construct(players);
            Player[] player = newTurnManager.getPlayer();
            CliView cv;
            for (int i = 0; i < (newTurnManager.getPlayer()).length; i++) {
                cv = new CliView(listaC[i], newTurnManager.getBoard());
                (player[i]).addCliView(cv);
            }

            gameConnection.put(c1, c2);
            gameConnection.put(c2, c3);
            gameConnection.put(c1,c3);
            queue.clear();

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    newTurnManager.startGame();
                }
            });
            t.start();
        }
    }


    public void run() {
        while (true) {
            try {
                Socket newSocket = listener.accept();
                ClientHandler clHandler = new ClientHandler(newSocket, this );
                pool.submit(clHandler);
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }
}
