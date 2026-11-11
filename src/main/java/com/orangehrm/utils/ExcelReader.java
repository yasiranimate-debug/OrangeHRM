package com.orangehrm.utils;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {

    private static final String EXCEL_PATH = "src/test/resources/testdata/TestData.xlsx";

    public static Object[][] getSheetData(String sheetName) {
        try (FileInputStream fis = new FileInputStream(EXCEL_PATH);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet not found: " + sheetName);
            }

            int rowCount = sheet.getPhysicalNumberOfRows();
            int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

            Object[][] data = new Object[rowCount - 1][colCount];
            DataFormatter formatter = new DataFormatter();

            for (int i = 1; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    data[i - 1][j] = formatter.formatCellValue(cell);
                }
            }
            return data;
        } catch (IOException e) {
            throw new RuntimeException("Unable to read Excel sheet: " + sheetName, e);
        }
    }
}
