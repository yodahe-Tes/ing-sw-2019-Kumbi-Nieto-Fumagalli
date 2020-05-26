package ModelTest;

import org.junit.*;
import model.BoardWorker;

public class BoardWorkerTest {

    BoardWorker worker;

    @BeforeClass
    public static void setUpClass(){
    }

    @Before
    public void setUp(){
        worker = new BoardWorker();}

    @Test
    public void testMove(){
        int[] move = new int[]{2, 2};
        worker.move(move);
        Assert.assertEquals(worker.getPosition(), move);
    }

    @After
    public void tearDown(){}

    @AfterClass
    public static void tearDownClass(){}
}
