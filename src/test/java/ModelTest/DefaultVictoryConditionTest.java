package ModelTest;

import controller.BoardGameConstructor;
import controller.PhaseResult;
import controller.TurnManager;
import controller.VictoryConditionChecker;
import model.DefaultVictoryCondition;
import model.MovementPhase;
import model.MovementPhaseResult;
import model.TestActionProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class DefaultVictoryConditionTest {

    private TurnManager turnManager;
    private MovementPhase phase;

    @Before
    public void prepareBoard(){

        turnManager = BoardGameConstructor.construct(new String[]{"Alpha","Beta"}, new int[]{-1,-1});
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
    public void notVictory(){
        int[][] actions = new int[][]{new int[]{1,2,3}};
        TestActionProvider.getProvider().setMovingActions(actions);

        MovementPhaseResult result=phase.doMovement();

        Assert.assertTrue(result.getResult()==PhaseResult.NEXT);
    }

    @Test
    public void victory(){
        int[][] actions = new int[][]{new int[]{1,2,2}};
        TestActionProvider.getProvider().setMovingActions(actions);

        MovementPhaseResult result=phase.doMovement();

        Assert.assertTrue(result.getResult()==PhaseResult.VICTORY);
    }
}
