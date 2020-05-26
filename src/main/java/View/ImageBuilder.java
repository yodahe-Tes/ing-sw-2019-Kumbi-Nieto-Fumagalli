package View;
import model.*;
/**
 * @author Kumbi
 * A class to build image of board and building blocks
 */

public class ImageBuilder {
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE ="\u001B[35m";
    private static final String FLOOR ="\u2585";
    private static final String DOME ="\u25D7";
    private static final String WORKER ="\uD83D\uDC68";

    public String[][] boardView = new String[5][5];



    /**
     * @author   Kumbi
     * used to join two strings
     * @param string1 , string2
     */

    public String join(String string1,String string2){

        return string1+string2;
    }


    /**
     * @author   Kumbi
     * gets floor height and enquires presence of dome from each square
     * and saves it in the array of strings boardView.
     */

    Object buildFloors(){

        for (int i = 0; i < 5; i ++) {

            for (int j = 0; j <5 ; j ++) {

                int[] pos = {i+1,j+1};         //  because at i= 0 it throws outofBound exception


                //boardView[i][j] = join(join(Integer.toString(board.getFloorFrom(pos))," "),Boolean.toString(board.squareHasDome(pos)));

            }

        }
        return boardView;
    }


    /**
     * @author   Kumbi
     * gets position of each player's workers and adds their workerId in
     * the right square
     */

    Object addPlayers(){

        int[] workerPosition = new int[2] ;


        for (int i = 0; i < 2; i++) {

            for (int j= 0; j <2; j++){

                //workerPosition = player[i].getWorker(j+1).getPosition();

                String workerIdentifierStr = " P"+(i+1)+"W"+(j+1);

                boardView[workerPosition[0]][workerPosition[1]] = boardView[workerPosition[0]][workerPosition[1]].concat(workerIdentifierStr);
            }

        }
        return boardView;

    }


    /**
     * @author   Kumbi
     * used to provide the right indentation for the clear representation
     * of each square
     * @param myString string
     * @param nTabsToIndent number of tabs to indent
     */

    private String indent(String myString,int nTabsToIndent) {
        return String.format("%-"+nTabsToIndent+"s",myString);
    }


    /**
     * @author   Kumbi
     * displays the boardView
     */

    public void displayboardView(){

        for (int i = 0; i < 5; i ++) {

            System.out.print( BLUE+"\n r" + (i));

            for (int j = 0; j <5 ; j ++) {

                int[] pos = {i+1,j+1};

                System.out.print(indent("  c"+j+" "+boardView[i][j],20));

            }

        }

    }



}
