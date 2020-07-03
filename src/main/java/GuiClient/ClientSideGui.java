package GuiClient;

import View.BoardView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import static java.lang.Thread.sleep;

/**
 *
 *   @author Kumbi
 *   Class for clientside gui communication
 */



public class ClientSideGui implements Observer{


        private String ip;
        private int port;
        private String messageToServer;
        private boolean gotMessage = false;

        public ClientSideGui(String ip, int port){
            this.ip = ip;
            this.port = port;
        }

        private boolean active = true;

        public synchronized boolean isActive(){
            return active;
        }

        public synchronized void setActive(boolean active){
            this.active = active;
        }


        public Thread asyncReadFromSocket(final ObjectInputStream socketIn){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (isActive()) {
                            Object inputObject = socketIn.readObject();
                            if (inputObject instanceof String) {
                                System.out.println((String) inputObject);
                            } else if(inputObject instanceof BoardView){
                                ((BoardView) inputObject).displayBoardView();
                            } else {
                                throw new IllegalArgumentException();
                            }
                        }
                    } catch (Exception e) {
                        setActive(false);
                    }
                }
            });
            t.start();
            return t;
        }

        public Thread asyncWriteToSocket(String messageToServer, final PrintWriter socketOut){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        while (isActive() ) {

                            while (!gotMessage) {
                                try {
                                    sleep(200);
                                } catch (InterruptedException ie) {
                                    Thread.currentThread().interrupt();
                                }
                                System.out.println("Dentro wait");
                            }
                            String inputLine = messageToServer;
                            socketOut.println(inputLine);
                            socketOut.flush();
                        }

                    }catch(Exception e){
                        setActive(false);
                    }
                }
            });
            t.start();
            return t;
        }

        public void run() throws IOException {
            Socket socket = new Socket(ip, port);
            System.out.println("Connection established");
            ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream(),true);
            Scanner stdin = new Scanner(System.in);

            try{
                Thread t0 = asyncReadFromSocket(socketIn);
                Thread t1 = asyncWriteToSocket(messageToServer, socketOut);

                t0.join();
                t1.join();


            } catch(InterruptedException | NoSuchElementException e){
                System.out.println("Connection closed from the client side");
            } finally {
                stdin.close();
                socketIn.close();
                socketOut.close();
                socket.close();
            }
        }


    @Override
    public void update(Observable o, Object arg) {
        this.gotMessage = true;
        this.messageToServer = String.valueOf(arg);
    }
}