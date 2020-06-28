package ModelTest;

import controller.BoardGameConstructor;
import controller.TurnManager;
import model.Hypnus;
import model.MovementPhase;
import model.TestActionProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class HypnusTest {
    TurnManager turnManager;
    MovementPhase phase;

    @Before
    public void setTheStartingPositions(){

        turnManager = BoardGameConstructor.construct(new String[]{"Alpha","Beta"}, new int[]{-1,11});
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
        turnManager.getBoard().addFloorTo(new int[]{4,4});
        turnManager.getBoard().addDomeTo(new int[]{3,4});

    }

    @Test
    public void sameLevel(){
        Assert.assertTrue(turnManager.getPlayer()[1].getDeity() instanceof Hypnus);

        for(int i=0;i<2;i++)
            turnManager.getBoard().addFloorTo(new int[]{3,2});

        int[][] actions = new int[][]{new int[]{1,2,3},new int[]{2,2,1}};
        TestActionProvider.getProvider().setMovingActions(actions);

        phase = turnManager.getTurn()[0].getMove();

        phase.doMovement();
        phase.doMovement();

        Assert.assertTrue(Arrays.equals(turnManager.getPlayer()[0].getWorker(1).getPosition(), new int[]{2,3}));
        Assert.assertTrue(Arrays.equals(turnManager.getPlayer()[0].getWorker(2).getPosition(), new int[]{2,1}));

    }

    @Test
    public void workerOneHigher(){
        int[][] actions = new int[][]{new int[]{1,2,3},new int[]{2,2,1}};
        TestActionProvider.getProvider().setMovingActions(actions);

        phase = turnManager.getTurn()[0].getMove();

        phase.doMovement();

        Assert.assertTrue(Arrays.equals(turnManager.getPlayer()[0].getWorker(1).getPosition(), new int[]{3,3}));
        Assert.assertTrue(Arrays.equals(turnManager.getPlayer()[0].getWorker(2).getPosition(), new int[]{2,1}));
    }

    @Test
    public void workerTwoHigher(){

        turnManager.getPlayer()[0].getWorker(2).forced(new int[]{2,2});

        Assert.assertTrue(turnManager.getPlayer()[1].getDeity() instanceof Hypnus);

        int[][] actions = new int[][]{new int[]{2,1,1},new int[]{1,3,2}};
        TestActionProvider.getProvider().setMovingActions(actions);

        phase = turnManager.getTurn()[0].getMove();

        phase.doMovement();

        Assert.assertTrue(Arrays.equals(turnManager.getPlayer()[0].getWorker(1).getPosition(), new int[]{3,2}));
        Assert.assertTrue(Arrays.equals(turnManager.getPlayer()[0].getWorker(2).getPosition(), new int[]{2,2}));

    }
}
