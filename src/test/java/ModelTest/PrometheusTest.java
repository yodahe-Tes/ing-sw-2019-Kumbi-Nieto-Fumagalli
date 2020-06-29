package ModelTest;

import controller.BoardGameConstructor;
import controller.TurnManager;
import model.BuildingAction;
import model.MovementPhase;
import model.TestActionProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class PrometheusTest {

    private TurnManager turnManager;

    MovementPhase phase;



    @Before
    public void prepareBoard(){


        turnManager = BoardGameConstructor.construct(new String[]{"Alpha","Beta"}, new int[]{8,-1});
        turnManager.getPlayer()[0].getWorker(1).forced(new int[]{3,3});
        turnManager.getPlayer()[0].getWorker(2).forced(new int[]{3,2});
        turnManager.getPlayer()[1].getWorker(1).forced(new int[]{4,3});
        turnManager.getPlayer()[1].getWorker(2).forced(new int[]{4,4});

        for(int i = 0; i<4; i++)
            turnManager.getBoard().addFloorTo(new int[]{4,2});
        for(int i=0; i<3; i++)
            turnManager.getBoard().addFloorTo(new int[]{2,2});
        for(int i=0; i<2;i++)
            turnManager.getBoard().addFloorTo(new int[]{2,3});
        turnManager.getBoard().addFloorTo(new int[]{2,4});
        for(int i=0;i<2;i++)
            turnManager.getBoard().addFloorTo(new int[]{3,3});
        for(int i=0;i<2;i++)
            turnManager.getBoard().addFloorTo(new int[]{4,4});
        turnManager.getBoard().addDomeTo(new int[]{3,4});

        phase = turnManager.getTurn()[0].getMove();

    }

    @Test
    public void testMovement(){

        int[][] actions = new int[][]{new int[]{1,2,2}};
        TestActionProvider.getProvider().setMovingActions(actions);

        boolean[] doOtherActions= new boolean[] {false};
        TestActionProvider.getProvider().setBooleanRequest(doOtherActions);

        phase.doMovement();

        Assert.assertTrue(Arrays.equals(turnManager.getPlayer()[0].getWorker(1).getPosition(), new int[]{2,2}));
    }


    @Test
    public void testFailedMovement(){

        int[][] actions = new int[][]{new int[]{1,3,2},new int[]{1,2,2}};
        TestActionProvider.getProvider().setMovingActions(actions);

        boolean[] doOtherActions= new boolean[] {false};
        TestActionProvider.getProvider().setBooleanRequest(doOtherActions);

        phase.doMovement();

        Assert.assertTrue(Arrays.equals(turnManager.getPlayer()[0].getWorker(1).getPosition(), new int[]{2,2}));
    }

    @Test
    public void testTwoBuild(){

        int[][] actions = new int[][]{new int[]{1,2,3}};
        TestActionProvider.getProvider().setMovingActions(actions);

        boolean[] doOtherActions= new boolean[] {true};
        TestActionProvider.getProvider().setBooleanRequest(doOtherActions);

        BuildingAction[] build = new BuildingAction[]{new BuildingAction(new int[]{2,2})};
        TestActionProvider.getProvider().setBuildingActions(build);

        phase.doMovement();

        int[] finalPosition=turnManager.getPlayer()[0].getWorker(1).getPosition();

        Assert.assertTrue(Arrays.equals(finalPosition, new int[]{2,3}));
        Assert.assertTrue(turnManager.getBoard().squareHasDome(new int[]{2,2}));
    }

    @Test
    public void testThreeFailedBuild(){

        int[][] actions = new int[][]{new int[]{1,2,3}};
        TestActionProvider.getProvider().setMovingActions(actions);

        BuildingAction[] build = new BuildingAction[]{new BuildingAction(new int[]{3,4}),new BuildingAction(new int[]{2,3}),new BuildingAction(new int[]{2,2},true),new BuildingAction(new int[]{2,2})};
        TestActionProvider.getProvider().setBuildingActions(build);

        boolean[] doOtherActions= new boolean[] {true};
        TestActionProvider.getProvider().setBooleanRequest(doOtherActions);

        phase.doMovement();

        int[] finalPosition=turnManager.getPlayer()[0].getWorker(1).getPosition();

        Assert.assertTrue(Arrays.equals(finalPosition, new int[]{2,3}));
        Assert.assertTrue(turnManager.getBoard().squareHasDome(new int[]{2,2}));
    }

    @Test
    public void cantBuildTwoTimes(){
        int[][] actions = new int[][]{new int[]{1,2,2}};
        TestActionProvider.getProvider().setMovingActions(actions);

        BuildingAction[] build = new BuildingAction[]{new BuildingAction(new int[]{2,3})};
        TestActionProvider.getProvider().setBuildingActions(build);

        boolean[] doOtherActions= new boolean[] {true};
        TestActionProvider.getProvider().setBooleanRequest(doOtherActions);

        phase.doMovement();

        int[] finalPosition=turnManager.getPlayer()[0].getWorker(1).getPosition();

        Assert.assertTrue(Arrays.equals(finalPosition, new int[]{2,2}));
        Assert.assertFalse(turnManager.getBoard().getFloorFrom(new int[]{2,3})>2);
    }
}
