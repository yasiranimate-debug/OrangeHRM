package com.orangehrm.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    // One driver per thread, so parallel="classes" runs do not share a browser.
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver() {
        String browser = ConfigReader.get("browser");
        boolean headless = Boolean.parseBoolean(ConfigReader.get("headless"));

        WebDriver webDriver;
        if ("firefox".equalsIgnoreCase(browser)) {
            FirefoxOptions options = new FirefoxOptions();
            if (headless) {
                options.addArguments("-headless", "--width=1920", "--height=1080");
            }
            webDriver = new FirefoxDriver(options);
        } else {
            ChromeOptions options = new ChromeOptions();
            if (headless) {
                // Headless Chrome keeps its small default viewport even after maximize(),
                // which collapses the site's responsive layout (e.g. filter panels) -
                // an explicit size avoids that.
                options.addArguments("--headless=new", "--window-size=1920,1080");
            }
            options.addArguments("--remote-allow-origins=*");
            webDriver = new ChromeDriver(options);
        }

        if (!headless) {
            // In headless mode maximize() resets the window back to Chrome's tiny
            // default (800x600) instead of enlarging it, which collapses the site's
            // responsive layout - the --window-size argument above covers headless.
            webDriver.manage().window().maximize();
        }
        driver.set(webDriver);
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
