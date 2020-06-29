package View;

import Network.ClientStatus;
import Network.Observable;
import Network.Observer;
import model.Board;
import model.BuildingAction;
import model.Player;

import static java.lang.Thread.sleep;


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
    public String[][] boardImage = new String[5][5];
    public String readInput = null;
    private boolean flag = false;

    /**
     * @author Kumbi
     * Constructor of CLiView
     */

    public CliView(ClientStatus client, Board board) {

        this.board = board;
        this.client = client;
        player = new Player[board.numberPlayers()];
               for (int i = 1; i <= (board.numberPlayers()); i++) {
            player[i - 1] = board.getPlayer(i);
        }
        client.addObs(this);
        board.attach(this);

        for (Player playerIdx : player) {
            for (int j = 1; j <= 2; j++) {
                playerIdx.getWorker(j).attach(this);
            }
        }
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
     * updates boardimage after notification from Model and sends BoardView to client
     */

    @Override
    public void update() {
        buildFloors();
        addWorkers();
        client.asyncSend(new BoardView(boardImage));
    }



    /**
     * @author Kumbi
     * updates the readInput after receiving message from client
     */

    @Override
    public void updateCli(String message) {
        this.flag = true;
        this.readInput = message;
    }

    /**
     * @author Kumbi
     * Asks the player if he wants to move or build and calls the respective Querymethods
     */

    public void intentionQuery(boolean waitingFor) {
        String str = ask("Do You want to  : \n  [1] move    or\n [2] build  \n (input 1 or 2) ");
            int choice = Integer.parseInt(str);

            if (choice == 1) {
                workerChoiceQuery();
            } else if (choice == 2) {
                buildLocationAndTypeQuery();
            } else {
                inform("input [1] or [2]");
                workerChoiceQuery();
            }

    }


    /**
     * Asks the player which worker he wants to move and returns choice
     */

    public int workerChoiceQuery() {
        ask("Which worker do you want to move?[1] or [2] ");

        Integer choice = Integer.valueOf(readInput);
        if (choice.equals(1) || choice.equals(2)) {
            return choice;
        } else {
            inform("input [1] or [2]");
            workerChoiceQuery();
        }
        return 0;
    }

    /**
     * Asks the player where he wants to move the worker and passes move action to Controller
     * @param i is the number of the worker that will be positioned
     */

    public int[] initialPositionQuery(int i) {

        String str = ask("Choose initial position of your worker number"+i+"(row,column)");
        try{
   String[] input = str.split(",");
            int[] destination = {Integer.parseInt(input[0]), Integer.parseInt(input[1])};

            return destination;
        }
        catch (NumberFormatException e) {
            System.out.println("Please provide integer values as coordinates");
        }
        return new int[0];
    }

    /**
     * Asks the player where he wants to move the worker and passes move action to Controller
     */

    public int[] moveLocationQuery() {
        System.out.println("Dentro MoveLocation");
        int workerId = workerChoiceQuery();
        String str = ask("Where do you want to move the worker to? (row,column)");
        try {

            String[] input = str.split(",");
            int[] destination = {workerId,Integer.parseInt(input[0]), Integer.parseInt(input[1])};

            return destination;
        } catch (NumberFormatException e) {
            System.out.println("Please provide integer values as coordinates");
        }
        return new int[0];
    }



    /**
     * Asks the player where he wants to build and passes build action to Controller
     *
     * @return building action
     */

    public BuildingAction buildLocationQuery() {
        String str = ask("Where do you want to build ? (row,column)\n");
        String[] input=null;
        int[] buildLocation=null;
        while(buildLocation==null) {
            try {
                input = str.split(",");
                buildLocation = new int[]{Integer.parseInt(input[0]), Integer.parseInt(input[1])};
                return new BuildingAction(buildLocation);
            } catch (NumberFormatException e) {
                inform("Please provide integer values as coordinates");
                buildLocation=null;
            }
        }
        return(new BuildingAction(buildLocation));
    }


    /**
     * Asks the player where he wants to build  and what he wants to build and passes build action to Controller
     */

    public BuildingAction buildLocationAndTypeQuery() {

        String str = ask("Where do you want to build ? (row,column)\n");

        try {
            String[] input = str.split(",");
            int[] buildLocation = {Integer.parseInt(input[0]), Integer.parseInt(input[1])};
            String str2 = ask("Do you want to build a [1]Block or a [2]Dome ? input [1] or[2]\n");
            int choice = Integer.parseInt(str2);

            if (choice == 1) {
                return new BuildingAction(buildLocation);
            } else if (choice == 2) {
                return new BuildingAction(buildLocation, true);
            } else {
                inform("input [1] or [2]");
                buildLocationAndTypeQuery();
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
        inform("It's not your turn");

    }


    /**
     * Informs the player that It is his turn to make a move
     */

    public void yourTUrnMessage() {

        inform("It's your turn to make a move");

    }


    /**
     * Asks the player if he wants to build again
     *
     * @return boolean
     */

    public boolean buildAgainQuery() {

        String answer = ask("Do you want to build again ? (true, false)\n");
        return Boolean.parseBoolean(answer);

    }

    /**
     * Asks the player if he wants to move again
     *
     * @return boolean
     */

    public boolean moveAgainQuery() {

        String answer = ask("Do you want to move again ? (true, false)\n");
        return Boolean.parseBoolean(answer);

    }

    /**
     * Informs player which God card he has
     *
     */

    public void assignedGodMessage(String godName) {
        inform("Your God Card is "+ godName);
    }



    /**
     * Informs player that he can't move the worker to the chosen destination
     *
     */

    public void squareIsOccupiedMessage() {
        inform("You can't move worker there.");
    }



    /**
     * Informs player that he has won
     *
     */

    public void winnerMessage() {
        inform("Congratulations!!! YOU HAVE WON");
    }

    /**
     * Informs player that he has lost because he has no moves left
     *
     */

    public void noMovesLeftMessage() {
        inform("Sorry,you have no moves left .You have lost");
    }


    /**
     * Informs player that he has lost
     *
     */

    public void loserMessage() {
        inform("Sorry, you have lost");
    }

    /**
     * method used to send info to client
     */

    private void inform(String infoForClient) {
        System.out.println("son dentro inform");
        client.asyncSend(infoForClient);
    }

    /**
     * method used to send questions to the client and retrieve the answer
     *
     */
    protected String ask(String QueryForClient) {

        client.asyncSend(QueryForClient);
        String str = null;
        try {
            System.out.println("prima di wait");
            while (!flag) {
                try {
                    sleep(200);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
               //

            }
            System.out.println("finito wait");
            str = readInput;
            flag=false;
            System.out.println("ho fatto ask");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str;
    }

}
