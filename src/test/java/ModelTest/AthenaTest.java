package ModelTest;

import controller.BoardGameConstructor;
import controller.TurnManager;
import model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class AthenaTest {

    TurnManager turnManager;
    MovementPhase phaseOne, phaseTwo;

    @Before
    public void setTheStartingPositions(){

        turnManager = BoardGameConstructor.construct(new String[]{"Alpha","Beta"}, new int[]{4,-1});
        turnManager.getPlayer()[0].getWorker(1).forced(new int[]{3,3});
        turnManager.getPlayer()[0].getWorker(2).forced(new int[]{3,2});
        turnManager.getPlayer()[1].getWorker(1).forced(new int[]{1,4});
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
        turnManager.getBoard().addFloorTo(new int[]{4,4});
        turnManager.getBoard().addDomeTo(new int[]{3,4});

    }

    @Test
    public void notGoingUp(){
        int[][] actions = new int[][]{new int[]{1,2,3},new int[]{2,3,3}};
        TestActionProvider.getProvider().setMovingActions(actions);

        phaseOne = turnManager.getTurn()[0].getMove();
        phaseTwo = turnManager.getTurn()[1].getMove();

        phaseOne.doMovement();

        Assert.assertTrue(Arrays.equals(turnManager.getPlayer()[0].getWorker(1).getPosition(), new int[]{2,3}));

        phaseTwo.doMovement();

        Assert.assertTrue(Arrays.equals(turnManager.getPlayer()[1].getWorker(2).getPosition(), new int[]{3,3}));

    }

    @Test
    public void goingUp(){
        int[][] actions = new int[][]{new int[]{1,2,2},new int[]{2,3,3},new int[]{2,5,5}};
        TestActionProvider.getProvider().setMovingActions(actions);

        phaseOne = turnManager.getTurn()[0].getMove();
        phaseTwo = turnManager.getTurn()[1].getMove();

        phaseOne.doMovement();

        Assert.assertTrue(Arrays.equals(turnManager.getPlayer()[0].getWorker(1).getPosition(), new int[]{2,2}));

        phaseTwo.doMovement();

        Assert.assertTrue(Arrays.equals(turnManager.getPlayer()[1].getWorker(2).getPosition(), new int[]{5,5}));

    }
}
