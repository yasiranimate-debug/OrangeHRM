package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NotFoundPage extends BasePage {

    // Chrome renders its own native error interstitial for this route (the
    // server answers a plain HTTP 404, not an OrangeHRM page), so the page
    // heading is read from the browser's own DOM rather than an OrangeHRM locator.
    private final By errorHeading = By.id("main-message");

    public NotFoundPage(WebDriver driver) {
        super(driver);
    }

    public boolean isErrorPageDisplayed() {
        return isVisibleWithinTimeout(errorHeading, "browser 'page can't be found' error message");
    }

    public String getErrorHeadingText() {
        return getText(errorHeading, "browser error heading");
    }
}
