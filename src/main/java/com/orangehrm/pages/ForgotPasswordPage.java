package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ForgotPasswordPage extends BasePage {

    @FindBy(css = "h6.oxd-text--h6")
    private WebElement pageHeading;

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    public String getPageHeading() {
        return getText(pageHeading, "'Reset Password' page heading");
    }
}
