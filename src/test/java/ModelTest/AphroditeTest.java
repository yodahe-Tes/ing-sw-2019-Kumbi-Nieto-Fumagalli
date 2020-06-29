package ModelTest;

import controller.BoardGameConstructor;
import controller.TurnManager;
import model.Aphrodite;
import model.MovementPhase;
import model.TestActionProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class AphroditeTest {
    TurnManager turnManager;
    MovementPhase phase;

    @Before
    public void setTheStartingPositions(){

        turnManager = BoardGameConstructor.construct(new String[]{"Alpha","Beta"}, new int[]{-1,9});
        turnManager.getPlayer()[0].getWorker(1).forced(new int[]{3,3});
        turnManager.getPlayer()[0].getWorker(2).forced(new int[]{3,2});
        turnManager.getPlayer()[1].getWorker(1).forced(new int[]{2,4});
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
    public void notNeighboringToNotNeighboring(){
        Assert.assertTrue(turnManager.getPlayer()[1].getDeity() instanceof Aphrodite);

        int[][] actions = new int[][]{new int[]{2,2,2},new int[]{2,2,1}};
        TestActionProvider.getProvider().setMovingActions(actions);

        phase = turnManager.getTurn()[0].getMove();

        phase.doMovement();

        Assert.assertTrue(Arrays.equals(turnManager.getPlayer()[0].getWorker(2).getPosition(), new int[]{2,1}));

    }

    @Test
    public void notNeighboringToNeighboring(){
        int[][] actions = new int[][]{new int[]{2,4,3}};
        TestActionProvider.getProvider().setMovingActions(actions);

        phase = turnManager.getTurn()[0].getMove();

        phase.doMovement();

        Assert.assertTrue(Arrays.equals(turnManager.getPlayer()[0].getWorker(2).getPosition(), new int[]{4,3}));
    }

    @Test
    public void NeighboringToNeighboring(){
        Assert.assertTrue(turnManager.getPlayer()[1].getDeity() instanceof Aphrodite);

        int[][] actions = new int[][]{new int[]{1,2,3}};
        TestActionProvider.getProvider().setMovingActions(actions);

        phase = turnManager.getTurn()[0].getMove();

        phase.doMovement();

        Assert.assertTrue(Arrays.equals(turnManager.getPlayer()[0].getWorker(1).getPosition(), new int[]{2,3}));

    }

    @Test
    public void neighboringToNotNeighboring(){
        Assert.assertTrue(turnManager.getPlayer()[1].getDeity() instanceof Aphrodite);

        int[][] actions = new int[][]{new int[]{1,2,2},new int[]{1,2,3}};
        TestActionProvider.getProvider().setMovingActions(actions);

        phase = turnManager.getTurn()[0].getMove();

        phase.doMovement();

        Assert.assertTrue(Arrays.equals(turnManager.getPlayer()[0].getWorker(1).getPosition(), new int[]{2,3}));

    }
}
