package model;

/*TODO
*  Handle ArrayIndexOutOfBoundsException
 */


/**
 * A class modelling a Santorini boardgame player
 * @author Fumagalli
 */

public class Player {

    private final Deity deity;

    private final String  nickname;

    private final BoardWorker[] worker;


    /**
     * Class constructor. Stores the deity and the nickname of the player, creates the workers.
     * @param playerName of player
     * @param god the player owns for the game
     */
    public Player( String playerName , Deity god) {

        deity = god;

        nickname = playerName;

        worker= new BoardWorker[2];

        for(BoardWorker workerNew : worker){
            workerNew = new BoardWorker();
        }
    }


    /**
     * getter for nickname
     * @return the player's nickname
     */
    public String getNickname() {
        return nickname;
    }


    /**
     * getter for Deity
     * @return the player's deity
     */
    public Deity getDeity(){
        return deity;
    }


    /**
     * allows those who has a player reference to move its workers
     * @param workerNum is the worker's number ID (1 or 2 in normal games)
     * @param row is the row part of the square where the worker will move
     * @param column is the column part of the square where the worker will move
     */
    public void moveWorker(int workerNum, int row, int column){
            worker[workerNum-1].move(row, column);
    }


    /**
     * allows those who has a player reference to get its workers' position
     * @param workerNum is the number which identifies the worker (1 or 2 in normal games)
     * @return the worker's position
     */
    public int[] workerPosition(int workerNum){
            return worker[workerNum-1].getPosition();
    }



}
