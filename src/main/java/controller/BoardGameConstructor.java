package controller;

import model.*;

/**
 * @author Fumagalli
 * Class that constructs the model for a new game
 */
public class BoardGameConstructor {
    public static void construct(String[] playerName) throws GodCollection.IllegalRandomNumberException {

        Deity[] gods = new Deity[playerName.length];

        int noInfiniteLoop=0;

        for(int i=0; i<gods.length; i++){
            try {
                gods[i] = GodCollection.pickARandomGod();
            }catch (GodCollection.IllegalRandomNumberException e){
                i--;
                System.out.println("Error in random number generator!");
                noInfiniteLoop++;
                if (noInfiniteLoop >= 20)
                    throw new GodCollection.IllegalRandomNumberException();
            }
        }


        Player[] player = new Player[playerName.length];

        for(int i = 0; i < player.length; i++){
            player[i] = new Player(playerName[i], gods[i]);
        }

        Board board = new Board(player);

        Turn[] turn = new Turn[playerName.length];

        for (int i=0; i<turn.length; i++){
            turn[i] = createTurn(player[i]);
        }



    }


    private static Turn createTurn(Player player){

        BuildingPhase build;
        MovementPhase move;


        if (player.getDeity() instanceof MovementRule){

        }
        if (player.getDeity() instanceof MovementPhase){}
    }
}




