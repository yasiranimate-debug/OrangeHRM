package com.orangehrm.tests.navigation;

import com.orangehrm.base.BaseTest;
import com.orangehrm.dataproviders.TestDataProvider;
import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utils.AssertUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class NavigationTest extends BaseTest {

    private DashboardPage dashboardPage;

    @BeforeMethod(alwaysRun = true)
    public void setUpPage() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.open(getBaseUrl());
        dashboardPage = loginPage.loginAs(getValidUsername(), getValidPassword());
    }

    @Test(description = "TC_NAV_001: The main menu contains exactly 12 items",
            groups = {"smoke", "navigation"})
    public void verifyMainMenuItemCount() {
        AssertUtils.verifyEquals(dashboardPage.sidebar().getMenuItemNames().size(), 12,
                "number of main menu items");
    }

    @Test(dataProvider = "mainMenuItems", dataProviderClass = TestDataProvider.class,
            description = "TC_NAV_002: Each expected item is present in the main menu",
            groups = {"regression", "navigation"})
    public void verifyMainMenuItemIsPresent(String menuItem) {
        AssertUtils.verifyTrue(dashboardPage.sidebar().getMenuItemNames().contains(menuItem),
                "'" + menuItem + "' is present in the main menu");
    }

    @Test(dataProvider = "moduleNavigation", dataProviderClass = TestDataProvider.class,
            description = "TC_NAV_003: Clicking a main menu item opens the expected module",
            groups = {"regression", "navigation"})
    public void verifyMenuItemOpensExpectedModule(String menuItem, String expectedModule) {
        dashboardPage.sidebar().clickMenuItem(menuItem);
        AssertUtils.verifyEquals(dashboardPage.header().getBreadcrumbModule(), expectedModule,
                "breadcrumb module after clicking '" + menuItem + "'");
    }
}
