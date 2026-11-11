package com.orangehrm.base;

import com.orangehrm.listeners.TestListener;
import com.orangehrm.utils.ConfigReader;
import com.orangehrm.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners(TestListener.class)
public class BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUp(ITestResult result) {
        // Created here, not in the listener: TestNG calls onTestStart only after
        // @BeforeMethod finishes, and test classes open their starting page in
        // their own @BeforeMethod - creating the entry in the listener would log
        // those first steps into the previous test's report entry.
        TestListener.createExtentTest(result);
        DriverFactory.initDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    protected WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

    protected String getBaseUrl() {
        return ConfigReader.get("baseUrl");
    }

    protected String getValidUsername() {
        return ConfigReader.get("validUsername");
    }

    protected String getValidPassword() {
        return ConfigReader.get("validPassword");
    }
}
