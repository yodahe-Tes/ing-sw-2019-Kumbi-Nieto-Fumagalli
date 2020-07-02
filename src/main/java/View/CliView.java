package View;

import Network.ClientStatus;
import Network.Observable;
import Network.Observer;
import model.Board;
import model.BuildingAction;
import model.Player;

import java.io.IOException;

import static java.lang.Thread.sleep;


/**
 * @author   Kumbi
 * Class for the CLI View
 */

public class CliView extends Observable implements model.Observer, Observer<String> {

    private Board board;
    private Player[] player;
    private ClientStatus client;
    private boolean active=false;
    private boolean connectionActive=true;

    private static final String FLOOR = "\u2589";
    private static final String DOME = "D";
    private static final String WORKER = "\u2689";
    private static final String IDTAG1 = "\u00B9";
    private static final String IDTAG2 = "\u00B2";
    private static final String IDTAG3 = "\u00B3";
    private String[][] boardImage = new String[5][5];
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

        for (int i = 0; i < board.numberPlayers(); i++) {
            for (int j = 0; j < 2; j++) {
                workerPosition = player[i].getWorker(j + 1).getPosition();
                try {
                    int row = workerPosition[0] - 1, column = workerPosition[1] - 1;
                    workerIdentifierStr = WORKER + idTag(i + 1) + idTag(j + 1);
                    boardImage[row][column] = boardImage[row][column].concat(workerIdentifierStr);
                }
                catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("error in constructing board");
                }
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
        } else if(n == 2) {
            return IDTAG2;
        }else  {
            return IDTAG3;
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
     * updates the readInput after receiving message from client, sends the gods description and informs the player about its input
     */

    @Override
    public void updateCli(String message) {

        if(message!=null && message.equals("closed") && !client.isActive()){
            connectionActive=false;
        }
        else if(message!=null && message.equals("g")){
            assignedGodMessage();
            otherPlayersGod();
        }
        else if(message!=null && message.equals("disconnect")){
            connectionActive=false;
        }
        else if(active) {
                this.flag = true;
                this.readInput = message;
        }
        else{
            informType();
        }

    }

    /**
     * informs the client when and what it can send
     */
    public void informType(){
        inform("When not asked to type you can only check gods with the command [g], or disconnect typing [disconnect]");
    }

    /**
     * @author Kumbi
     * Asks the player if he wants to move or build and calls the respective Querymethods
     */

    public void intentionQuery(boolean waitingFor) throws IOException {
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
     * checks if input is within the range of x up to y
     */

    public boolean xTOychecker(int i,int x, int y) {
        return (i>=x && i<=y);
    }


    /**
     * Asks the player which worker he wants to move and returns choice
     */

    public int workerChoiceQuery() throws IOException {
        update();
        String str = ask("Which worker do you want to move?[1] or [2] ");

        Integer choice;
        try {
            choice = Integer.valueOf(str);
        }catch (NumberFormatException e){
            inform("Please provide an integer values as worker number");
            choice = workerChoiceQuery();
        }
        if (xTOychecker(choice,1,2))
            return choice;
        else {
            inform("input [1] or [2]");
            choice = workerChoiceQuery();
        }
        return choice;
    }

    /**
     * Asks the player where he wants to move the worker and passes move action to Controller
     */

    public int[] initialPositionQuery(int i) throws IOException {

        String [] input;
        int[] destination=null;
        while(destination==null) {
            try {
                update();
                String str = ask("Choose initial position of worker"+i+"(row,column)");
                input = str.split(",");
                if (xTOychecker(Integer.parseInt(input[0]),1,5)&&xTOychecker(Integer.parseInt(input[1]),1,5))
                    destination = new int[]{Integer.parseInt(input[0]), Integer.parseInt(input[1])};
            } catch (NumberFormatException|NullPointerException|ArrayIndexOutOfBoundsException e) {
                inform("Please provide integer values as coordinates");
                destination = initialPositionQuery(i);
            }
        }
        return destination;
    }

    /**
     * Asks the player which worker he wants to move and where, then passes move action to Controller
     */

    public int[] moveQuery() throws IOException {
        System.out.println("Dentro MoveQuery");
        int workerId = workerChoiceQuery();
        int[] destination = moveLocationQuery();
        return new int[]{workerId,destination[0],destination[1]};
    }

    /**
     * Asks the player where he wants to move the worker
     */
    public int[] moveLocationQuery() throws IOException {
        System.out.println("Dentro MoveLocation");
        String [] input = null;
        int[] destination=null;
        while(input==null) {
            try {
                String str = ask(" (row,column)");
                input = str.split(",");
                destination = new int[]{Integer.parseInt(input[0]), Integer.parseInt(input[1])};
                return destination;
            } catch (NumberFormatException|NullPointerException e) {
                inform("Please provide integer values as coordinates");
                destination = null;
            }

        }return destination;
    }


    /**
     * Asks the player where he wants to build and passes build action to Controller
     *
     * @return building action
     */

    public BuildingAction buildLocationQuery() throws IOException {
        String str = ask("Where do you want to build ? (row,column)\n");
        String [] input = null;
        int[] buildLocation=null;
        while(input==null) {
            try {
                input = str.split(",");
                buildLocation = new int[]{Integer.parseInt(input[0]), Integer.parseInt(input[1])};
                return new BuildingAction(buildLocation);
            } catch (NumberFormatException|NullPointerException e) {
                inform("Please provide integer values as coordinates");
                buildLocation = null;
            }
        }
        return (new BuildingAction(buildLocation));
    }


    /**
     * Asks the player where he wants to build  and what he wants to build and passes build action to Controller
     */

    public BuildingAction buildLocationAndTypeQuery() throws IOException {

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
        } catch (NumberFormatException|NullPointerException e) {
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

    public boolean buildAgainQuery() throws IOException{

        String answer = ask("Do you want to build again ? (y, n)");

        if(answer.equals("y")){
            return true;}

        else if(answer.equals("n")){
            return false;
        }

        else{
            inform("Type y or n ");
            return buildAgainQuery();
        }
    }

    /**
     * Asks the player if he wants to move again
     *
     * @return boolean
     */

    public boolean moveAgainQuery() throws IOException{

        String answer = ask("Do you want to move again ? (y, n)");
        if(answer.equals("y")){
            return true;}
        else if(answer.equals("n")){
            return false;
        }
        else{
            inform("Type y or n ");
            return moveAgainQuery();
        }
    }

    /**
     * used to inform a player about the opponent players' God
     *
     */
    public void otherPlayersGod(){
        for(int i=0;i<player.length;i++){
            if (player[i] != playerOwner()){
                inform(player[i].getNickname()+", the player "+(i+1)+", has God "+player[i].godDesc());
            }
        }
    }


    /**
     * used to retrieve the player
     *
     */
    private Player playerOwner(){
        for(Player players : player){
            if (players.getView()==this){
                return players;
            }
        }
        return null;
    }


    /**
     * Informs player which God card he has and the description
     *
     */

    public void assignedGodMessage() {

        inform("Your God Card is "+ playerOwner().godDesc());
    }


    /**
     * Informs player that he can't move the worker to the chosen destination
     *
     */

    public void squareIsOccupiedMessage() {
        inform("You can't move worker there. choose another destination");
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
     * Informs players that one of the players  has lost
     *
     */


    public void aPlayerHasLostmessage(Player loser) {
        inform("player "+ loser.getNickname()+" has lost.");
    }

    /**
     * informs players that one of the player has disconnected
     * @param disconnected
     */
    public void aPlayerHasDisconnectedMessage(Player disconnected){inform("player "+ disconnected.getNickname()+" has disconnected from game.");}

    /**
     * Informs player that he has lost
     *
     */

    public void loserMessage() {
        inform("Sorry, you have lost");
    }

    public void aPlayerHasWonMessage(Player winner){inform("player "+ winner.getNickname()+" has won.");}
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
    protected String ask(String QueryForClient) throws IOException{
        client.asyncSend(QueryForClient);
        active=true;
        String str = null;
        try {
            System.out.println("prima di wait");
            while (!flag && connectionActive) {
                try {

                    sleep(200);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
            if(connectionActive) {
                System.out.println("finito wait");
                str = readInput;
                flag = false;
                System.out.println("ho fatto ask");
                if(str.equals("disconnect")){
                    throw new IOException();
                }
            }
            else{
                throw new IOException();
            }
        } catch (Exception e) {
            if(e instanceof IOException)
                throw new IOException();
            e.printStackTrace();
        }
        active=false;
        return str;
    }

    public void closeConnection(){
        client.close();

    }
}
