package controller;

import model.*;

import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * a class that at the game's start choose a random god
 */

public class GodCollection {

    static public class IllegalRandomNumberException extends Exception{}


    public static Deity pickARandomGod() throws IllegalRandomNumberException{
        Random rand = new Random();
        int godChose = rand.nextInt(5);

        switch (godChose){
            case 0:
                return new Atlas;
            case 1:
                return new Demetra;
            case 2:
                return new Artemis;
            case 3:
                return new Apollo;
            case 4:
                return new Athena;
            default:
                throw new IllegalRandomNumberException();
        }
    }
}
