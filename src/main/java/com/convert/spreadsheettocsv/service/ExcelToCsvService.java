package com.convert.spreadsheettocsv.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Iterator;

@Service
public class ExcelToCsvService {
    public void convertXlsxToCsv(File xlsxFile, File csvFile) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(xlsxFile));
             PrintWriter writer = new PrintWriter(new FileWriter(csvFile))) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                StringBuilder csvLine = new StringBuilder();

                for (Cell cell : row) {
                    String cellValue = getCellValueAsString(cell);
                    csvLine.append(cellValue).append(";");
                }

                // Remove the last ";" and add a newline
                if (csvLine.length() > 0) {
                    csvLine.setLength(csvLine.length() - 1);
                }
                writer.println(csvLine.toString());
            }
        }
    }

    private String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    // Check if the cell has a format applied that is not just a plain number
                    if (cell.getCellStyle().getDataFormatString().contains("0")) {
                        // Use DataFormatter to retain the format
                        DataFormatter dataFormatter = new DataFormatter();
                        return dataFormatter.formatCellValue(cell);
                    } else {
                        return Double.toString(cell.getNumericCellValue());
                    }
                }
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }
}
