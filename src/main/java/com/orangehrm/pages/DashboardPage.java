package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import com.orangehrm.components.HeaderComponent;
import com.orangehrm.components.SidebarComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class DashboardPage extends BasePage {

    private final By widgetNames = By.cssSelector(".orangehrm-dashboard-widget-name");

    private final HeaderComponent header;
    private final SidebarComponent sidebar;

    public DashboardPage(WebDriver driver) {
        super(driver);
        this.header = new HeaderComponent(driver);
        this.sidebar = new SidebarComponent(driver);
    }

    public HeaderComponent header() {
        return header;
    }

    public SidebarComponent sidebar() {
        return sidebar;
    }

    public List<String> getWidgetNames() {
        return textsOf(findAll(widgetNames, "dashboard widgets"));
    }

    public LoginPage logout() {
        header.logout();
        return new LoginPage(driver);
    }
}
