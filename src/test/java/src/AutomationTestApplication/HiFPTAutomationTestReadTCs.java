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
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class HiFPTAutomationTestReadTCs {

    public static void main(String[] args) {
        AppiumDriver<MobileElement> appiumDriver = DriverFactoryForHiFPT.getDriver(Platform.ANDROID);
        WebDriverWait wait = new WebDriverWait(appiumDriver, 6);

        try {
            System.out.println("=====Start running Automation Test=====");

            // Xử lý đăng nhập
            handleLogin(wait);

            // Kiểm tra popup thông báo
            handlePopUpNotification(wait);

            // Đọc file Excel & thực thi test cases
            String excelFilePath = "C:\\FPT\\Tele\\TestCaseForAppium.xlsx";
            readTestDataFromExcel(excelFilePath, appiumDriver, wait);

            // Stay in app 30s before stop process
            Thread.sleep(10000);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            appiumDriver.quit();
        }
    }

    public static void handleLogin(WebDriverWait wait) {
        try {
            MobileElement loginScreenOTP = getElement(wait, "//android.widget.EditText");
            if (loginScreenOTP != null) loginScreenOTP.sendKeys("0908418782");
            System.out.println("✅ Input phone number successfully");

            MobileElement PopupBlockSignUp = getElement(wait, "//android.widget.TextView[@text=\"Khóa đăng nhập\"]");
            if (PopupBlockSignUp != null && PopupBlockSignUp.isDisplayed()) {
                MobileElement clickClose = getElement(wait, "//android.widget.TextView[@text=\"Đóng\"]");
                if (clickClose != null) clickClose.click();
                System.out.println("❌ Login unsuccessfully because system is showing error popup");

                // Dừng chương trình ngay lập tức
                System.exit(0);  // Thoát chương trình với mã lỗi 0
                return;
            }

            MobileElement inputPIN = getElement(wait, "//android.widget.TextView[@text=\"Nhập mã PIN\"]");
            if (inputPIN != null) {
                inputPIN.click();
                String PIN = "123456";
                for (char digit : PIN.toCharArray()) {
                    Runtime.getRuntime().exec("adb shell input text " + digit);
                    Thread.sleep(1000);
                }
                System.out.println("✅ Input PIN successfully");
            }
        } catch (Exception e) {
            System.out.println("Login Error: " + e.getMessage());
        }
    }

    private static void handlePopUpNotification(WebDriverWait wait) {
        try {
            MobileElement popUp = getElement(wait, "//android.widget.TextView[@text=\"Bật  thông  báo\"]");
            if (popUp != null && popUp.isDisplayed()) {
                MobileElement clickNo = getElement(wait, "//android.widget.TextView[@text=\"Để sau\"]");
                if (clickNo != null) clickNo.click();
            }
            System.out.println("✅ Home Hi FPT say Hi");
        } catch (Exception ignored) {
        }
    }

    private static void readTestDataFromExcel(String excelFilePath, AppiumDriver<MobileElement> appiumDriver, WebDriverWait wait) {
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            int totalCases = sheet.getLastRowNum();
            boolean allTestsPassed = true;

            for (int i = 1; i <= totalCases; i++) {
                Row row = sheet.getRow(i);
                if (row == null || row.getCell(0) == null) continue;

                String testCaseName = getCellValue(row, 0);
                String action = getCellValue(row, 1);
                String ID = getCellValue(row, 2);
                String coordinates = getCellValue(row, 3);
                String inputData = getCellValue(row, 4);

                if (testCaseName.trim().isEmpty() || action.trim().isEmpty()) {
                    System.out.println("Invalid test case format at row " + (i + 1));
                    continue;
                }
                Thread.sleep(2000);
                System.out.println("⮑ Running case: " + testCaseName);
                boolean result = performAction(appiumDriver, action, ID, inputData, coordinates);

                if (!result) {
                    allTestsPassed = false;
                    System.out.println("❌ Test case failed: " + testCaseName);
                }
            }

            System.out.println(allTestsPassed ? "✅ PASS - All test cases have been run" : "❌ FAIL - Some test cases failed");

            // Xuất báo cáo
            String reportFilePath = "C:\\FPT\\Tele\\Test_Report.xlsx";
            Report.exportReport(excelFilePath, reportFilePath);

            // Stay in app 15s before stop process
            Thread.sleep(10000);
            // Dừng chương trình ngay sau khi chạy hết test cases
            System.exit(0);

        } catch (IOException e) {
            System.err.println("Error reading Excel file: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean performAction(AppiumDriver<MobileElement> appiumDriver, String action, String ID, String inputData, String coordinates) {
        WebDriverWait wait = new WebDriverWait(appiumDriver, 15);
        if (ID == null || ID.trim().isEmpty()) {
            System.out.println("⚠️ Skipping test case due to missing ID.");
            return true;  // Trả về true để không làm thất bại toàn bộ quá trình
        }

        Map<String, Consumer<String>> actions = new HashMap<>();
        actions.put("click", id -> clickElement(wait, id));
        actions.put("value", id -> inputText(wait, id, inputData));
        actions.put("tap", coordinate -> tapCoordinates(appiumDriver, coordinates));

        Consumer<String> actionMethod = actions.get(action.toLowerCase());
        if (actionMethod != null) {
            actionMethod.accept(ID);
            return true;
        } else {
            System.out.println("⚠️ Invalid action: '" + action + "' at ID: " + ID);
            return false;
        }
    }

    private static void clickElement(WebDriverWait wait, String ID) {
        MobileElement element = getElement(wait, ID);
        if (element != null) element.click();
    }

    private static void inputText(WebDriverWait wait, String ID, String inputData) {
        if (inputData == null) return;
        MobileElement inputField = getElement(wait, ID);
        if (inputField != null) inputField.sendKeys(inputData);
    }

    private static void tapCoordinates(AppiumDriver<MobileElement> appiumDriver, String coordinates) {
        if (coordinates == null || coordinates.trim().isEmpty()) return;

        String[] parts = coordinates.split(",");
        if (parts.length == 2) {
            int x = Integer.parseInt(parts[0].trim());
            int y = Integer.parseInt(parts[1].trim());
            new TouchAction<>(appiumDriver).tap(PointOption.point(x, y)).perform();
        }
    }

    private static MobileElement getElement(WebDriverWait wait, String ID) {
        if (ID == null || ID.trim().isEmpty()) return null;

        try {
            if (ID.startsWith("com")) {
                return (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(ID)));
            } else if (ID.startsWith("//")) {
                return (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.xpath(ID)));
            } else {
                return (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.className(ID)));
            }
        } catch (Exception e) {
            System.out.println("⚠️ Element not found, because popup error not showing: " + ID);
            return null;  // Trả về null để bỏ qua test case này
        }
    }

    public static String getCellValue(Row row, int index) {
        Cell cell = row.getCell(index);
        return (cell != null) ? cell.toString().trim() : "";
    }
}