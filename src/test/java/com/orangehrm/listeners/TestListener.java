package com.orangehrm.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.orangehrm.utils.DriverFactory;
import com.orangehrm.utils.ExtentLogger;
import com.orangehrm.utils.ExtentManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    public static void createExtentTest(ITestResult result) {
        // API tests have no @BeforeMethod/BaseTest, so the entry is created here instead.
        if (ExtentManager.getTest() == null) {
            ExtentTest test = ExtentManager.getInstance().createTest(
                    result.getMethod().getQualifiedName().replaceAll(".*\\.", ""),
                    result.getMethod().getDescription());
            ExtentManager.setTest(test);
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        createExtentTest(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Browser is still open here - TestNG calls this before @AfterMethod closes it.
        ExtentLogger.withScreenshot(Status.PASS, "Test passed", DriverFactory.getDriver());
        ExtentManager.unload();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentLogger.withScreenshot(Status.FAIL,
                "Test failed : " + result.getThrowable(), DriverFactory.getDriver());
        ExtentManager.unload();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentManager.getTest().log(Status.SKIP, "Test skipped : " + result.getThrowable());
        ExtentManager.unload();
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.getInstance().flush();
    }
}
