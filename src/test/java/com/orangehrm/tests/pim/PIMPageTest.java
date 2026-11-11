package com.orangehrm.tests.pim;

import com.orangehrm.base.BaseTest;
import com.orangehrm.dataproviders.TestDataProvider;
import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.pages.PIMPage;
import com.orangehrm.utils.AssertUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PIMPageTest extends BaseTest {

    private PIMPage pimPage;

    @BeforeMethod(alwaysRun = true)
    public void setUpPage() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.open(getBaseUrl());
        DashboardPage dashboardPage = loginPage.loginAs(getValidUsername(), getValidPassword());
        dashboardPage.sidebar().clickMenuItem("PIM");
        pimPage = new PIMPage(getDriver());
    }

    @Test(description = "TC_PIM_001: PIM page opens under the PIM module",
            groups = {"smoke", "pim"})
    public void verifyPIMPageOpensUnderPIMModule() {
        AssertUtils.verifyEquals(pimPage.header().getBreadcrumbModule(), "PIM",
                "breadcrumb module on the Employee List page");
    }

    @Test(description = "TC_PIM_002: The 'Add' (employee) button is available on the Employee List page",
            groups = {"regression", "pim"})
    public void verifyAddEmployeeButtonIsDisplayed() {
        AssertUtils.verifyTrue(pimPage.isAddButtonDisplayed(), "'Add' button on the Employee List page");
    }

    @Test(dataProvider = "employeeSearch", dataProviderClass = TestDataProvider.class,
            description = "TC_PIM_003: Typing a letter into Employee Name shows autocomplete suggestions",
            groups = {"regression", "pim"})
    public void verifyEmployeeNameAutocompleteShowsSuggestions(String searchTerm) {
        int suggestionCount = pimPage.typeEmployeeNameAndGetSuggestionCount(searchTerm);
        AssertUtils.verifyTrue(suggestionCount > 0,
                "autocomplete suggestions are shown for '" + searchTerm + "'");
    }
}
