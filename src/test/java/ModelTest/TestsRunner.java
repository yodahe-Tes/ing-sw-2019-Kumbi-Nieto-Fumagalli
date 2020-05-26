package ModelTest;

import model.DefaultMovementPhase;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * a class that runs tests
 */

public class TestsRunner{
    public static void main(String[] args) {

        Result result = JUnitCore.runClasses(DemetraTest.class);


        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());
    }
}
