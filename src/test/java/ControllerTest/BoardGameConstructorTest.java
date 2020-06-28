package ControllerTest;

import controller.BoardGameConstructor;
import controller.PhaseResult;
import controller.TurnManager;
import model.BuildingAction;
import model.Minotaur;
import model.Player;
import model.TestActionProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BoardGameConstructorTest {

    TurnManager turnManager;

    @Test
    public void testEveryPlayerHasAGod(){
        turnManager = BoardGameConstructor.construct(new String[]{"alpha","beta","gamma"});

        boolean everyPlayerHasAGod=true;

        for (Player player : turnManager.getPlayer()){
            if(player.getDeity()==null){
                everyPlayerHasAGod=false;
                break;
            }
        }

        Assert.assertTrue(everyPlayerHasAGod);
    }

    @Test
    public void testTwoPlayerOneLostMovement(){
        turnManager = BoardGameConstructor.construct(new String[]{"alpha","beta"});

        for(int i = 0; i<4; i++) {
            turnManager.getBoard().addFloorTo(new int[]{4,2});
            turnManager.getBoard().addFloorTo(new int[]{2,4});
            turnManager.getBoard().addFloorTo(new int[]{2,3});
            turnManager.getBoard().addFloorTo(new int[]{2,2});
            turnManager.getBoard().addFloorTo(new int[]{2,1});
            turnManager.getBoard().addFloorTo(new int[]{3,1});
            turnManager.getBoard().addFloorTo(new int[]{4,1});
        }
        for(int i=0;i<1;i++)
            turnManager.getBoard().addFloorTo(new int[]{3,3});
        for(int i=0;i<3;i++)
            turnManager.getBoard().addFloorTo(new int[]{4,4});
        turnManager.getBoard().addDomeTo(new int[]{3,4});

        int[][]actions = new int[][]{new int[]{3,3},new int[]{4,3},new int[]{3,2},new int[]{4,4}};
        TestActionProvider.getProvider().setMovingActions(actions);

        turnManager.startGame();
        PhaseResult result = turnManager.getTurn()[0].getMove().doMovement().getResult();

        Assert.assertTrue(result==PhaseResult.DEFEAT);

    }

    @Test
    public void testTwoPlayerOneLostBuilding(){
        turnManager = BoardGameConstructor.construct(new String[]{"alpha","beta"});

        for(int i = 0; i<4; i++) {

            turnManager.getPlayer()[0].getWorker(1).forced(new int[]{3,3});
            turnManager.getPlayer()[0].getWorker(2).forced(new int[]{3,2});
            turnManager.getPlayer()[1].getWorker(1).forced(new int[]{4,3});
            turnManager.getPlayer()[1].getWorker(2).forced(new int[]{4,4});

            turnManager.getBoard().addFloorTo(new int[]{4,2});
            turnManager.getBoard().addFloorTo(new int[]{2,4});
            turnManager.getBoard().addFloorTo(new int[]{2,3});
            turnManager.getBoard().addFloorTo(new int[]{2,2});
            turnManager.getBoard().addFloorTo(new int[]{2,1});
            turnManager.getBoard().addFloorTo(new int[]{3,1});
            turnManager.getBoard().addFloorTo(new int[]{4,1});
        }
        for(int i=0;i<1;i++)
            turnManager.getBoard().addFloorTo(new int[]{3,3});
        for(int i=0;i<3;i++)
            turnManager.getBoard().addFloorTo(new int[]{4,4});
        turnManager.getBoard().addDomeTo(new int[]{3,4});

        PhaseResult result = turnManager.getTurn()[0].getBuild().doBuild(turnManager.getPlayer()[0].getWorker(1));

        Assert.assertTrue(result==PhaseResult.DEFEAT);
    }

}
