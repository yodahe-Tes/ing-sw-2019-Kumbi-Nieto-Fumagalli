package ControllerTest;

import controller.BoardGameConstructor;
import controller.TurnManager;
import model.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BoardGameConstructorTest {

    TurnManager manager;

    @Before
    public void setUpEveryPlayerHasAGod(){
        manager = BoardGameConstructor.construct(new String[]{"alpha","beta","gamma1"});
    }

    @Test
    public void testEveryPlayerHasAGod(){
        boolean everyPlayerHasAGod=true;

        for (Player player : manager.getPlayer()){
            if(player.getDeity()==null){
                everyPlayerHasAGod=false;
                break;
            }
        }

        Assert.assertTrue(everyPlayerHasAGod);
    }
}
