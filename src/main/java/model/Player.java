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
     * allows those who has a player reference to get its workers' position
     * @param workerNum is the number which identifies the worker (1 or 2 in normal games)
     * @return the worker's position
     */
    public int[] workerPosition(int workerNum){
            return worker[workerNum-1].getPosition();
    }

    /**
     * returns a chosen worker
     * @param workerNum the chosen worker's ID
     * @return the chosen worker
     */
    public BoardWorker getWorker(int workerNum){
        return worker[workerNum-1];
    }

    public BoardWorker[] getWorker(){ return worker; }

    /**
     * this method allows only one initialization for player's god
     * @param god the god the player will uses as its
     */
    public void setDeity(Deity god){
        if(deity==null)
            deity=god;
    }

    /**
     * establish a reference to the player's client
     * @param view
     */
    public void addCliView(CliView view){
        this.view=view;
    }

    public CliView getView(){return view;}

    public String godDesc(){return deity.desc();}
}
