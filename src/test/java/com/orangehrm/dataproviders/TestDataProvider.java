package com.orangehrm.dataproviders;

import com.orangehrm.utils.ExcelReader;
import org.testng.annotations.DataProvider;

public class TestDataProvider {

    @DataProvider(name = "invalidLogins")
    public Object[][] invalidLogins() {
        return ExcelReader.getSheetData("InvalidLogins");
    }

    @DataProvider(name = "mainMenuItems")
    public Object[][] mainMenuItems() {
        return ExcelReader.getSheetData("MainMenuItems");
    }

    @DataProvider(name = "moduleNavigation")
    public Object[][] moduleNavigation() {
        return ExcelReader.getSheetData("ModuleNavigation");
    }

    @DataProvider(name = "employeeSearch")
    public Object[][] employeeSearch() {
        return ExcelReader.getSheetData("EmployeeSearch");
    }
}
