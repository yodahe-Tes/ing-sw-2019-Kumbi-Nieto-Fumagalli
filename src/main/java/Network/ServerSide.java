package Network;

import View.CliView;
import controller.BoardGameConstructor;
import controller.TurnManager;
import model.Player;
import java.io.IOException;
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
    private static ExecutorService pool = Executors.newFixedThreadPool(128);
    //A store fo all the threads and a pool to execute them
    public Map<String, ClientStatus> queue = new HashMap<>();
    public Map<String, ClientStatus> queue2 = new HashMap<>();
    public ArrayList<Socket> clientSocket = new ArrayList();
    int keyNum = 0;
    String keyString;
    private Map<ClientStatus, ClientStatus> gameConnection = new HashMap<>();

    public ServerSide() throws IOException {
        this.listener = new ServerSocket(port);
    }

    /**
     * A method to deregister the client connection and set free the spot to start
     * a new game
     *
     * @param s client socket
     */

    public synchronized void deregisterConnection(ClientStatus s) {
        gameConnection.remove(s);
    }

    /**
     * A method that handles the client connected to the server and set everything to start the game
     *
     * @param s      client managed by the class ClientHandler
     * @param name   the nickname decided from the player
     * @param socket socked used for the connection
     */
    public synchronized void room(ClientHandler s, String name, Socket socket) {

        clientSocket.add(socket);
        keyString = "" + keyNum;
        queue.put(keyString, s);
        keyNum++;


        if (queue.size() == 2) {

            List<String> keys = new ArrayList<>(queue.keySet());
            ClientStatus c1 = queue.get(keys.get(0));

            if (!c1.isActive()) {
                deregisterConnection(c1);

                queue.remove(keys.get(0));

                keys.remove(0);

                return;
            }
            ClientStatus c2 = queue.get(keys.get(1));

            if (!c2.isActive()) {
                deregisterConnection(c2);

                queue.remove(keys.get(1));

                keys.remove(1);

                return;
            }

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

    /**
     * Same class as below with the only difference that here is possible to
     * handle a three players game
     */
    public synchronized void room2(ClientStatus s, String name, Socket socket) {
        clientSocket.add(socket);
        keyString = "" + keyNum;
        queue2.put(keyString, s);
        keyNum++;

        if (queue2.size() == 3) {
            List<String> keys = new ArrayList<>(queue2.keySet());
            ClientStatus c1 = queue2.get(keys.get(0));

            if (!c1.isActive()) {
                deregisterConnection(c1);

                queue2.remove(keys.get(0));

                keys.remove(0);

                return;
            }

            ClientStatus c2 = queue2.get(keys.get(1));

            if (!c2.isActive()) {
                deregisterConnection(c2);

                queue2.remove(keys.get(1));

                keys.remove(1);

                return;
            }

            ClientStatus c3 = queue2.get(keys.get(2));

            if (!c3.isActive()) {
                deregisterConnection(c3);

                queue2.remove(keys.get(2));

                keys.remove(2);

                return;
            }

            ClientStatus[] listaC = {c1, c2, c3};

            Set gg = queue2.keySet();
            Object[] a = gg.toArray();
            String[] players = new String[a.length];
            for (int i = 0; i < players.length; i++) {
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
            gameConnection.put(c1, c3);
            queue2.clear();

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
                ClientHandler clHandler = new ClientHandler(newSocket, this);
                pool.submit(clHandler);
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }
}
