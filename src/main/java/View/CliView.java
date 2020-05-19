package View;
import model.*;

import java.util.Scanner;

/**
 * @author   Kumbi
 * Class for the CLI View
 */

public class CliView implements Observer {


    private Scanner scanner;
    private Board board;
    private BoardWorker[] worker;
    private Player[] player;
    private Client client;
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE ="\u001B[35m";
    public static final String RESET = "\u001B[0m";
    public static final String FLOOR ="\u2589";
    public static final String DOME ="D";
    public static final String WORKER ="\uD83D\uDC68";
    public static final String BORDER ="\u2591";
    public String[][] boardView = new String[5][5];


    /**
     * @author Kumbi
     * Constructor of CLiView
     */

    public CliView(Client client) {
        this.client = client;
        scanner = new Scanner(System.in);
        board.attach(this);

        for (Player playerIdx : player) {
            for (int j = 0; j < worker.length; j++) {
                playerIdx.getWorker(j).attach(this);
            }
        }
    }


    /**
     * @author   Kumbi
     * gets floor height and enquires presence of dome from each square
     * and saves it in the array of strings boardView.
     */

    public void buildFloors(){

        for (int i = 0; i < 5; i ++) {

            for (int j = 0; j <5 ; j ++) {

                int[] pos = {i+1,j+1};

                String floor = floorBuilder(board.getFloorFrom(pos));

                if(board.squareHasDome(pos))
                    floor = domeBuilder(floor);

                boardView[i][j]= floor;
            }

        }
    }


    /**
     * @author   Kumbi
     * gets position of each player's workers and adds their workerId in
     * the right square
     */

    public void addWorkers(){

        int[] workerPosition ;

        String workerIdentifierStr;

        for (int i = 0; i < 2; i++) {

            for (int j= 0; j <2; j++){

                workerPosition = player[i].getWorker(j+1).getPosition();

                int row = workerPosition[0]-1,column = workerPosition[1]-1;

                workerIdentifierStr = WORKER+idTag(i+1)+idTag(j+1);

                boardView[row][column] = boardView[row][column].concat(workerIdentifierStr);
            }

        }

    }


    /**
     * @author   Kumbi
     * used to provide the right indentation for the clear representation
     * of each square
     * @param myString string
     */

    public String indent(String myString,int n) {
        return String.format("%-"+n+"s",myString);
    }


    /**
     * @author   Kumbi
     * used to provide a number tag to identify
     * each worker
     * @param n number
     */

    public String idTag(int n) {
        if (n==1){return "\u00B9";}
        else {return "\u00B2"; }

    }


    /**
     * @author   Kumbi
     * used to construct the visual representation of
     * the floors
     * @param num number of floors to build
     */

    public String floorBuilder(int num){
        StringBuilder strFlr = new StringBuilder();
        for(int i=0;i<num;i++) {
            strFlr.append(FLOOR);
        }
        return String.valueOf(strFlr);
    }


    /**
     * @author   Kumbi
     * used to append the visual representation of
     * a dome to the floors
     * @param str string representing the UNICODE REPRESENTATION OF FLOORS
     */

    public String domeBuilder(String str){
        StringBuilder strBld = new StringBuilder(str);
        strBld.append(DOME);
        return String.valueOf(strBld);
    }


    /**
     * @author   Kumbi
     * displays the board
     */

    public void displayBoardView(){

        for (int i = 0; i < 5; i ++) {

            for (int j = 0; j <5 ; j ++) {

                for(j = 0; j<5; j ++) {

                    System.out.print(GREEN +"\u2587" + BLUE+BORDER + BORDER + BORDER + BORDER + RED + (j + 1)
                            + RESET + BLUE + BORDER + BORDER + BORDER + BORDER + BORDER + RESET);     // shows top border of board
                }

                System.out.print( GREEN +"\u2587"+RESET+"\n");

                for(j = 0; j<5; j++) {
                    System.out.print(BLUE +BORDER+RESET+"          ");                             // shows top layer of first row squares
                }

                System.out.print(BLUE +BORDER+RESET+"  "+"\n");                                        // shows last wall of top layer of first row of square

                for(j = 0; j<5; j++) {
                    System.out.print("   " + indent(boardView[i][j], 8));                        // shows blocks and players row
                }

                System.out.print(RED+(i+1)+RESET+"\n");                                                         //shows row identifier number

                for(j = 0; j<5; j++)
                    System.out.print(BLUE +BORDER+RESET+"          ");                            // shows bottom layer of row

                System.out.print(BLUE +BORDER+RESET+"\n");                                        // shows last wall of bottom layer of row

            }

        }
        for(int j = 0 ; j<5 ;j++) {
            System.out.print(GREEN +"\u2587" + BLUE+BORDER + BORDER + BORDER + BORDER + RED + (j + 1)
                    + RESET + BLUE + BORDER + BORDER + BORDER + BORDER + BORDER + RESET);
        }                                                                                            // shows bottom layer of each row


        System.out.print( "\u001B[32m\u2587\u001B[0m");                                             //shows last wall of bottom layer of row
        System.out.print("\n");

    }


