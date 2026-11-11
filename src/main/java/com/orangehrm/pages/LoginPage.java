package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class LoginPage extends BasePage {

    @FindBy(name = "username")
    private WebElement usernameInput;

    @FindBy(name = "password")
    private WebElement passwordInput;

    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    @FindBy(css = ".orangehrm-login-branding img")
    private WebElement loginLogo;

    @FindBy(css = "p.orangehrm-login-forgot-header")
    private WebElement forgotPasswordLink;

    private final By alertMessage = By.cssSelector(".oxd-alert-content-text");
    private final By requiredFieldMessages = By.cssSelector("span.oxd-text--span");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoginLogoDisplayed() {
        return isVisibleWithinTimeout(loginLogo, "OrangeHRM login logo");
    }

    public boolean isUsernameFieldDisplayed() {
        return isVisibleWithinTimeout(usernameInput, "username field");
    }

    public boolean isPasswordFieldDisplayed() {
        return isVisibleWithinTimeout(passwordInput, "password field");
    }

    public DashboardPage loginAs(String username, String password) {
        type(usernameInput, username, "username field");
        type(passwordInput, password, "password field");
        click(loginButton, "'Login' button");
        return new DashboardPage(driver);
    }

    public void submitEmptyForm() {
        click(loginButton, "'Login' button");
    }

    public String getInvalidCredentialsMessage() {
        return getText(alertMessage, "invalid credentials alert");
    }

    public List<String> getRequiredFieldMessages() {
        return textsOf(findAll(requiredFieldMessages, "required field validation messages"));
    }

    public ForgotPasswordPage clickForgotPassword() {
        click(forgotPasswordLink, "'Forgot your password?' link");
        return new ForgotPasswordPage(driver);
    }
}
