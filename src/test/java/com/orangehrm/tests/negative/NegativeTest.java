package com.orangehrm.tests.negative;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.pages.NotFoundPage;
import com.orangehrm.utils.AssertUtils;
import com.orangehrm.utils.HttpStatusChecker;
import org.testng.annotations.Test;

public class NegativeTest extends BaseTest {

    @Test(description = "TC_NEG_001: An invalid route returns HTTP 404 and never loads the app",
            groups = {"regression", "negative"})
    public void verifyInvalidRouteShowsErrorPage() {
        String invalidRouteUrl = getBaseUrl().replace("/auth/login", "/does-not-exist");

        AssertUtils.verifyEquals(HttpStatusChecker.getStatusCode(invalidRouteUrl), 404,
                "HTTP status code for an invalid route");

        NotFoundPage notFoundPage = new NotFoundPage(getDriver());
        notFoundPage.open(invalidRouteUrl);
        AssertUtils.verifyTrue(!notFoundPage.isAppLoaded(), "the OrangeHRM app did not load for an invalid route");
    }

    @Test(description = "TC_NEG_002: Opening a protected page as a guest redirects to the login page",
            groups = {"smoke", "negative"})
    public void verifyProtectedPageRedirectsGuestToLogin() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.open(getBaseUrl().replace("/auth/login", "/admin/viewSystemUsers"));
        AssertUtils.verifyContains(loginPage.getCurrentUrl(), "/auth/login",
                "URL after requesting a protected page as a guest");
        AssertUtils.verifyTrue(loginPage.isUsernameFieldDisplayed(), "login page is shown instead");
    }
}
