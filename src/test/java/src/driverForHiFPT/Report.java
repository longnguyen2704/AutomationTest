package src.driverForHiFPT;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Report {
    public static void exportReport(String excelFilePath, String reportFilePath) {
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook testCaseWorkbook = new XSSFWorkbook(fis);
             Workbook reportWorkbook = new XSSFWorkbook()) {

            Sheet testCaseSheet = testCaseWorkbook.getSheetAt(0); // Lấy sheet đầu tiên
            Sheet reportSheet = reportWorkbook.createSheet("Test Report");

            // Tạo tiêu đề cho file báo cáo
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

            // Duyệt từng dòng trong file test case
            int rowNum = 1;
            for (int i = 1; i <= testCaseSheet.getLastRowNum(); i++) {
                Row testCaseRow = testCaseSheet.getRow(i);
                if (testCaseRow == null || testCaseRow.getCell(0) == null) continue;

                // Lấy dữ liệu test case
                String testCaseName = getCellValue(testCaseRow, 0);
                String action = getCellValue(testCaseRow, 1);
                String elementID = getCellValue(testCaseRow, 2);
                String inputData = getCellValue(testCaseRow, 4);

                // Giả lập kết quả (Bạn có thể thay bằng kết quả thực tế)
                String result = "Passed"; // Giả lập pass, có thể cập nhật từ thực tế

                // Ghi dữ liệu vào báo cáo
                Row reportRow = reportSheet.createRow(rowNum++);
                reportRow.createCell(0).setCellValue(testCaseName);
                reportRow.createCell(1).setCellValue(action);
                reportRow.createCell(2).setCellValue(elementID);
                reportRow.createCell(3).setCellValue(inputData);
                reportRow.createCell(4).setCellValue(result);
            }

            // Tự động căn chỉnh độ rộng cột
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

    private static String getCellValue(Row row, int index) {
        Cell cell = row.getCell(index);
        return (cell != null) ? cell.toString().trim() : "";
    }
}