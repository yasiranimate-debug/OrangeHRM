package com.orangehrm.components;

import com.orangehrm.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class SidebarComponent extends BasePage {

    private final By menuItems = By.cssSelector(".oxd-main-menu-item");

    public SidebarComponent(WebDriver driver) {
        super(driver);
    }

    public List<String> getMenuItemNames() {
        return textsOf(findAll(menuItems, "main menu items"));
    }

    public void clickMenuItem(String itemName) {
        By item = By.xpath("//a[contains(@class,'oxd-main-menu-item')]//span[normalize-space()='" + itemName + "']");
        click(item, "'" + itemName + "' menu item");
    }
}
