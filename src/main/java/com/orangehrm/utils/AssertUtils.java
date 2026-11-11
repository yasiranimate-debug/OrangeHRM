package com.orangehrm.utils;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class AssertUtils {

    public static void verifyEquals(Object actual, Object expected, String description) {
        Assert.assertEquals(actual, expected, description);
        ExtentLogger.pass("Verified " + description + " : " + actual);
    }

    public static void verifyEqualsIgnoreCase(String actual, String expected, String description) {
        Assert.assertTrue(actual != null && actual.equalsIgnoreCase(expected),
                description + " - expected (ignoring case): " + expected + " but got: " + actual);
        ExtentLogger.pass("Verified " + description + " : " + actual);
    }

    public static void verifyTrue(boolean condition, String description) {
        Assert.assertTrue(condition, description);
        ExtentLogger.pass("Verified " + description);
    }

    public static void verifyContains(String actual, String expectedPart, String description) {
        Assert.assertTrue(actual != null && actual.contains(expectedPart),
                description + " - expected to contain: " + expectedPart + " but got: " + actual);
        ExtentLogger.pass("Verified " + description + " : " + actual);
    }

    public static void softVerifyEquals(SoftAssert softAssert, Object actual, Object expected, String description) {
        softAssert.assertEquals(actual, expected, description);
        ExtentLogger.info("Checked " + description + " : " + actual);
    }

    public static void softVerifyTrue(SoftAssert softAssert, boolean condition, String description) {
        softAssert.assertTrue(condition, description);
        ExtentLogger.info("Checked " + description);
    }
}
