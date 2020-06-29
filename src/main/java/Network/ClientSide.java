package Network;

import View.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;


/***
 * This class implements java socket class
 * It implements a thin client that  read user input and sends to the server
 * vice-versa and elaborates it.
 * @author Nieto
 */

public class ClientSide {


        private String ip;
        private int port;

    /**
     * Constructor
     * @param ip address used for the connection
     * @param port used to establish the connection
     */
    public ClientSide(String ip, int port){
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

    /**
     * A class that reads from the socket and based on what it gets it shows different infos to the player
     * @param socketIn the socket used by the client for the connection
     * @return the thread that contains the communication
     */
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

    /**
     * A class used to elaborate the output sent to the server
     * @param stdin the input received
     * @param socketOut the output to send
     * @return the thread that contains the communication
     */
        public Thread asyncWriteToSocket(final Scanner stdin, final PrintWriter socketOut){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (isActive()) {
                            String inputLine = stdin.nextLine();
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
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
            Scanner stdin = new Scanner(System.in);

            try{
                Thread t0 = asyncReadFromSocket(socketIn);
                Thread t1 = asyncWriteToSocket(stdin, socketOut);
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

    }

