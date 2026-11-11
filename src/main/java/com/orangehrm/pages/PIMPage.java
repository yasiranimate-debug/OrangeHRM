package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import com.orangehrm.components.HeaderComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PIMPage extends BasePage {

    @FindBy(xpath = "//label[text()='Employee Name']/ancestor::div[contains(@class,'oxd-input-group')][2]//input")
    private WebElement employeeNameInput;

    @FindBy(xpath = "//button[normalize-space()='Add']")
    private WebElement addButton;

    private final By autocompleteOptions = By.cssSelector(".oxd-autocomplete-dropdown .oxd-autocomplete-option");

    private final HeaderComponent header;

    public PIMPage(WebDriver driver) {
        super(driver);
        this.header = new HeaderComponent(driver);
    }

    public HeaderComponent header() {
        return header;
    }

    public boolean isAddButtonDisplayed() {
        return isVisibleWithinTimeout(addButton, "'Add' button");
    }

    public int typeEmployeeNameAndGetSuggestionCount(String partialName) {
        type(employeeNameInput, partialName, "Employee Name search field");
        wait.until(ExpectedConditions.presenceOfElementLocated(autocompleteOptions));
        return findAll(autocompleteOptions, "Employee Name autocomplete suggestions").size();
    }
}
