package Network;

import controller.BoardGameConstructor;
import controller.TurnManager;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import View.*;
import model.Player;

/***
 * This class implements java server class
 * @author Nieto
 */

public class ServerSide {
    //static ServerSocket variable
    private static ServerSocket listener;
    //socket server port on which it will listen
    private static int port = 9876;
    //A store fo all the threads and a pool to execute them
    private  Map<String, ClientStatus> queue = new HashMap<>();
    private Map<ClientStatus, ClientStatus> gameConnection = new HashMap<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(2);

    /**
     * A method to deregister the client connection and set free the spot to start
     * a new game
     * @param s client socket
     */

    public synchronized void deregisterConnection(ClientStatus s){
        ClientStatus opponent = gameConnection.get(s);
        if (opponent != null) opponent.closeConnection();
        gameConnection.remove(s);
        gameConnection.remove(opponent);
        Iterator<String> iterator = queue.keySet().iterator();
        while(iterator.hasNext()){
            if (queue.get(iterator.next())==s) iterator.remove();
        }

    }


    public synchronized void room (ClientStatus s, String name){
        queue.put(name,s);
        if (queue.size()==2){
            List<String> keys = new ArrayList<>(queue.keySet());
            ClientStatus c1 = queue.get(keys.get(0));
            ClientStatus c2 = queue.get(keys.get(1));

            ClientStatus [] ListaC = {c1, c2};

            Set ss = queue.keySet ();
            Object[] a = ss.toArray ();
            String[] players = new String[a.length];
            for (int i = 0; i < players.length; i++){
                players[i] = a[i].toString ();
            }

            gameConnection.put(c1, c2);
            gameConnection.put(c2, c1);
            queue.clear();

            TurnManager newTurnManager = BoardGameConstructor.construct(players);

            for(int i=0; i< (newTurnManager.getPlayer()).length; i++){
                Player[] player = newTurnManager.getPlayer();
                (player[i]).addCliView(new CliView(ListaC[i], newTurnManager.getBoard() ));

            }


            newTurnManager.startGame();
        }
    }

  /*  public  synchronized void room2 (ClientStatus s, String name){
        queue.put(name, s);
        if (queue.size()==3){
            List<String> keys = new ArrayList<>(queue.keySet());
            ClientStatus c1 = queue.get(keys.get(0));
            ClientStatus c2 = queue.get(keys.get(1));
            ClientStatus c3 = queue.get(keys.get(2));

            ClientStatus [] ListaC = {c1, c2, c3};

            Set gg = queue.keySet ();
            Object[] a = gg.toArray ();
            String[] players = new String[a.length];
            for (int i = 0; i < players.length; i++){
                players[i] = a[i].toString ();
            }

            gameConnection.put(c1, c2);
            gameConnection.put(c2, c3);
            gameConnection.put(c1,c3);
            queue.clear();

            TurnManager newTurnManager = BoardGameConstructor.construct(players);

            for (Player in (newTurnManager.getPlayer())){
                int i=0;
                Player.AddCliview(new CliView(ListaC[i], newTurnManager.getBoard()));
                i++;
            }

            CliView player1View = new CliView(c1, newTurnManager.getBoard());
            CliView player2View = new CliView(c2, newTurnManager.getBoard());
            CliView player3View = new CliView(c3, newTurnManager.getBoard());

            newTurnManager.startGame();

        }

    }
*/


    public ServerSide() throws IOException {
        this.listener = new ServerSocket(port);
    }

    public void run(){
        while(true){
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

