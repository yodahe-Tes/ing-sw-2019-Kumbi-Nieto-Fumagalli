package Network;

import controller.BoardGameConstructor;
import controller.TurnManager;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import View.*;

/***
 * This class implements java server class
 * @author Nieto
 */

public class serverSide {
    //static ServerSocket variable
    private static ServerSocket listener;
    //socket server port on which it will listen
    private static int port = 9876;
    //A store fo all the threads and a pool to execute them
    private  Map<String,clientStatus > queue = new HashMap<>();
    private Map<clientStatus,clientStatus> gameConnection = new HashMap<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(2);

    /**
     * A method to deregister the client connection and set free the spot to start
     * a new game
     * @param s client socket
     */

    public synchronized void deregisterConnection(clientStatus s){
        clientStatus opponent = gameConnection.get(s);
        if (opponent != null) opponent.closeConnection();
        gameConnection.remove(s);
        gameConnection.remove(opponent);
        Iterator<String> iterator = queue.keySet().iterator();
        while(iterator.hasNext()){
            if (queue.get(iterator.next())==s) iterator.remove();
        }

    }


    public synchronized void room (clientStatus s, String name){
        queue.put(name,s);
        if (queue.size()==2){
            List<String> keys = new ArrayList<>(queue.keySet());
            clientStatus c1 = queue.get(keys.get(0));
            clientStatus c2 = queue.get(keys.get(1));

            Set ss = queue.keySet ();
            Object[] a = ss.toArray ();
            String[] players = new String[a.length];
            for (int i = 0; i < players.length; i++){
                players[i] = a[i].toString ();
            }

            TurnManager newTurnManager = BoardGameConstructor.construct(players);
            CliView player1View = new CliView(c1);
            CliView player2View = new CliView(c2);
            newTurnManager.startGame();

            gameConnection.put(c1, c2);
            gameConnection.put(c2, c1);
            queue.clear();

            c1.asyncSend(player1View.clone());//clone della board
            c2.asyncSend(player2View.clone());

            if((newTurnManager.getOwner()).getNickname == players[0]){
                c1.asyncSend(CliQuery.yourTurnMessage());
                c2.asyncSend(CliQuery.notYourTurnMessage());
            } else {
                c2.asyncSend(CliQuery.yourTurnMessage());
                c1.asyncSend(CliQuery.notYourTurnMessage());
            }

        }
    }

    public serverSide() throws IOException {
        this.listener = new ServerSocket(port);
    }

    public void run(){
        while(true){
            try {
                Socket newSocket = listener.accept();
                clientHandler clHandler = new clientHandler(newSocket, this);
                pool.submit(clHandler);
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }
}

