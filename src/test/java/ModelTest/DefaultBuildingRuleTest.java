package ModelTest;

import controller.BoardGameConstructor;
import controller.TurnManager;
import model.BuildingAction;
import model.DefaultBuildingRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DefaultBuildingRuleTest {

    TurnManager turnManager;
    DefaultBuildingRule rule;


    @Before
    public void setTheStartingPositions(){

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

        rule =new DefaultBuildingRule(turnManager.getBoard());
    }

    @Test
    public void buildADome(){
        Assert.assertFalse(rule.doCheckRule(turnManager.getPlayer()[0].getWorker(1), new BuildingAction(new int[]{2,2}, true)));
    }
    @Test
    public void buildADomeNormally(){
        Assert.assertTrue(rule.doCheckRule(turnManager.getPlayer()[0].getWorker(1), new BuildingAction(new int[]{2,2}, false)));
    }
    @Test
    public void buildNormally(){
        Assert.assertTrue(rule.doCheckRule(turnManager.getPlayer()[0].getWorker(1), new BuildingAction(new int[]{2,3}, false)));
    }
    @Test
    public void buildNormallyBis(){
        Assert.assertTrue(rule.doCheckRule(turnManager.getPlayer()[0].getWorker(1), new BuildingAction(new int[]{2,4}, false)));
    }
    @Test
    public void buildOnAFriendlyWorker(){
        Assert.assertFalse(rule.doCheckRule(turnManager.getPlayer()[0].getWorker(1), new BuildingAction(new int[]{3,2}, false)));
    }
    @Test
    public void buildOnADome(){
        Assert.assertFalse(rule.doCheckRule(turnManager.getPlayer()[0].getWorker(1), new BuildingAction(new int[]{3,4}, false)));
    }
    @Test
    public void buildOnAFullyBuiltSquare(){
        Assert.assertFalse(rule.doCheckRule(turnManager.getPlayer()[0].getWorker(1), new BuildingAction(new int[]{4,2}, false)));
    }
    @Test
    public void buildOnAnEnemyWorker(){
        Assert.assertFalse(rule.doCheckRule(turnManager.getPlayer()[0].getWorker(1), new BuildingAction(new int[]{4,3}, false)));
    }
    @Test
    public void buildOnAnEnemyWorkerBis(){
        Assert.assertFalse(rule.doCheckRule(turnManager.getPlayer()[0].getWorker(1), new BuildingAction(new int[]{4,4}, false)));
    }
    @Test
    public void buildOutsideBoard(){
        turnManager.getPlayer()[0].getWorker(1).forced(new int[]{1,1});
        Assert.assertFalse(rule.doCheckRule(turnManager.getPlayer()[0].getWorker(1), new BuildingAction(new int[]{0,0}, false)));
    }
    @Test
    public void buildFarAway(){
        turnManager.getPlayer()[0].getWorker(1).forced(new int[]{1,1});
        Assert.assertFalse(rule.doCheckRule(turnManager.getPlayer()[0].getWorker(1), new BuildingAction(new int[]{2,3}, false)));
    }
    @Test
    public void buildOnSelf(){
        Assert.assertFalse(rule.doCheckRule(turnManager.getPlayer()[0].getWorker(1), new BuildingAction(new int[]{3,3}, false)));
    }
}
