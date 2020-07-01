package Network;

import View.*;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;


/***
 * This class implements java socket class
 * It implements a thin client that simply read user input and sends to the server
 * and vice-versa
 * @author Nieto
 */

public class ClientSide {


        private String ip = "127.0.0.1";
        private int port = 12345;
        private String name;
        private int players = 2;

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




        private String getName(){

            String name=null;
            Scanner scanner;

            while(name==null){
                try{
                    System.out.println("What's your name?");
                    scanner = new Scanner(System.in);
                    name=scanner.nextLine().split(" ")[0];
                }catch (Exception e){
                    System.out.println("There was a problem with your name. Try with something normal and you'll see there won't be any.");
                    name=null;
                }
            }
            return name;
        }

        private int numberOpponents(){
            int players = 0;
            Scanner scanner;
            String input;

            while(players != 2 && players != 3){
                try{
                    System.out.println("Do you want to play in a game with [2] or [3] players?");
                    scanner = new Scanner(System.in);
                    input = scanner.nextLine();
                    players = Integer.parseInt(input);
                    if(players != 2 && players != 3)
                        System.out.println("Just type [2] or [3] and then press enter, not more, not less");
                }catch (Exception e){
                    System.out.println("Just type [2] or [3] and then press enter, it isn't so difficult.");
                    players = 0;
                }
            }
            return players;
        }

        private String changeIp(){
            String newIp = null;
            Scanner scanner;

            while (newIp==null){
                try {
                    System.out.println("Type the ip address of your server, or [default] to reset the default server:");
                    scanner = new Scanner(System.in);
                    newIp = scanner.next();
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

        private void printMenu(){
            System.out.println("Your nickname is "+name+System.lineSeparator()+"Your game size is "+players+" players"+System.lineSeparator()+"The server's ip is "+ip+System.lineSeparator());
            System.out.println(System.lineSeparator()+"Do you want to:"+System.lineSeparator()+"[1]:    Start a new game with current settings"+System.lineSeparator()+"[2]:    Change nickname"+System.lineSeparator()+"[3]:   Change game's size"+System.lineSeparator()+"[4]:    Change server's ip address"+System.lineSeparator()+"[5]:    Exit the game");
        }

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
                        finally {
                            break;
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

    private int getMenuInput(){
        int input = 0;
        Scanner scanner;

        while(input == 0){
            try{
                scanner = new Scanner(System.in);
                input = scanner.nextInt();
            }catch (Exception e){
                System.out.println("Just type a number and then press enter, it isn't so difficult.");
                input = 0;
            }
        }
        return input;
    }

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
            System.out.println("Connection closed from the client side");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }
}

