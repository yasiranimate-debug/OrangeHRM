package com.orangehrm.tests.negative;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.pages.NotFoundPage;
import com.orangehrm.utils.AssertUtils;
import org.testng.annotations.Test;

public class NegativeTest extends BaseTest {

    @Test(description = "TC_NEG_001: An invalid route shows the browser's 'page can't be found' error",
            groups = {"regression", "negative"})
    public void verifyInvalidRouteShowsErrorPage() {
        NotFoundPage notFoundPage = new NotFoundPage(getDriver());
        notFoundPage.open(getBaseUrl().replace("/auth/login", "/does-not-exist"));
        AssertUtils.verifyTrue(notFoundPage.isErrorPageDisplayed(), "error page is shown for an invalid route");
        AssertUtils.verifyContains(notFoundPage.getErrorHeadingText(), "can’t be found",
                "error heading text");
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
