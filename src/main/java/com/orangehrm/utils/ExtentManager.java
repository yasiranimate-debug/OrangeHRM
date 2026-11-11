package com.orangehrm.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            String browser = ConfigReader.get("browser");
            ExtentSparkReporter spark = new ExtentSparkReporter("reports/ExtentReport-" + browser + ".html");
            spark.config().setDocumentTitle("OrangeHRM UI Automation Report");
            spark.config().setReportName("OrangeHRM Test Execution - " + browser);

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Application", "OrangeHRM Demo");
            extent.setSystemInfo("Browser", browser);
        }
        return extent;
    }

    public static void setTest(ExtentTest extentTest) {
        test.set(extentTest);
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void unload() {
        test.remove();
    }
}
