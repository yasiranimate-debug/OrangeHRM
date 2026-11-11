package com.orangehrm.components;

import com.orangehrm.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HeaderComponent extends BasePage {

    @FindBy(css = ".oxd-topbar-header-breadcrumb-module")
    private WebElement breadcrumbModule;

    @FindBy(css = ".oxd-topbar-header-breadcrumb h6")
    private WebElement breadcrumbTitle;

    @FindBy(css = ".oxd-userdropdown-tab")
    private WebElement userDropdownTab;

    private final By dropdownMenuItems = By.cssSelector("ul.oxd-dropdown-menu li");

    public HeaderComponent(WebDriver driver) {
        super(driver);
    }

    public String getBreadcrumbModule() {
        return getText(breadcrumbModule, "breadcrumb module name");
    }

    public String getPageTitle() {
        return getText(breadcrumbTitle, "page heading");
    }

    public void openUserDropdown() {
        click(userDropdownTab, "user profile dropdown");
    }

    public List<String> getUserDropdownOptions() {
        openUserDropdown();
        return textsOf(findAllVisible(dropdownMenuItems, "user dropdown menu items"));
    }

    public void logout() {
        openUserDropdown();
        By logoutOption = By.xpath("//ul[contains(@class,'oxd-dropdown-menu')]//a[normalize-space()='Logout']");
        click(logoutOption, "'Logout' option");
    }
}
