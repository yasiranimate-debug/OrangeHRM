package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NotFoundPage extends BasePage {

    // The server answers this route with an empty HTTP 404 body, so the
    // OrangeHRM Vue app never mounts its #app root - that absence is what
    // this page checks, rather than either browser's own native error UI
    // (Chrome and Firefox render completely different markup for an empty
    // error response, so asserting on either one is not portable).
    @FindBy(id = "app")
    private WebElement appRoot;

    public NotFoundPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAppLoaded() {
        return isDisplayed(appRoot, "OrangeHRM app root (#app)");
    }
}
