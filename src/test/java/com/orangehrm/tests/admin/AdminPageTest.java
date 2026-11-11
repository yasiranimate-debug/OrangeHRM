package com.orangehrm.tests.admin;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.AdminPage;
import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utils.AssertUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AdminPageTest extends BaseTest {

    private AdminPage adminPage;

    @BeforeMethod(alwaysRun = true)
    public void setUpPage() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.open(getBaseUrl());
        DashboardPage dashboardPage = loginPage.loginAs(getValidUsername(), getValidPassword());
        dashboardPage.sidebar().clickMenuItem("Admin");
        adminPage = new AdminPage(getDriver());
    }

    @Test(description = "TC_ADM_001: Admin page opens under the Admin module",
            groups = {"smoke", "admin"})
    public void verifyAdminPageOpensUnderAdminModule() {
        AssertUtils.verifyEquals(adminPage.header().getBreadcrumbModule(), "Admin",
                "breadcrumb module on the Admin page");
    }

    @Test(description = "TC_ADM_002: Searching System Users by username 'Admin' returns a matching row",
            groups = {"regression", "admin"})
    public void verifySearchByUsernameReturnsMatchingUser() {
        adminPage.searchByUsername("Admin");
        AssertUtils.verifyEquals(adminPage.getResultRowCount(), 1, "number of rows returned for username 'Admin'");
        AssertUtils.verifyContains(adminPage.getFirstResultRowText(), "Admin", "first result row text");
    }

    @Test(description = "TC_ADM_003: The 'Add' button is available on the System Users page",
            groups = {"regression", "admin"})
    public void verifyAddButtonIsDisplayed() {
        AssertUtils.verifyTrue(adminPage.isAddButtonDisplayed(), "'Add' button on the System Users page");
    }
}
