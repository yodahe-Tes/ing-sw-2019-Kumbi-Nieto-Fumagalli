package View;

import model.*;
import java.util.Scanner;


/**
 * @author Kumbi
 * Class with CLI questions for the player that sends the player choices to controller
 */

public class CliQuery {

    private static Scanner scanner;

    /**
     * @author Kumbi
     * Asks the player if he wants to move or build and calls the respective Querymethods
     */

    public static void IntentionQuery() {
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
     * Asks the player which worker he wants to move and returns choice
     */

    public static int workerChoiceQuery() {
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

        return 0;
    }


    /**
     * Asks the player wher he wants to move the worker and passes move action to Controller
     */

    public static int[] moveLocationQuery() {

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
        return new int[0];
    }

    /**
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
     * Asks the player where he wants to build  and what he wants to build and passes build action to Controller
     */

    public static BuildingAction buildLocationAndTypeQuery() {

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


    /**
     * Informs the player that It's not his turn
     */

    public void notYourTUrnMessage() {
        System.out.print("It's not your turn");

    }


    /**
     * Informs the player that It is his turn to make a move
     */

    public void yourTUrnMessage() {

        System.out.print("It's your turn to make a move");

    }


}
