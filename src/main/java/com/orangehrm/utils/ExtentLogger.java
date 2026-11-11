package com.orangehrm.utils;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;

public class ExtentLogger {

    public static void pass(String message) {
        ExtentManager.getTest().log(Status.PASS, message);
    }

    public static void info(String message) {
        ExtentManager.getTest().log(Status.INFO, message);
    }

    public static void fail(String message) {
        ExtentManager.getTest().log(Status.FAIL, message);
    }

    public static void warning(String message) {
        ExtentManager.getTest().log(Status.WARNING, message);
    }

    public static void withScreenshot(Status status, String message, WebDriver driver) {
        String base64 = ScreenshotUtil.captureBase64(driver);
        if (base64 != null) {
            ExtentManager.getTest().log(status, message,
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
        } else {
            ExtentManager.getTest().log(status, message);
        }
    }
}
