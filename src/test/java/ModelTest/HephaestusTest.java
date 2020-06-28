package ModelTest;

import controller.BoardGameConstructor;
import controller.TurnManager;
import model.BuildingAction;
import model.BuildingPhase;
import model.Hephaestus;
import model.TestActionProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HephaestusTest {
    private TurnManager turnManager;

    BuildingPhase phase;

    @Before
    public void prepareBoard(){


        turnManager = BoardGameConstructor.construct(new String[]{"Alpha","Beta"}, new int[]{5,-1});
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

        phase = turnManager.getTurn()[0].getBuild();

    }

    @Test
    public void testBuild(){

        Assert.assertTrue(turnManager.getPlayer()[0].getDeity() instanceof Hephaestus);

        BuildingAction[] actions = new BuildingAction[]{new BuildingAction(new int[]{2,2})};
        TestActionProvider.getProvider().setBuildingActions(actions);

        boolean[] doOtherActions= new boolean[] {false};
        TestActionProvider.getProvider().setBooleanRequest(doOtherActions);



        phase.doBuild(turnManager.getPlayer()[0].getWorker(1));

        Assert.assertTrue(turnManager.getBoard().squareHasDome(new int[]{2,2}));
    }

    @Test
    public void testFailedBuild(){

        Assert.assertTrue(turnManager.getPlayer()[0].getDeity() instanceof Hephaestus);

        BuildingAction[] actions = new BuildingAction[]{new BuildingAction(new int[]{3,2}),new BuildingAction(new int[]{2,2})};
        TestActionProvider.getProvider().setBuildingActions(actions);

        boolean[] doOtherActions= new boolean[] {false};
        TestActionProvider.getProvider().setBooleanRequest(doOtherActions);

        phase.doBuild(turnManager.getPlayer()[0].getWorker(1));

        Assert.assertTrue(turnManager.getBoard().squareHasDome(new int[]{2,2}));
        Assert.assertTrue(turnManager.getBoard().getFloorFrom(new int[]{3,2})==0);
    }

    @Test
    public void twoBuilds(){
        Assert.assertTrue(turnManager.getPlayer()[0].getDeity() instanceof Hephaestus);

        BuildingAction[] actions = new BuildingAction[]{new BuildingAction(new int[]{2,4})};
        TestActionProvider.getProvider().setBuildingActions(actions);

        boolean[] doOtherActions= new boolean[] {true};
        TestActionProvider.getProvider().setBooleanRequest(doOtherActions);

        phase.doBuild(turnManager.getPlayer()[0].getWorker(1));

        Assert.assertEquals(3,turnManager.getBoard().getFloorFrom(new int[]{2,4}));
    }

    @Test
    public void twoFailedBuilds(){
        Assert.assertTrue(turnManager.getPlayer()[0].getDeity() instanceof Hephaestus);

        BuildingAction[] actions = new BuildingAction[]{new BuildingAction(new int[]{3,4}),new BuildingAction(new int[]{2,3})};
        TestActionProvider.getProvider().setBuildingActions(actions);

        boolean[] doOtherActions= new boolean[] {true};
        TestActionProvider.getProvider().setBooleanRequest(doOtherActions);

        phase.doBuild(turnManager.getPlayer()[0].getWorker(1));

        Assert.assertEquals(3,turnManager.getBoard().getFloorFrom(new int[]{2,3}));
    }
}

