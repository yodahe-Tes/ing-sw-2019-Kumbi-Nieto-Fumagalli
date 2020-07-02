package Network;

import View.*;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;


/***
 * A class that implements the client socket
 * It implements a thin client that simply read user input and sends to the server
 * and vice-versa, using a CLI menu
 * @author Nieto
 */

public class ClientSide {


        private String ip = "127.0.0.1";
        private int port = 12345;
        private String name;
        private int players = 2;
        Scanner scanner = new Scanner(System.in);

        public ClientSide(){
            name = getName();
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

        public Thread asyncWriteToSocket(final Scanner stdin, final PrintWriter socketOut){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (isActive()) {
                            String inputLine = stdin.nextLine();
                            cleanIn();
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




        public void run(){
            menu();
        }

        /**
        * A method that returns the name given by the player
        * @return the name of the player
        */
        private String getName(){
            String name = null;
            while (name==null){
                try {
                    System.out.println("What's your name?");
                    name = scanner.next();
                    cleanIn();
                }catch (Exception e){
                    System.out.println("Try to type a normal name.");
                    name=null;
                }
            }
            return name;
        }

        /**
        * A method that return the number of players for the game decided by the player
        * @return number of players for the game
        */
        private int numberOpponents(){
            int players = 0;
            String input = null;
            while (input==null){
                try {
                    System.out.println("Type [2] if you want to play a 2 players game, [3] if yo want to play a 3 players game.");
                    input = scanner.next();
                    cleanIn();
                    players = Integer.parseInt(input);
                    if(players != 2 && players != 3)
                        System.out.println("Just type [2] or [3] and then press enter, not more, not less");
                }catch (Exception e){
                    System.out.println("Just type [2] or [3] and then press enter, not more, not less");
                    name=null;
                }
            }
            return players;
        }

        /**
        * A method that returns the new changed ip decided by the player
        * @return new IP address
        */
        private String changeIp(){
            String newIp = null;
            while (newIp==null){
                try {
                    System.out.println("Type the ip address of your server, or [default] to reset the default server:");
                    newIp = scanner.next();
                    cleanIn();
                    if(newIp.equals("default")){
                        newIp="127.0.0.1";
                    }
                    else if(!correctIp(newIp)){
                        newIp = null;
                        System.out.println("Try to type a correct ip address. If you don't know what's going on here, just type [default].");
                    }
                }catch (Exception e){
                    System.out.println("Try to type a correct ip address. If you don't know what's going on here, just type [default].");
                    newIp=null;
                }
            }
            return newIp;
        }

        private boolean correctIp(String ip){
            try {
                String[] stringArr = ip.split("\\.");
                int i = 0;
                int check;
                for(String element : stringArr){
                    check=Integer.parseInt(element);
                    if(check>255 || check<0){
                        return false;
                    }
                    i++;
                }
                if(i==4)
                    return true;
                return false;

            }catch (Exception e){
                return false;
            }
        }

        /***
        * A method to print the menù
        */
        private void printMenu(){
            System.out.println("Your nickname is "+name+System.lineSeparator()+"Your game size is "+players+" players"+System.lineSeparator()+"The server's ip is "+ip+System.lineSeparator());
            System.out.println(System.lineSeparator()+"Do you want to:"+System.lineSeparator()+"[1]:    Start a new game with current settings"+System.lineSeparator()+"[2]:    Change nickname"+System.lineSeparator()+"[3]:   Change game's size"+System.lineSeparator()+"[4]:    Change server's ip address"+System.lineSeparator()+"[5]:    Exit the game");
        }

        /**
        * A method that sets up the menù to show to the player
        */
        private void menu(){
            boolean insideMenu=true;
            while(insideMenu){
                printMenu();
                switch (getMenuInput()){
                    case(1):{
                        try {
                            connectToGame();
                        }catch (IOException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                    case(2):{
                        name = getName();
                        break;
                    }
                    case(3):{
                        players = numberOpponents();
                        break;
                    }
                    case(4):{
                        ip = changeIp();
                        break;
                    }
                    case(5):{
                        System.out.println("You chose to exit. Goodbye!");
                        insideMenu=false;
                        break;
                    }
                    default:{
                        System.out.println("Make a choice!");
                    }
            }
            }
        }

        /**
        * A method that saves the input from the player
        * @return input from the player
        */
        private int getMenuInput(){
        int input = 0;
        while(input == 0){
            try{
                input = scanner.nextInt();
                cleanIn();
            }catch (Exception e){
                e.getMessage();
                System.out.println("Just type a number and then press enter, it isn't so difficult.");
                input = 0;
            }
        }
        return input;
        }

        /**
        * A method that creates the connection to the server
        * @throws IOException
        */
        private void connectToGame() throws IOException {

            Socket socket = new Socket(ip, port);
            System.out.println("Connection established");
            ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream(),true);
            Scanner stdin = new Scanner(System.in);

            socketOut.println(name);
            socketOut.println(players);

            try{
                Thread t0 = asyncReadFromSocket(socketIn);
                Thread t1 = asyncWriteToSocket(stdin, socketOut);

                t0.join();
                t1.join();


        } catch(InterruptedException | NoSuchElementException e){
            active=false;
            System.out.println("Connection closed from the client side");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
            System.err.println("finito di chiudere socket");
            scanner.nextLine();
        }
    }

    private void cleanIn(){
        int dummy;

        try {
            while ((System.in.available()) != 0)
                dummy = System.in.read();
        } catch (java.io.IOException e) {
            System.out.println("Input error");
        }
    }
}