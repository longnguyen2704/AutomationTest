package src.driverForHiFPT;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import static src.AutomationTestApplication.HiFPTAutomationTestReadTCs.performAction;


public interface Report {

    static void exportReport(String excelFilePath, String reportFilePath) {
        AppiumDriver<MobileElement> appiumDriver = DriverFactoryForHiFPT.getDriver(Platform.ANDROID);

        try (FileInputStream fileInputStream = new FileInputStream(excelFilePath); Workbook workbook = new XSSFWorkbook(fileInputStream); Workbook reportWorkbook = new XSSFWorkbook(); FileOutputStream fileOut = new FileOutputStream(reportFilePath)) {

            Sheet sheet = workbook.getSheetAt(0);  // Đọc sheet đầu tiên trong file testcase Excel
            Sheet reportSheet = reportWorkbook.createSheet("Test Report");

            // Tạo tiêu đề cho file báo cáo
            Row headerRow = reportSheet.createRow(0);
            String[] columnHeaders = {"Test Case", "Action", "ID", "Input Data", "Coordinates", "Result"};
            for (int i = 0; i < columnHeaders.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnHeaders[i]);

                // Định dạng style cho header
                CellStyle headerStyle = reportWorkbook.createCellStyle();
                Font font = reportWorkbook.createFont();
                font.setBold(true);
                headerStyle.setFont(font);
                cell.setCellStyle(headerStyle);
            }

            // Sau khi thêm tất cả dữ liệu vào bảng, tự động scale các cột
            for (int i = 0; i < columnHeaders.length; i++) {
                reportSheet.autoSizeColumn(i);
            }

            int rowNum = 1;
            Iterator<Row> rowIterator = sheet.iterator();

            // Bỏ qua dòng tiêu đề
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            // Duyệt qua từng dòng trong file Excel
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                // Đọc các giá trị từ dòng
                String testCaseName = row.getCell(0).getStringCellValue();
                String action = row.getCell(1).getStringCellValue();
                String ID = row.getCell(2).getStringCellValue();
                String coordinates = row.getCell(3) != null ? row.getCell(3).getStringCellValue() : "";
                String inputData = row.getCell(4) != null ? row.getCell(4).getStringCellValue() : "";

                String result = "Success";  // Mặc định là thành công

                try {
                    // Thực thi hành động dựa trên dữ liệu từ Excel
                    boolean stepSuccess = performAction(appiumDriver, action, ID, inputData, coordinates);
                    if (!stepSuccess) {
                        result = "Fail";
                    }
                } catch (Exception e) {
                    result = "Fail";  // Đánh dấu thất bại nếu có lỗi
                }

                // Ghi kết quả vào file báo cáo Excel
                Row reportRow = reportSheet.createRow(rowNum++);
                reportRow.createCell(0).setCellValue(testCaseName);
                reportRow.createCell(1).setCellValue(action);
                reportRow.createCell(2).setCellValue(ID);
                reportRow.createCell(3).setCellValue(inputData);
                reportRow.createCell(4).setCellValue(coordinates);
                reportRow.createCell(5).setCellValue(result);
            }

            // Ghi file báo cáo ra đĩa
            reportWorkbook.write(fileOut);
            System.out.println("Report exported successfully to " + reportFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
