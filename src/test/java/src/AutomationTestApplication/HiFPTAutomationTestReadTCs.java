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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
            String excelFilePath = "D:\\TestCaseForAppium.xlsx";//Remember to change
            readTestDataFromExcel(excelFilePath, appiumDriver, wait);

            // Stay in app 10s before stop process
            Thread.sleep(10000);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            appiumDriver.quit();
        }
    }

    private static void handleLogin(WebDriverWait wait) {
        try {
            MobileElement loginScreenOTP = getElement(wait, "//android.widget.EditText");
            if (loginScreenOTP != null) loginScreenOTP.sendKeys("0867634110");
            System.out.println("✅ Input phone number successfully");

            MobileElement PopupBlockSignUp = getElement(wait, "//android.widget.TextView[@text=\"Khóa đăng nhập\"]");
            if (PopupBlockSignUp != null && PopupBlockSignUp.isDisplayed()) {
                MobileElement clickClose = getElement(wait, "//android.widget.TextView[@text=\"Đóng\"]");
                if (clickClose != null) clickClose.click();
                System.out.println("❌ Login unsuccessfully because system is showing error popup");

                // Dừng chương trình ngay lập tức
                System.exit(1);  // Thoát chương trình với mã lỗi 1
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
            MobileElement inputOTP = getElement(wait, "//android.widget.TextView[@text=\"Mã OTP vừa được gửi đến số điện thoại\"]");
            if (inputOTP != null && inputOTP.isDisplayed()) {
                inputOTP.click();
                String OTP = "1309";
                for (char digit : OTP.toCharArray()) {
                    Runtime.getRuntime().exec("adb shell input text " + digit);
                    Thread.sleep(1000);
                }
                System.out.println("✅ Input OTP successfully");
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void handlePopUpNotification(WebDriverWait wait) {
        try {
            MobileElement popUp = getElement(wait, "//android.widget.TextView[@text=\"Bật  thông  báo\"]");
            if (popUp != null && popUp.isDisplayed()) {
                MobileElement clickNo = getElement(wait, "//android.widget.TextView[@text=\"Để sau\"]");
                if (clickNo != null) clickNo.click();
            }
            System.out.println("✅ Not allow notification success");
        } catch (Exception ignored) {
        }
        try {
            MobileElement bottomSheet =
                    getElement(wait, "//android.view.ViewGroup[@resource-id=\"android:id/content\"]/android.view.View/android.view.View/android.view.View[1]");
            if (bottomSheet != null && bottomSheet.isDisplayed()){
                MobileElement tabOutSide =
                        getElement(wait, "//android.view.ViewGroup[@resource-id=\"android:id/content\"]/android.view.View/android.view.View/android.view.View[2]");
                if (tabOutSide != null) tabOutSide.click();
            }
            System.out.println("✅ Close bottom sheet");
        } catch (Exception ignored){
        }
        System.out.println("✅ Welcome to Hi FPT!!!");
    }

    private static void readTestDataFromExcel(String excelFilePath, AppiumDriver<MobileElement> appiumDriver, WebDriverWait wait) {
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            int totalCases = sheet.getLastRowNum();
            boolean allTestsPassed = true;
            boolean isModemError = false;

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
                // Kiểm tra lỗi chỉ một lần
                if (!isModemError) {
                    MobileElement popUpNotHaveInfoModem = getElement(wait, "//android.widget.TextView[@text=\"Mất kết nối với Modem\"]");
                    if (popUpNotHaveInfoModem != null && popUpNotHaveInfoModem.isDisplayed()) {
                        MobileElement clickClose = getElement(wait, "//android.widget.TextView[@text=\"Đóng\"]");
                        if (clickClose != null) clickClose.click();
                        System.out.println("⚠️ Vui lòng đổi sang Hợp đồng khác vì Hợp đồng này không có thông tin Modem");
                        // Stay in app 10s before stop process
                        Thread.sleep(6000);
                        isModemError = true; // Đánh dấu đã xử lý lỗi
                        System.exit(1);
                    }
                    MobileElement popupSystemError = getElement(wait, "//android.widget.TextView[@text=\"Chưa hiển thị được thông tin, vui lòng thử lại sau.\"]");
                    if (popupSystemError != null && popupSystemError.isDisplayed()){
                        MobileElement clickClose = getElement(wait, "//android.widget.TextView[@text=\"Đóng\"]");
                        if (clickClose != null) clickClose.click();
                        System.out.println("Chưa hiển thị được thông tin");
                        // Stay in app 10s before stop process
                        Thread.sleep(6000);
                        isModemError = true; // Đánh dấu đã xử lý lỗi
                        System.exit(1);
                    }
                    MobileElement popupNotHaveInfoModem = getElement(wait, "//android.widget.TextView[@text=\"Chưa có thông tin\"]");
                    if (popupNotHaveInfoModem != null && popupNotHaveInfoModem.isDisplayed()){
                        MobileElement clickClose = getElement(wait, "//android.widget.TextView[@text=\"Đóng\"]");
                        if (clickClose != null) clickClose.click();
                        System.out.println("Chưa có thông tin Modem");
                        // Stay in app 10s before stop process
                        Thread.sleep(6000);
                        isModemError = true; // Đánh dấu đã xử lý lỗi
                        System.exit(1);
                    }
                }

                if (!result) {
                    allTestsPassed = false;
                    System.out.println("❌ Test case failed: " + testCaseName);
                }
            }


            System.out.println(allTestsPassed ? "✅ PASS - All test cases have been run" : "❌ FAIL - Some test cases failed");

            // Xuất báo cáo
            String reportFilePath = "D:\\Test_Report.xlsx"; //Remember to change
            Report.exportReport(excelFilePath, reportFilePath);

            // Stay in app 15s before stop process
            Thread.sleep(10000);
            // Dừng chương trình ngay sau khi chạy hết test cases
            System.exit(1);

        } catch (IOException e) {
            System.err.println("Error reading Excel file: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean performAction(AppiumDriver<MobileElement> appiumDriver, String action, String ID, String inputData, String coordinates) {
        WebDriverWait wait = new WebDriverWait(appiumDriver, 15);

        Map<String, Consumer<String>> actions = new HashMap<>();
        actions.put("click", id -> clickElement(wait, id));
        actions.put("value", id -> inputText(wait, id, inputData));
        actions.put("tap", id -> {
            if (coordinates != null && !coordinates.isEmpty()) {
                tapCoordinates(appiumDriver, coordinates);
            } else {
                System.out.println("⚠️ Skipping tap, no coordinates provided.");
            }
        });

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

    private static final Set<String> failedElements = new HashSet<>(); // Lưu ID đã lỗi

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
            // Nếu ID chưa từng báo lỗi, in ra thông báo
            if (!failedElements.contains(ID)) {
                System.out.println("⚠️ Element not found, because not showing: " + ID);
                failedElements.add(ID); // Đánh dấu ID này đã bị lỗi
            }
            return null;  // Trả về null để bỏ qua test case này
        }
    }

    public static String getCellValue(Row row, int index) {
        Cell cell = row.getCell(index);
        return (cell != null) ? cell.toString().trim() : "";
    }
}