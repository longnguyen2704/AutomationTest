package src.AutomationTestApplication;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import src.driverForHiFPT.DriverFactoryForHiFPT;
import src.driverForHiFPT.Platform;
import src.driverForHiFPT.Report;

import java.io.FileInputStream;
import java.io.IOException;

public class HiFPTAutomationTestReadTCs {

    public static void main(String[] args) {
        AppiumDriver<MobileElement> appiumDriver = DriverFactoryForHiFPT.getDriver(Platform.ANDROID);
        WebDriverWait wait = new WebDriverWait(appiumDriver, 60L);

        try {
            System.out.println("Start running Auto Test");

            // Xử lý các bước kiểm thử cơ bản như đăng nhập
            try {
                MobileElement loginScreen =
                        (MobileElement) wait.until
                                (ExpectedConditions.visibilityOfElementLocated
                                        (MobileBy.xpath("//android.widget.EditText")));
                if (loginScreen.isDisplayed()) {
                    loginScreen.sendKeys("0775892638");

//                    MobileElement buttonContinue =
//                            (MobileElement) wait.until
//                                    (ExpectedConditions.visibilityOfElementLocated
//                                            (MobileBy.xpath("//android.widget.Button")));
//                    if (buttonContinue.isEnabled()) {
//                        buttonContinue.click();
//                    }
                }
            } catch (Exception e) {
                System.out.println("System error! Please try again");
            }

            try {
                MobileElement inputPIN =
                        (MobileElement) wait.until
                                (ExpectedConditions.visibilityOfElementLocated(MobileBy.xpath
                                        ("//android.widget.TextView[@text=\"Nhập mã PIN của bạn\"]")));
                if (inputPIN.isDisplayed()) {
                    MobileElement clickOutside =
                            (MobileElement) wait.until
                                    (ExpectedConditions.visibilityOfElementLocated
                                            (MobileBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout")));
                    clickOutside.click();
                    // Input PIN
                    String otp = "123456";
                    for (char digit : otp.toCharArray()) {
                        Runtime.getRuntime().exec("adb shell input text " + digit);
                        Thread.sleep(1000); // Chờ giữa các lần nhập
                    }
                }
            } catch (Exception e) {
                System.out.println("System error! Please try again");
            }

            try {
                MobileElement inputOTP =
                        (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated
                                (MobileBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout")));
                inputOTP.click();
                // Input PIN
                String pin = "1309";
                for (char digit : pin.toCharArray()) {
                    Runtime.getRuntime().exec("adb shell input text " + digit);
                    Thread.sleep(1000); // Chờ giữa các lần nhập
                }

            }
            catch (Exception e){
                System.out.println("System error! Please try again");
            }

//            try {
//                // Popup force update is showing
//                MobileElement popupForceUpdate = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("com.rad.hifpt:id/imgforceUpdate")));
//                if (popupForceUpdate.isDisplayed()) {
//                    MobileElement clickClose = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("com.rad.hifpt:id/ivClose")));
//                    clickClose.click();
//                }
//            } catch (Exception e) {
//                System.out.println("Not found element. Continuing with the next steps.");
//            }
//            System.out.println("Continue...");
//
//            try {
//                //Popup noti
//                MobileElement popupNoti = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.xpath("//android.widget.TextView[@text=\"Cho phép Hi FPT gửi thông báo để bạn không bỏ lỡ các nhắc nhở quan trọng và thông tin hóa đơn\"]")));
//                if (popupNoti.isDisplayed()) {
//                    MobileElement clickYes = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.xpath("//android.widget.TextView[@text=\"Bật thông báo\"]")));
//                    clickYes.click();
//                }
//            } catch (Exception e) {
//                System.out.println("Not found element. Continuing with the next steps.");
//            }
//            System.out.println("Continue...");
        } catch (Exception e) {
            System.err.println("Error!!!!" + e.getMessage());
            appiumDriver.quit();
        }
//        try {
//            // Đọc file Excel
//            String excelFilePath = "D:\\TestCaseForActiveAP.xlsx";
//            readTestDataFromExcel(excelFilePath, appiumDriver);
//            // Xuất report
//            String reportFilePath = "D:\\Test Report.xlsx";
//            Report.exportReport(excelFilePath, reportFilePath);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            System.err.println("System error. Please try again: " + e.getMessage());
//        }
    }

    // Hàm đọc dữ liệu từ file Excel
    private static void readTestDataFromExcel(String excelFilePath, AppiumDriver<MobileElement> appiumDriver) throws InterruptedException {
        try (FileInputStream fis = new FileInputStream(excelFilePath)) {
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0); // Đọc sheet đầu tiên

            boolean allTestsPassed = true; // Biến kiểm tra xem tất cả các test case có thành công hay không

            // Duyệt qua từng dòng trong sheet
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String testCaseName = row.getCell(0).getStringCellValue();
                String action = row.getCell(1).getStringCellValue();
                String ID = row.getCell(2).getStringCellValue();
                String coordinates = row.getCell(3) != null ? row.getCell(3).getStringCellValue() : "";
                String inputData = row.getCell(4) != null ? row.getCell(4).getStringCellValue() : "";

                System.out.println("Executing: " + testCaseName);
                boolean result = performAction(appiumDriver, action, ID, inputData, coordinates);

                // Kiểm tra nếu hành động thất bại thì đánh dấu allTestsPassed là false
                if (!result) {
                    allTestsPassed = false;
                    System.out.println("Test case failed: " + testCaseName);
                }
            }

            if (allTestsPassed) {
                System.out.println("All test cases passed");
            } else {
                System.out.println("Some test cases failed");
            }
        } catch (IOException e) {
            // Nếu có lỗi IO, dừng toàn bộ quá trình và thông báo lỗi
            System.err.println("Error reading Excel file: " + e.getMessage());
            throw new RuntimeException("Failed to read test data from Excel.", e); // Ném ngoại lệ để dừng quá trình
        } catch (Exception e) {
            // Bắt các ngoại lệ khác để xử lý trường hợp ngoài dự kiến
            System.err.println("An unexpected error occurred: " + e.getMessage());
            throw new RuntimeException("An unexpected error occurred.", e); // Ném ngoại lệ
        } finally {
            // Đảm bảo driver được dừng
            appiumDriver.quit();
        }
    }


    // Hàm thực hiện các bước kiểm thử
    public static boolean performAction(AppiumDriver<MobileElement> appiumDriver, String action, String ID, String inputData, String coordinates) {
        String selector = action.toLowerCase();
        WebDriverWait wait = new WebDriverWait(appiumDriver, 60L);
        try {
            switch (selector) {
                case "click":
                    if (ID == null || ID.trim().isEmpty()) {
                        System.out.println("Invalid ID: ID is empty or null");
                        return false;
                    }
                    if (ID.startsWith("com")) {
                        wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(ID)));
                        appiumDriver.findElement(MobileBy.id(ID)).click();
                    } else if (ID.startsWith("//")) {
                        wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.xpath(ID)));
                        appiumDriver.findElement(MobileBy.xpath(ID)).click();
                    } else if (ID.startsWith("android.widget")) {
                        wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.className(ID)));
                        appiumDriver.findElement(MobileBy.className(ID)).click();
                    }
                    break;
                case "tap":
                    if (coordinates == null || coordinates.trim().isEmpty()) {
                        System.out.println("Invalid Coordinates: Coordinates are empty or null");
                        return false;
                    }
                    String[] parts = coordinates.split(",");
                    int x = Integer.parseInt(parts[0].trim());
                    int y = Integer.parseInt(parts[1].trim());
                    new TouchAction<>(appiumDriver).tap(PointOption.point(x, y)).perform();
                    break;
                case "value":
                    if (inputData == null || inputData.trim().isEmpty()) {
                        System.out.println("Invalid Input Data: inputData is empty or null");
                        return false;
                    }
                    if (ID == null || ID.trim().isEmpty()) {
                        System.out.println("Invalid ID: ID is empty or null");
                        return false;
                    }
                    MobileElement inputField;
                    if (ID.startsWith("com")) {
                        inputField = appiumDriver.findElement(MobileBy.id(ID));
                    } else if (ID.startsWith("//")) {
                        inputField = appiumDriver.findElement(MobileBy.xpath(ID));
                    } else {
                        inputField = appiumDriver.findElement(MobileBy.className(ID));
                    }
                    inputField.sendKeys(inputData);
                    break;
                default:
                    throw new UnsupportedOperationException("Please input action");
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return true;
    }
}