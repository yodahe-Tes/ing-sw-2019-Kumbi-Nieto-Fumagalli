package controller;

import model.*;

import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * @author Fumagalli
 * a class that at the game's start choose a random god
 */

public class GodCollection {

    static public class IllegalRandomNumberException extends Exception{}

    /**
     * a method that choose a random god
     * @return a random god from the list
     * @throws IllegalRandomNumberException if an error in the generation of a random integer occurs
     */
    public static Deity pickARandomGod() throws IllegalRandomNumberException{
        Random rand = new Random();
        int godChose = rand.nextInt(5);

        switch (godChose){
            case 0:
                return new Atlas();
            case 1:
                return new Demetra();
            case 2:
                return new Artemis();
            case 3:
                return new Apollo();
            case 4:
                return new Athena();
            default:
                throw new IllegalRandomNumberException();
        }
    }
}
