package com.orangehrm.base;

import com.orangehrm.utils.ConfigReader;
import com.orangehrm.utils.ExtentLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        long explicitWait = Long.parseLong(ConfigReader.get("explicitWait"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
        PageFactory.initElements(driver, this);
    }

    protected void click(WebElement element, String elementName) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            ExtentLogger.pass("Clicked on " + elementName);
        } catch (Exception e) {
            ExtentLogger.fail("Unable to click on " + elementName + " : " + e.getMessage());
            throw new RuntimeException("Unable to click on " + elementName, e);
        }
    }

    protected void click(By locator, String elementName) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            ExtentLogger.pass("Clicked on " + elementName);
        } catch (Exception e) {
            ExtentLogger.fail("Unable to click on " + elementName + " : " + e.getMessage());
            throw new RuntimeException("Unable to click on " + elementName, e);
        }
    }

    protected void type(WebElement element, String text, String elementName) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            element.clear();
            element.sendKeys(text);
            ExtentLogger.pass("Typed '" + text + "' into " + elementName);
        } catch (Exception e) {
            ExtentLogger.fail("Unable to type into " + elementName + " : " + e.getMessage());
            throw new RuntimeException("Unable to type into " + elementName, e);
        }
    }

    protected String getText(WebElement element, String elementName) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            String text = element.getText();
            ExtentLogger.pass("Read text from " + elementName + " : " + text);
            return text;
        } catch (Exception e) {
            ExtentLogger.fail("Unable to read text from " + elementName + " : " + e.getMessage());
            throw new RuntimeException("Unable to read text from " + elementName, e);
        }
    }

    protected String getText(By locator, String elementName) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            String text = element.getText();
            ExtentLogger.pass("Read text from " + elementName + " : " + text);
            return text;
        } catch (Exception e) {
            ExtentLogger.fail("Unable to read text from " + elementName + " : " + e.getMessage());
            throw new RuntimeException("Unable to read text from " + elementName, e);
        }
    }

    /** Waits up to the explicit timeout - use when the element IS expected to appear. */
    protected boolean isVisibleWithinTimeout(By locator, String elementName) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            ExtentLogger.pass(elementName + " is visible");
            return true;
        } catch (Exception e) {
            ExtentLogger.fail(elementName + " did not become visible : " + e.getMessage());
            return false;
        }
    }

    /** Waits up to the explicit timeout - use when the element IS expected to appear. */
    protected boolean isVisibleWithinTimeout(WebElement element, String elementName) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            ExtentLogger.pass(elementName + " is visible");
            return true;
        } catch (Exception e) {
            ExtentLogger.fail(elementName + " did not become visible : " + e.getMessage());
            return false;
        }
    }

    /** No waiting - use when the element is NOT expected, so a false result returns immediately. */
    protected boolean isDisplayed(WebElement element, String elementName) {
        try {
            boolean displayed = element.isDisplayed();
            ExtentLogger.info(elementName + " displayed : " + displayed);
            return displayed;
        } catch (Exception e) {
            ExtentLogger.info(elementName + " is not displayed");
            return false;
        }
    }

    protected List<WebElement> findAll(By locator, String elementName) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            List<WebElement> elements = driver.findElements(locator);
            ExtentLogger.pass("Found " + elements.size() + " " + elementName);
            return elements;
        } catch (Exception e) {
            ExtentLogger.fail("Unable to find " + elementName + " : " + e.getMessage());
            throw new RuntimeException("Unable to find " + elementName, e);
        }
    }

    /** Waits for visibility (not just presence) before reading text - avoids an empty
     *  string read mid-animation, e.g. a dropdown menu that is still sliding open. */
    protected List<WebElement> findAllVisible(By locator, String elementName) {
        try {
            List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
            ExtentLogger.pass("Found " + elements.size() + " " + elementName);
            return elements;
        } catch (Exception e) {
            ExtentLogger.fail("Unable to find " + elementName + " : " + e.getMessage());
            throw new RuntimeException("Unable to find " + elementName, e);
        }
    }

    protected List<String> textsOf(List<WebElement> elements) {
        return elements.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public void open(String url) {
        try {
            driver.get(url);
            ExtentLogger.pass("Opened URL : " + url);
        } catch (Exception e) {
            ExtentLogger.fail("Unable to open URL : " + url + " : " + e.getMessage());
            throw new RuntimeException("Unable to open URL : " + url, e);
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        return driver.getTitle();
    }
}
