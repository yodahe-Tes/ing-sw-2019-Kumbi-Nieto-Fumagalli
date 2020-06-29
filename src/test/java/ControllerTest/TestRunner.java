package ControllerTest;

import ModelTest.BoardWorkerTest;
import controller.BoardGameConstructor;
import controller.TurnManager;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * a class that runs tests
 */

public class TestRunner{
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TurnManagerTest.class);


        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());
    }
}
