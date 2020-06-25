package View;

import Network.Observer;
import model.*;
import Network.*;

import java.io.InputStream;

import java.util.Scanner;



/**
 * @author   Kumbi
 * Class for the CLI View
 */

public class CliView extends Observable implements model.Observer, Observer<String> {

    private Board board;
    private Player[] player;
    private ClientStatus client;

    public static final String FLOOR = "\u2589";
    public static final String DOME = "D";
    public static final String WORKER = "\uD83D\uDC68";
    public static final String IDTAG1 = "\u00B9";
    public static final String IDTAG2 = "\u00B2";
    public String[][] boardImage = new String[5][5] ;
    private final InputStream inFromClient;
    private final Scanner in;
    public String readInput = null;

    /**
     *
     * @author Kumbi
     * Constructor of CLiView
     */

    public CliView(ClientStatus client, InputStream inputStream, Board board) {
        System.out.println("Dentro CliView");

        this.inFromClient = inputStream;
        this.board = board;
        this.client = client;
        player= new Player[board.numberPlayers()];
        System.out.println("PreFor "+ board.numberPlayers());

        for (int i=1; i<=(board.numberPlayers()); i++){
            player[i-1] = board.getPlayer(i);
        }
        System.out.println("PostFor");
        client.addObs(this);
        System.out.println("inFromClient");

        board.attach(this);
        System.out.println("Dopo Attach");

        in = new Scanner(inFromClient);
        for (Player playerIdx : player) {
            for (int j = 1; j <=2 ; j++) {
                playerIdx.getWorker(j).attach(this);
            }
        }
        System.out.println("Chiusura CliView");

    }


    /**
     * @author Kumbi
     * displays Board
     */


    public void displayBoard() {
        buildFloors();
        addWorkers();
        client.asyncSend(new BoardView(boardImage));
    }


    /**
     * @author Kumbi
     * gets floor height and enquires presence of dome from each square
     * and saves it in the array of strings boardView.
     */

