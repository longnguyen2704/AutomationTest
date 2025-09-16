package src.driverForHiFPT;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.yaml.snakeyaml.Yaml;
import java.io.*;
import java.util.*;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Report {
    public static void exportReportFromYamlReport(String yamlFilePath, String reportFilePath) {
        try (InputStream inputStream = new FileInputStream(yamlFilePath);
             Workbook reportWorkbook = new XSSFWorkbook()) {

            // Parse YAML
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);
            List<Map<String, String>> testCases = (List<Map<String, String>>) data.get("testcases");

            // Tạo sheet cho báo cáo
            Sheet reportSheet = reportWorkbook.createSheet("Test Report");

            // Tạo header
            String[] columns = {"Test Case", "Action", "ID", "Input Data", "Result"};
            Row headerRow = reportSheet.createRow(0);
            CellStyle headerStyle = reportWorkbook.createCellStyle();
            Font headerFont = reportWorkbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            // Ghi test cases từ YAML vào báo cáo
            int rowNum = 1;
            for (Map<String, String> tc : testCases) {
                String testCaseName = tc.get("name");
                String action = tc.get("action");
                String elementID = tc.get("id");
                String inputData = tc.get("input");

                // Kiểm tra result
                String result = null;
                if (testCaseName.toLowerCase().contains("false")) {
                    result = "False";
                } else if (testCaseName.toLowerCase().contains("true")) {
                    result = "Passed";
                }

                Row reportRow = reportSheet.createRow(rowNum++);
                reportRow.createCell(0).setCellValue(testCaseName);
                reportRow.createCell(1).setCellValue(action);
                reportRow.createCell(2).setCellValue(elementID);
                reportRow.createCell(3).setCellValue(inputData);
                reportRow.createCell(4).setCellValue(result);
            }

            // Auto size column
            for (int i = 0; i < columns.length; i++) {
                reportSheet.autoSizeColumn(i);
            }
            // Lưu file báo cáo
            try (FileOutputStream fileOut = new FileOutputStream(reportFilePath)) {
                reportWorkbook.write(fileOut);
                System.out.println("✅ Report exported successfully to: " + reportFilePath);
            }

        } catch (IOException e) {
            System.err.println("❌ Error exporting report: " + e.getMessage());
        }
    }
}