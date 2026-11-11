package com.orangehrm.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    // The suite runs against the live, publicly shared OrangeHRM demo site, not
    // a controlled environment - a one-off slow response or a mid-reset data
    // state from another automated run against the same instance shouldn't
    // fail the whole build, so a failed test gets one retry before it counts.
    private static final int MAX_RETRIES = 1;

    private int retryCount = 0;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRIES) {
            retryCount++;
            return true;
        }
        return false;
    }
}
