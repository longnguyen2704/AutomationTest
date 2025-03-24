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

            // Xử lý Đăng nhập luồng OTP / PIN
            try {
                MobileElement loginScreenOTP =
                        (MobileElement) wait.until
                                (ExpectedConditions.visibilityOfElementLocated
                                        (MobileBy.xpath("//android.widget.EditText")));
                if (loginScreenOTP.isDisplayed()) {
                    loginScreenOTP.sendKeys("0908418782");
                }
            } catch (Exception e) {
                System.out.println("System error! Please try again");
            }

            try {
                MobileElement inputPIN =
                        (MobileElement) wait.until
                                (ExpectedConditions.visibilityOfElementLocated(MobileBy.xpath
                                        ("//android.widget.TextView[@text=\"Nhập mã PIN\"]")));
                    inputPIN.click();
                    // Input PIN
                    String otp = "123456";
                    for (char digit : otp.toCharArray()) {
                        Runtime.getRuntime().exec("adb shell input text " + digit);
                        Thread.sleep(1000); // Chờ giữa các lần nhập
                    }
            } catch (Exception e) {
                System.out.println("System error! Please try again");
            }

//            try {
//                // Chờ đến khi ô nhập OTP hiển thị
//                MobileElement inputOTP =
//                        (MobileElement) wait.until(
//                        ExpectedConditions.elementToBeClickable(MobileBy.id("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout"))); // Thay thế bằng ID thực tế
//
//                inputOTP.click();
//                Thread.sleep(1000); // Chờ 1 giây trước khi nhập PIN
//
//                if (inputOTP.isDisplayed()) {
//                    // Nhập mã PIN trực tiếp
//                    inputOTP.sendKeys("1309");
//                }
//            } catch (Exception e) {
//                System.out.println("System error! Please try again. Error: " + e.getMessage());
//            }

            try {
                MobileElement PopUpNoti =
                        (MobileElement) wait.until
                                (ExpectedConditions.visibilityOfElementLocated(MobileBy.xpath
                                        ("//android.widget.TextView[@text=\"Bật  thông  báo\"]")));
                if (PopUpNoti.isDisplayed()){
                    MobileElement clickNo =
                            (MobileElement) wait.until
                                    (ExpectedConditions.visibilityOfElementLocated
                                            (MobileBy.xpath("//android.widget.TextView[@text=\"Để sau\"]")));
                    clickNo.click();
                }
            }
            catch ( Exception e){
                System.out.println("System error! Please try again");
            }
        } catch (Exception e) {
            System.err.println("Error!!!!" + e.getMessage());
            appiumDriver.quit();
        }

        try {
            // Đọc file Excel
            String excelFilePath = "C:\\FPT\\Tele\\TestCaseForAppium.xlsx";
            readTestDataFromExcel(excelFilePath, appiumDriver);
            // Xuất report
            String reportFilePath = "C:\\FPT\\Tele\\Test Report.xlsx";
            Report.exportReport(excelFilePath, reportFilePath);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.err.println("System error. Please try again: " + e.getMessage());
        }
    }

    // Hàm đọc dữ liệu từ file Excel
    private static void readTestDataFromExcel(String excelFilePath, AppiumDriver<MobileElement> appiumDriver) throws InterruptedException {
        try (FileInputStream fis = new FileInputStream(excelFilePath)) {
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0); // Đọc sheet đầu tiên

            boolean allTestsPassed = true; // Biến kiểm tra xem tất cả các test case có thành công hay không
            int totalCases = sheet.getLastRowNum(); // Số lượng test cases

            for (int i = 1; i <= totalCases; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                // Kiểm tra từng ô trước khi đọc giá trị để tránh NullPointerException
                String testCaseName = (row.getCell(0) != null) ? row.getCell(0).getStringCellValue() : "N/A";
                String action = (row.getCell(1) != null) ? row.getCell(1).getStringCellValue() : "N/A";
                String ID = (row.getCell(2) != null) ? row.getCell(2).getStringCellValue() : "N/A";
                String coordinates = (row.getCell(3) != null) ? row.getCell(3).getStringCellValue() : "";
                String inputData = (row.getCell(4) != null) ? row.getCell(4).getStringCellValue() : "";

                System.out.println("Running case: " + testCaseName);
                boolean result = performAction(appiumDriver, action, ID, inputData, coordinates);

                if (!result) {
                    allTestsPassed = false;
                    System.out.println("Test case failed: " + testCaseName);
                }
                if (i == totalCases) {
                    if (allTestsPassed) {
                        System.out.println("PASS - All test cases passed");
                    } else {
                        System.out.println("FAIL - Some test cases failed");
                    }
                }
                // Thời gian chờ giữa các test case
                Thread.sleep(1500);
            }
        } catch (IOException e) {
            System.err.println("Error reading Excel file: " + e.getMessage());
            throw new RuntimeException("Failed to read test data from Excel.", e);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            throw new RuntimeException("An unexpected error occurred.", e);
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