    /**
     * @author Kumbi
     * updates board after notification from Model
     */

    @Override
    public void update() {
        buildFloors();
        addWorkers();
        displayBoardView();
    }


    /**
     * @author Kumbi
     * Asks the player if he wants to move or build and calls the respective Querymethods
     */

    public void IntentionQuery() {
        System.out.print("Do You want to  : \n  [1] move    or\n [2] build  \n (input 1 or 2) ");
        if (scanner.hasNextInt()) {

            int choice = scanner.nextInt();
            if (choice == 1) {
                workerChoiceQuery();
            } else if (choice == 2) {
                buildLocationAndTypeQuery();
            } else {
                System.out.print("input [1] or [2]");
                workerChoiceQuery();
            }
        } else {
            System.out.print("wrong input \n");
            workerChoiceQuery();
        }

    }


    /**
     * @author Kumbi
     * Asks the player which worker he wants to move and calls the  MoveLOcationQuerymethod by passing the workerId parameter
     */

    public int workerChoiceQuery() {
        System.out.print("Which worker do you want to move?[1] or [2] \n");
        if (scanner.hasNextInt()) {
            int choice = scanner.nextInt();

            if (choice == 1 || choice == 2) {
                return choice;

            } else {
                System.out.print("input [1] or [2]");
                workerChoiceQuery();
            }
        } else {
            System.out.print("wrong input \n");
            workerChoiceQuery();
        }

    }


    /**
     * @author Kumbi
     * Asks the player wher he wants to move the worker and passes move action to Controller
     */

    public int[] moveLocationQuery() {

        int workerId = workerChoiceQuery();
        System.out.print("Where do you want to move it to? (row,column)\n");
        if (scanner.hasNext()) {
            {
                String s = scanner.next();
                try {
                    String[] input = s.split(",");

                    int[] destination = {Integer.parseInt(input[0]), Integer.parseInt(input[1])};

                        return new int[] { workerId,destination[0],destination[1]};   // MANDALO COME FLUSSO SCANNER

                } catch (NumberFormatException e) {
                    System.out.println("Please provide integer values as coordinates");
                }
            }
        }
        else {

        }
    }

    /**
     * @author Kumbi
     * Asks the player where he wants to build and passes build action to Controller
     * @return
     */

    public BuildingAction buildLocationQuery() {

        System.out.print("Where do you want to build ? (row,column)\n");
        if (scanner.hasNext()) {

            String str = scanner.next();
            try {
                String[] input = str.split(",");
                int[] buildLocation = {Integer.parseInt(input[0]), Integer.parseInt(input[1])};

                return new BuildingAction(buildLocation, true);


            } catch (NumberFormatException e) {
                System.out.println("Please provide integer values as coordinates");
            }
        }
        return null;
    }





    /**
     * @author Kumbi
     * Asks the player where he wants to build  and what he wants to build and passes build action to Controller
     */

    public BuildingAction buildLocationAndTypeQuery() {

            System.out.print("Where do you want to build ? (row,column)\n");
            if (scanner.hasNext()) {

                    String str = scanner.next();
                    try {
                        String[] input = str.split(",");

                        int[] buildLocation = {Integer.parseInt(input[0]), Integer.parseInt(input[1])};

                        System.out.print("Do you want to build a [1]Block or a [2]Dome ? input [1] or[2]\n");
                        if (scanner.hasNext()) {

                                int choice = scanner.nextInt();

                                if (choice == 1) {
                                    return new BuildingAction(buildLocation);
                                } else if (choice == 2) {
                                    return  new BuildingAction(buildLocation, true);
                                } else {
                                    System.out.print("input [1] or [2]");
                                    workerChoiceQuery();
                                    return null;

                                }
                            }

                    } catch (NumberFormatException e) {
                        System.out.println("Please provide integer values as coordinates");
                    }
            }
        return null;
    }



    public void notYourTUrnMessage() {
        System.out.print("It's not your turn");

    }


    public void yourTUrnMessage() {

        System.out.print("It's your turn to make a move");

    }

}


