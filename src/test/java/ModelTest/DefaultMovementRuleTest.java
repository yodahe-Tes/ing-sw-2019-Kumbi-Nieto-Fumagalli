package ModelTest;

import controller.BoardGameConstructor;
import controller.TurnManager;
import model.DefaultMovementRule;
import model.MovementAction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DefaultMovementRuleTest {

    TurnManager turnManager;
    DefaultMovementRule rule;


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

        rule =new DefaultMovementRule(turnManager.getBoard());



    }

    @Test
    public void testMoveUpOne(){
        Assert.assertTrue(rule.doCheckRule(new MovementAction(turnManager.getPlayer()[0].getWorker(1), new int[] {2,2})));
    }
    @Test
    public void moveOnSameFloorTest(){
        Assert.assertTrue(rule.doCheckRule(new MovementAction(turnManager.getPlayer()[0].getWorker(1), new int[] {2,3})));
    }
    @Test
    public void moveDownTest() {
        Assert.assertTrue(rule.doCheckRule(new MovementAction(turnManager.getPlayer()[0].getWorker(1), new int[]{2, 4})));
    }
    @Test
    public void moveOnFriendlyWorker(){
        Assert.assertFalse(rule.doCheckRule(new MovementAction(turnManager.getPlayer()[0].getWorker(1), new int[] {3,2})));
    }
    @Test
    public void moveOnLowerDome(){
        Assert.assertFalse(rule.doCheckRule(new MovementAction(turnManager.getPlayer()[0].getWorker(1), new int[] {3,4})));
    }
    @Test
    public void moveOnFullyConstructedSquare(){
        Assert.assertFalse(rule.doCheckRule(new MovementAction(turnManager.getPlayer()[0].getWorker(1), new int[] {4,2})));
    }
    @Test
    public void moveOnEnemyWorker(){
        Assert.assertFalse(rule.doCheckRule(new MovementAction(turnManager.getPlayer()[0].getWorker(1), new int[] {4,3})));
    }
    @Test
    public void moveOnEnemyWorkerTwo(){
        Assert.assertFalse(rule.doCheckRule(new MovementAction(turnManager.getPlayer()[0].getWorker(1), new int[] {4,4})));
    }
    @Test
    public void moveOutsideBoard(){
        turnManager.getPlayer()[0].getWorker(1).forced(new int[]{1,1});
        Assert.assertFalse(rule.doCheckRule(new MovementAction(turnManager.getPlayer()[0].getWorker(1), new int[] {0,1})));
    }
    @Test
    public void moveMoreThanOneSquare(){
        Assert.assertFalse(rule.doCheckRule(new MovementAction(turnManager.getPlayer()[0].getWorker(1), new int[] {4,5})));
    }
    @Test
    public void moveTooUp(){
        turnManager.getPlayer()[0].getWorker(1).forced(new int[]{1,1});
        Assert.assertFalse(rule.doCheckRule(new MovementAction(turnManager.getPlayer()[0].getWorker(1), new int[] {2,2})));
    }
    @Test
    public void moveOnSelf(){
        Assert.assertFalse(rule.doCheckRule(new MovementAction(turnManager.getPlayer()[0].getWorker(1), new int[] {3,3})));
    }
}
