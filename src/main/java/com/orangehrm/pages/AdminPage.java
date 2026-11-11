package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import com.orangehrm.components.HeaderComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminPage extends BasePage {

    @FindBy(xpath = "//label[text()='Username']/ancestor::div[contains(@class,'oxd-input-group')][2]//input")
    private WebElement usernameSearchInput;

    @FindBy(xpath = "//button[normalize-space()='Search']")
    private WebElement searchButton;

    @FindBy(xpath = "//button[normalize-space()='Add']")
    private WebElement addButton;

    private final By resultRows = By.cssSelector(".oxd-table-card");

    private final HeaderComponent header;

    public AdminPage(WebDriver driver) {
        super(driver);
        this.header = new HeaderComponent(driver);
    }

    public HeaderComponent header() {
        return header;
    }

    public boolean isAddButtonDisplayed() {
        return isVisibleWithinTimeout(addButton, "'Add' button");
    }

    public void searchByUsername(String username) {
        type(usernameSearchInput, username, "username search field");
        click(searchButton, "'Search' button");
    }

    public int getResultRowCount() {
        return findAll(resultRows, "system user result rows").size();
    }

    public String getFirstResultRowText() {
        return getText(By.cssSelector(".oxd-table-card"), "first system user result row");
    }
}
