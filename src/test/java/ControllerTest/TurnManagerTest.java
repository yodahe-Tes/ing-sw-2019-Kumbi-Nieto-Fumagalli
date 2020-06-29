package ControllerTest;

import controller.BoardGameConstructor;
import controller.PhaseResult;
import controller.TurnManager;
import model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TurnManagerTest {

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

        turnManager.getPlayer()[0].getWorker(1).forced(new int[]{3,3});
        turnManager.getPlayer()[0].getWorker(2).forced(new int[]{3,2});
        turnManager.getPlayer()[1].getWorker(1).forced(new int[]{4,3});
        turnManager.getPlayer()[1].getWorker(2).forced(new int[]{4,4});


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

        PhaseResult result = turnManager.getTurn()[0].getBuild().doBuild(turnManager.getPlayer()[0].getWorker(1));

        Assert.assertTrue(result==PhaseResult.DEFEAT);
    }

    @Test
    public void threePlayerOneLostOneWon(){

        turnManager= BoardGameConstructor.construct(new String[]{"alpha","beta","gamma"},new int[]{-1,-1,-1});

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
        for(int i=0;i<3;i++){
            turnManager.getBoard().addFloorTo(new int[]{4,4});
            turnManager.getBoard().addFloorTo(new int[]{5,3});
        }
        turnManager.getBoard().addDomeTo(new int[]{3,4});
        for(int i=0;i<2;i++)
            turnManager.getBoard().addFloorTo(new int[]{5,4});

        int[][] movementAction = new int[][]{new int[]{3,3},new int[]{4,3},new int[]{5,1},new int[]{3,2},new int[]{4,4},new int[]{5,4},new int[]{2,3,3},new int[]{2,5,3}};
        TestActionProvider.getProvider().setMovingActions(movementAction);

        BuildingAction[] buildingAction = new BuildingAction[]{new BuildingAction(new int[]{4,4})};
        TestActionProvider.getProvider().setBuildingActions(buildingAction);

        turnManager.startGame();

        Assert.assertEquals(2,turnManager.getPlayer().length);
        Assert.assertEquals(2,turnManager.getTurn().length);

    }

    @Test
    public void threePlayerOneLostOneWonWithOpponentMovementRule(){

        turnManager= BoardGameConstructor.construct(new String[]{"alpha","beta","gamma"},new int[]{4,-1,-1});

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
        for(int i=0;i<3;i++){
            turnManager.getBoard().addFloorTo(new int[]{4,4});
            turnManager.getBoard().addFloorTo(new int[]{5,3});
        }
        turnManager.getBoard().addDomeTo(new int[]{3,4});
        for(int i=0;i<2;i++)
            turnManager.getBoard().addFloorTo(new int[]{5,4});

        int[][] movementAction = new int[][]{new int[]{3,3},new int[]{4,3},new int[]{5,1},new int[]{3,2},new int[]{4,4},new int[]{5,4},new int[]{2,3,3},new int[]{2,5,3}};
        TestActionProvider.getProvider().setMovingActions(movementAction);

        BuildingAction[] buildingAction = new BuildingAction[]{new BuildingAction(new int[]{4,4})};
        TestActionProvider.getProvider().setBuildingActions(buildingAction);

        turnManager.startGame();

        Assert.assertEquals(2,turnManager.getPlayer().length);
        Assert.assertEquals(2,turnManager.getTurn().length);

    }

    @Test
    public void threePlayerOneLostOneWonWithLimus(){

        turnManager= BoardGameConstructor.construct(new String[]{"alpha","beta","gamma"},new int[]{12,0,-1});

        Assert.assertTrue(turnManager.getPlayer()[0].getDeity() instanceof Limus);
        Assert.assertTrue(turnManager.getPlayer()[1].getDeity() instanceof Atlas);

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
        for(int i=0;i<3;i++){
            turnManager.getBoard().addFloorTo(new int[]{4,4});
            turnManager.getBoard().addFloorTo(new int[]{5,3});
        }
        turnManager.getBoard().addDomeTo(new int[]{3,4});
        for(int i=0;i<2;i++)
            turnManager.getBoard().addFloorTo(new int[]{5,4});

        int[][] movementAction = new int[][]{new int[]{3,3},new int[]{4,3},new int[]{5,1},new int[]{3,2},new int[]{4,4},new int[]{5,4},new int[]{1,3,3},new int[]{2,5,3}};
        TestActionProvider.getProvider().setMovingActions(movementAction);

        BuildingAction[] buildingAction = new BuildingAction[]{new BuildingAction(new int[]{4,3},true)};
        TestActionProvider.getProvider().setBuildingActions(buildingAction);

        turnManager.startGame();

        System.out.println(turnManager.getBoard().getFloorFrom(new int[]{4,3}));
        System.out.println(turnManager.getBoard().squareHasDome(new int[]{4,3}));

        Assert.assertEquals(2,turnManager.getPlayer().length);
        Assert.assertEquals(2,turnManager.getTurn().length);
        Assert.assertTrue(turnManager.getBoard().squareHasDome(new int[]{4,3}));


    }
}
