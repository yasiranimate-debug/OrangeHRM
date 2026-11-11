package com.orangehrm.tests.login;

import com.orangehrm.base.BaseTest;
import com.orangehrm.dataproviders.TestDataProvider;
import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.ForgotPasswordPage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utils.AssertUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void setUpPage() {
        loginPage = new LoginPage(getDriver());
        loginPage.open(getBaseUrl());
    }

    @Test(description = "TC_LOGIN_001: Login page shows logo, username and password fields",
            groups = {"smoke", "login"})
    public void verifyLoginPageElementsDisplayed() {
        AssertUtils.verifyTrue(loginPage.isLoginLogoDisplayed(), "OrangeHRM logo is displayed");
        AssertUtils.verifyTrue(loginPage.isUsernameFieldDisplayed(), "username field is displayed");
        AssertUtils.verifyTrue(loginPage.isPasswordFieldDisplayed(), "password field is displayed");
    }

    @Test(description = "TC_LOGIN_002: Valid credentials log the user in to the Dashboard",
            groups = {"smoke", "login"})
    public void verifyValidLoginNavigatesToDashboard() {
        DashboardPage dashboardPage = loginPage.loginAs(getValidUsername(), getValidPassword());
        AssertUtils.verifyEquals(dashboardPage.header().getPageTitle(), "Dashboard",
                "page heading after a valid login");
    }

    @Test(dataProvider = "invalidLogins", dataProviderClass = TestDataProvider.class,
            description = "TC_LOGIN_003: Invalid credentials show an 'Invalid credentials' message",
            groups = {"regression", "login"})
    public void verifyInvalidLoginShowsErrorMessage(String username, String password) {
        loginPage.loginAs(username, password);
        AssertUtils.verifyEquals(loginPage.getInvalidCredentialsMessage(), "Invalid credentials",
                "error message for username '" + username + "'");
    }

    @Test(description = "TC_LOGIN_004: Submitting the form with empty fields shows 'Required' messages",
            groups = {"regression", "login"})
    public void verifyEmptyCredentialsShowRequiredFieldMessages() {
        loginPage.submitEmptyForm();
        AssertUtils.verifyEquals(loginPage.getRequiredFieldMessages().size(), 2,
                "number of 'Required' field messages shown");
    }

    @Test(description = "TC_LOGIN_005: 'Forgot your password?' link opens the Reset Password page",
            groups = {"regression", "login"})
    public void verifyForgotPasswordLinkOpensResetPasswordPage() {
        ForgotPasswordPage forgotPasswordPage = loginPage.clickForgotPassword();
        AssertUtils.verifyEquals(forgotPasswordPage.getPageHeading(), "Reset Password",
                "heading on the Reset Password page");
    }
}
