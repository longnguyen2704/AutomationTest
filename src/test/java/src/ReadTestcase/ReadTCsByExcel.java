package src.ReadTestcase;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ReadTCsByExcel {
    public static void main(String[] args) {
        // Đường dẫn đến tệp Excel cần đọc
        String filePath = "path/to/your/file.xlsx";

        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            // Mở workbook từ FileInputStream
            Workbook workbook = new XSSFWorkbook(fileInputStream);

            // Lấy sheet đầu tiên từ workbook
            Sheet sheet = workbook.getSheetAt(0);

            // Lặp qua từng dòng trong sheet
            for (Row row : sheet) {
                // Lặp qua từng ô trong dòng
                for (Cell cell : row) {
                    // In ra giá trị của ô
                    System.out.print(cell.toString() + "\t");
                }
                System.out.println(); // Xuống dòng sau mỗi dòng
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
