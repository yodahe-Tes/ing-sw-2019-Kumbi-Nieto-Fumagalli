package ModelTest;

import controller.BoardGameConstructor;
import controller.TurnManager;
import model.BuildingAction;
import model.Limus;
import model.BuildingPhase;
import model.TestActionProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class LimusTest {
    TurnManager turnManager;
    BuildingPhase phase;

    @Before
    public void setTheStartingPositions(){

        turnManager = BoardGameConstructor.construct(new String[]{"Alpha","Beta"}, new int[]{-1,12});
        turnManager.getPlayer()[0].getWorker(1).forced(new int[]{2,3});
        turnManager.getPlayer()[0].getWorker(2).forced(new int[]{3,2});
        turnManager.getPlayer()[1].getWorker(1).forced(new int[]{1,3});
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
    public void buildNotNeighboring(){
        Assert.assertTrue(turnManager.getPlayer()[1].getDeity() instanceof Limus);

        BuildingAction[] actions = new BuildingAction[]{new BuildingAction(new int[]{2,2})};
        TestActionProvider.getProvider().setBuildingActions(actions);

        phase = turnManager.getTurn()[0].getBuild();

        phase.doBuild(turnManager.getPlayer()[0].getWorker(2));

        Assert.assertTrue(turnManager.getBoard().squareHasDome(new int[]{2,2}));

    }

    @Test
    public void buildNeighboring(){
        BuildingAction[] actions = new BuildingAction[]{new BuildingAction(new int[]{2,3}),new BuildingAction(new int[]{2,2})};
        TestActionProvider.getProvider().setBuildingActions(actions);

        phase = turnManager.getTurn()[0].getBuild();

        phase.doBuild(turnManager.getPlayer()[0].getWorker(2));

        Assert.assertTrue(turnManager.getBoard().squareHasDome(new int[]{2,2}));
        Assert.assertEquals(2,turnManager.getBoard().getFloorFrom(new int[]{2,3}));
    }
}
