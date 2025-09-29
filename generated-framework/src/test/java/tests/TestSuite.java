package tests;

import org.testng.ISuite;
import org.testng.ISuiteListener;

public class TestSuite implements ISuiteListener {
    
    @Override
    public void onStart(ISuite suite) {
        System.out.println("=== Starting Test Suite ===");
        System.out.println("All test classes will share a single browser session");
    }
    
    @Override
    public void onFinish(ISuite suite) {
        System.out.println("=== Test Suite Completed ===");
        BaseTest.closeGlobalDriver();
    }
}
