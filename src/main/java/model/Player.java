package model;

import View.CliView;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * A class modelling a Santorini boardgame player
 * @author Fumagalli
 */

public class Player {

    private Deity deity;

    private final String  nickname;

    private final BoardWorker[] worker;

    private CliView view;


    /**
     * Class constructor. Stores the deity and the nickname of the player, creates the workers.
     * @param playerName of player
     */
    public Player( String playerName) {

        nickname = playerName;

       BoardWorker[] initWorker= new BoardWorker[2];

        for(int i=0;i<2;i++){
            initWorker[i] = new BoardWorker();
        }

        worker = initWorker;

        deity=null;
        view = null;
    }


    /**
     * Getter for nickname
     * @return the player's nickname
     */
    public String getNickname() {
        return nickname;
    }


    /**
     * Getter for Deity
     * @return the player's deity
     */
    public Deity getDeity(){
        return deity;
    }


    /**
     * A method that allows those who has a player reference to get its workers' position
     * @param workerNum is the number which identifies the worker (1 or 2 in normal games)
     * @return the worker's position
     */
    public int[] workerPosition(int workerNum){
            return worker[workerNum-1].getPosition();
    }

    /**
     * Returns a chosen worker
     * @param workerNum the chosen worker's ID
     * @return the chosen worker
     */
    public BoardWorker getWorker(int workerNum){
        return worker[workerNum-1];
    }

    public BoardWorker[] getWorker(){ return worker; }

    /**
     * This method allows only one initialization for player's god
     * @param god the god the player will uses as its
     */
    public void setDeity(Deity god){
        if(deity==null)
            deity=god;
    }

    /**
     * Establish a reference to the player's client
     * @param
     */
    public void addCliView(CliView view){
        this.view=view;
    }

    /**
     * Getter for the view linked to the player
     * @return the needed ClientView
     */
    public CliView getView(){return view;}

    /**
     * A method that get the desc from the player's god
     * @return the desc of the player's god
     */
    public String godDesc(){return deity.desc();}

    public void closeConnection(){
        view.closeConnection();
    }
}
