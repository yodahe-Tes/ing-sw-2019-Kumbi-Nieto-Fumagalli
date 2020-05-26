package View;
import model.*;
import Network.*;
import model.Observer;


import java.io.Serializable;


/**
 * @author   Kumbi
 * Class for the CLI View
 */

public class CliView implements Observer {



    private Board board;
    private BoardWorker[] worker;
    private Player[] player;
    private clientStatus client;
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE ="\u001B[35m";
    public static final String RESET = "\u001B[0m";
    public static final String FLOOR ="\u2589";
    public static final String DOME ="D";
    public static final String WORKER ="\uD83D\uDC68";
    public static final String BORDER ="\u2591";
    public static final String IDTAG1 ="\u00B9";
    public static final String IDTAG2 ="\u00B2";
    public String[][] boardView = new String[5][5];


    /**
     * @author Kumbi
     * Constructor of CLiView
     */

    public CliView(clientStatus client) {
        this.client = client;
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

    private void buildFloors(){

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

    private void addWorkers(){

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

    private String indent(String myString, int n) {
        return String.format("%-"+n+"s",myString);
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
     * @author   Kumbi
     * used to provide a number tag to identify
     * each worker
     * @param n number
     */

    private String idTag(int n) {
        if (n==1){return IDTAG1;}
        else {return IDTAG2; }

    }


    /**
     * @author   Kumbi
     * used to construct the visual representation of
     * the floors
     * @param num number of floors to build
     */

    private String floorBuilder(int num){
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

    private String domeBuilder(String str){
        StringBuilder strBld = new StringBuilder(str);
        strBld.append(DOME);
        return String.valueOf(strBld);
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

}


