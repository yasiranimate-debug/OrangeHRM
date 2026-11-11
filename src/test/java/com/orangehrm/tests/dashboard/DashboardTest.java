package com.orangehrm.tests.dashboard;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utils.AssertUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Arrays;
import java.util.List;

public class DashboardTest extends BaseTest {

    private DashboardPage dashboardPage;

    @BeforeMethod(alwaysRun = true)
    public void setUpPage() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.open(getBaseUrl());
        dashboardPage = loginPage.loginAs(getValidUsername(), getValidPassword());
    }

    @Test(description = "TC_HOME_001: Dashboard page heading reads 'Dashboard' after login",
            groups = {"smoke", "dashboard"})
    public void verifyDashboardHeadingAfterLogin() {
        AssertUtils.verifyEquals(dashboardPage.header().getPageTitle(), "Dashboard", "Dashboard page heading");
    }

    @Test(description = "TC_HOME_002: Dashboard shows all expected widgets",
            groups = {"regression", "dashboard"})
    public void verifyDashboardWidgetsAreDisplayed() {
        List<String> expectedWidgets = Arrays.asList(
                "Time at Work", "My Actions", "Quick Launch", "Buzz Latest Posts",
                "Employees on Leave Today", "Employee Distribution by Sub Unit",
                "Employee Distribution by Location");

        List<String> actualWidgets = dashboardPage.getWidgetNames();
        SoftAssert softAssert = new SoftAssert();
        for (String widget : expectedWidgets) {
            AssertUtils.softVerifyTrue(softAssert, actualWidgets.contains(widget),
                    "'" + widget + "' widget is present on the Dashboard");
        }
        softAssert.assertAll();
    }

    @Test(description = "TC_HOME_003: User dropdown shows About, Support, Change Password and Logout",
            groups = {"regression", "dashboard"})
    public void verifyUserDropdownOptions() {
        List<String> options = dashboardPage.header().getUserDropdownOptions();
        AssertUtils.verifyEquals(options, Arrays.asList("About", "Support", "Change Password", "Logout"),
                "user dropdown menu options");
    }

    @Test(description = "TC_HOME_004: Logout redirects the user back to the login page",
            groups = {"smoke", "dashboard"})
    public void verifyLogoutRedirectsToLoginPage() {
        LoginPage loginPage = dashboardPage.logout();
        AssertUtils.verifyTrue(loginPage.isUsernameFieldDisplayed(), "login page username field after logout");
        AssertUtils.verifyContains(loginPage.getCurrentUrl(), "/auth/login", "URL after logout");
    }
}
