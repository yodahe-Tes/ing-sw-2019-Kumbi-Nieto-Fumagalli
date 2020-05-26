package View;
import Network.Observer;
import model.*;

import java.util.Scanner;

/**
 * @author   Kumbi
 * Class for the CLI View
 */

public class CliView implements Observer<O> {


    private Scanner scanner;
    private ImageBuilder imageBuilder;
    private Board board;
    private BoardWorker boardWorker;
    private Player player;

    /**
     * @author Kumbi
     * Constructor of CLiView
     */

    public CliView(Board board, Player player) {
        this.board = board;
        this.player = player;
        scanner = new Scanner(System.in);
        board.addObs(this);
        boardWorker.addObs(this);
    }



    /**
     * @author Kumbi
     * updates board after notification from Model
     */

    @Override
    public void update() {
        imageBuilder.buildFloors();
        imageBuilder.addPlayers();
        imageBuilder.displayBoardView();

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

    public void workerChoiceQuery() {
        System.out.print("Which worker do you want to move?[1] or [2] \n");
        if (scanner.hasNextInt()) {
            int choice = scanner.nextInt();

            if (choice == 1) {
                MoveLocationQuery(1);
            } else if (choice == 2) {
                MoveLocationQuery(2);
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

    public int[] MoveLocationQuery(int workerId) {

        System.out.print("Where do you want to move it to? (row,column)\n");
        if (scanner.hasNext()) {
            {
                String s = scanner.next();
                try {
                    String[] input = s.split(",");

                    int[] destination = {Integer.parseInt(input[0]), Integer.parseInt(input[1])};

                        return BuildingAction buildingAction = new BuildingAction(destination,workerId);

                } catch (NumberFormatException e) {
                    System.out.println("Please provide integer values as coordinates");
                }
            }

        }
        else {

        }
        return movementAction;
    }


    /**
     * @param workerId worker Id number
     * @author Kumbi
     * Asks the player where he wants to build and passes build action to Controller
     */

    public Object buildLocationAndTypeQuery(int workerId) {
        System.out.print("Where do you want to build ? (row,column)\n");
        if (scanner.hasNext()) {
            {
                String str = scanner.next();
                try {
                    String[] input = str.split(",");


                    int[] buildLocation = {Integer.parseInt(input[0]), Integer.parseInt(input[1])};

                    System.out.print("Do you want to build a [1]Block or a [2]Dome ? input [1] or[2]\n");
                    if (scanner.hasNext()) {
                        {
                            int choice = scanner.nextInt();

                            if (choice == 1) {
                                return BuildingAction buildingAction = new BuildingAction(buildLocation, false);
                            } else if (choice == 2) {
                                return BuildingAction buildingAction = new BuildingAction(buildLocation, true);
                            } else {
                                System.out.print("input [1] or [2]");
                                workerChoiceQuery();
                            }
                        }
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Please provide integer values as coordinates");
                }
            }

        }
    }

}