    private void buildFloors() {

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int[] pos = {i + 1, j + 1};

                String floor = floorBuilder(board.getFloorFrom(pos));
                if (board.squareHasDome(pos))
                    floor = domeBuilder(floor);

                boardImage[i][j] = floor;
            }

        }
    }


    /**
     * @author Kumbi
     * gets position of each player's workers and adds their workerId in
     * the right square
     */

    private void addWorkers() {

        int[] workerPosition;

        String workerIdentifierStr;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                workerPosition = player[i].getWorker(j + 1).getPosition();
                int row = workerPosition[0] - 1, column = workerPosition[1] - 1;
                workerIdentifierStr = WORKER + idTag(i + 1) + idTag(j + 1);
                boardImage[row][column] = boardImage[row][column].concat(workerIdentifierStr);
            }

        }

    }

    /**
     * @param n number
     * @author Kumbi
     * used to provide a number tag to identify
     * each worker
     */

    private String idTag(int n) {
        if (n == 1) {
            return IDTAG1;
        } else {
            return IDTAG2;
        }

    }


    /**
     * @param num number of floors to build
     * @author Kumbi
     * used to construct the visual representation of
     * the floors
     */

    private String floorBuilder(int num) {
        StringBuilder strFlr = new StringBuilder();
        for (int i = 0; i < num; i++) {
            strFlr.append(FLOOR);
        }
        return String.valueOf(strFlr);
    }


    /**
     * @param str string representing the UNICODE REPRESENTATION OF FLOORS
     * @author Kumbi
     * used to append the visual representation of
     * a dome to the floors
     */

    private String domeBuilder(String str) {
        StringBuilder strBld = new StringBuilder(str);
        strBld.append(DOME);
        return String.valueOf(strBld);
    }


    /**
     * @author Kumbi
     * updates boardimage after notification from Model
     */

    @Override
    public void update() {
        buildFloors();
        addWorkers();
        client.asyncSend(new BoardView(boardImage));
    }

    @Override
    /**
     * @author Kumbi
     * updates boardimage after notification from Model
     */
    public void updateCli(String message) {
        this.readInput = message;
    }

    /**
     * @author Kumbi
     * Asks the player if he wants to move or build and calls the respective Querymethods
     */

    public void intentionQuery(boolean waitingFor) {
        ask("Do You want to  : \n  [1] move    or\n [2] build  \n (input 1 or 2) ");
        int choice;

        if(readInput !=null) {
            choice = Integer.parseInt(readInput);

            if (choice == 1) {
                workerChoiceQuery();
            } else if (choice == 2) {
                buildLocationAndTypeQuery();
            } else {
                ask("input [1] or [2]");
                workerChoiceQuery();
            }
        }
    }


    /**
     * Asks the player which worker he wants to move and returns choice
     */

    public int workerChoiceQuery() {
        ask("Which worker do you want to move?[1] or [2] \n");

        Integer choice = in.nextInt();
        if (choice.equals(1) || choice.equals(2)) {
            return choice;
        } else {
            ask("input [1] or [2]");
            workerChoiceQuery();
        }
        return 0;
    }


    /**
     * Asks the player where he wants to move the worker and passes move action to Controller
     */

    public int[] moveLocationQuery() {
        System.out.println("Dentro MoveLocation");
        // int workerId = workerChoiceQuery();
        ask("Where do you want to move the worker to? (row,column)");
        //
        //   while(readInput==null){                   //TODO  ALTERNATIVA PER LEGGERE INPUT DA CLIENTE
        //      try {
        //          Thread.sleep( 1000);
        //         wait++;
        //     } catch (InterruptedException ie) {
        //           Thread.currentThread().interrupt();
        //       }System.out.println("Dentro wait per:" + wait);

        //  }
        //  String str = readInput;

        String str = in.nextLine();
        try {
            String[] input = str.split(",");
            int[] destination = {Integer.parseInt(input[0]), Integer.parseInt(input[1])};
            System.out.println(destination[0]+""+destination[1]);
            System.out.println(destination.toString());
            return new int[]{ destination[0], destination[1]};   // MANDALO COME FLUSSO SCANNER

        } catch (NumberFormatException e) {
            ask("Please provide integer values as coordinates");
        }
        return new int[6];
    }

    /**
     * Asks the player where he wants to build and passes build action to Controller
     * @return building action
     */

    public BuildingAction buildLocationQuery() {
        ask("Where do you want to build ? (row,column)\n");

        String str = in.next();
        try {
            String[] input = str.split(",");
            int[] buildLocation = {Integer.parseInt(input[0]), Integer.parseInt(input[1])};
            return new BuildingAction(buildLocation, true);

        } catch (NumberFormatException e) {
            ask("Please provide integer values as coordinates");
        }

        return null;
    }


    /**
     * Asks the player where he wants to build  and what he wants to build and passes build action to Controller
     */

    public BuildingAction buildLocationAndTypeQuery() {

        ask("Where do you want to build ? (row,column)\n");
        String str = in.next();
        try {
            String[] input = str.split(",");
            int[] buildLocation = {Integer.parseInt(input[0]), Integer.parseInt(input[1])};
            ask("Do you want to build a [1]Block or a [2]Dome ? input [1] or[2]\n");
            int choice = in.nextInt();

            if (choice == 1) {
                return new BuildingAction(buildLocation);
            } else if (choice == 2) {
                return new BuildingAction(buildLocation, true);
            } else {
                ask("input [1] or [2]");
                workerChoiceQuery();
            }
        } catch (NumberFormatException e) {
            ask("Please provide integer values as coordinates");
        }

        return null;
    }


    /**
     * Informs the player that It's not his turn
     */

    public void notYourTUrnMessage() {
        ask("It's not your turn");

    }


    /**
     * Informs the player that It is his turn to make a move
     */

    public void yourTUrnMessage() {

        ask("It's your turn to make a move");

    }


    /**
     * Asks the player if he wants to build again
     *
     * @return
     */

    public boolean buildAgainQuery() {

        ask("Do you want to build again ? (true, false)\n");
        return in.nextBoolean();

    }

    /**
     * Asks the player if he wants to move again
     *
     * @return
     */

    public boolean moveAgainQuery() {

        ask("Do you want to move again ? (true, false)\n");
        return in.nextBoolean();

    }

    /**
     * Informs player that he has won
     *
     * @return
     */

    public void winnerMessage() {
        ask("Congratulations!!! YOU HAVE WON");
    }

    /**
     * Informs player that he has lost because he has no moves left
     *
     * @return
     */

    public void noMovesLeftMessage() {
        ask("Sorry,you have no moves left .You have lost");
    }


    /**
     * Informs player that he has lost
     *
     * @return
     */

    public void loserMessage() {
        ask("Sorry, you have lost");
    }


    protected void ask(String QueryForClient){
        System.out.println("son dentro ask");
        client.asyncSend(QueryForClient);
        System.out.println("ho fatto ask");

    }



}
