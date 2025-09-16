package src.AutomationTestApplication;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.poi.ss.usermodel.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import src.Handle.HandleLogin;
import src.Handle.HandlePopupNotification;
import src.Handle.SwipeUpAndDown;
import src.driverForHiFPT.DriverFactoryForHiFPT;
import src.driverForHiFPT.Platform;

import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;

public class HiFPTAutomationTestReadTCs {

    public static void main(String[] args) throws MalformedURLException {
        AppiumDriver<MobileElement> appiumDriver = DriverFactoryForHiFPT.getDriver(Platform.ANDROID);
        WebDriverWait wait = new WebDriverWait(appiumDriver, 5);

        try {
            System.out.println("=====Start running Automation Test=====");

            // Xử lý đăng nhập
            HandleLogin.LoginScreen(wait);

            // Kiểm tra popup thông báo
            HandlePopupNotification.handlePopUpNotification(wait);

            // Handle swipe up and down
            SwipeUpAndDown.scrollUp(appiumDriver);
//            SwipeUpAndDown.scrollDown(appiumDriver);

            // Đọc file Excel & thực thi test cases
            String fileYAML = "/baymax/test.yaml"; //Remember to change
            readTestDataFromYaml(fileYAML, appiumDriver, wait);

            // Stay in app 6s before stop process
            Thread.sleep(6000);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            appiumDriver.quit();
        }
    }

    private static void readTestDataFromYaml(String yamlFilePath, AppiumDriver<MobileElement> appiumDriver, WebDriverWait wait) {
        boolean allTestsPassed = true;
        boolean isModemError = false;

        try (InputStream inputStream = new FileInputStream(yamlFilePath)) {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);

            List<Map<String, Object>> testCases = (List<Map<String, Object>>) data.get("testcases");

            for (Map<String, Object> tc : testCases) {
                String testCaseName = (String) tc.getOrDefault("name", "");
                String action = (String) tc.getOrDefault("action", "");
                String ID = (String) tc.getOrDefault("id", "");
                String coordinates = (String) tc.getOrDefault("coordinates", "");
                String inputData = (String) tc.getOrDefault("input", "");

                if (testCaseName.trim().isEmpty() || action.trim().isEmpty()) {
                    System.out.println("⚠️ Invalid test case format: " + tc);
                    continue;
                }

                Thread.sleep(1500);
                System.out.println("⮑ Running case: " + testCaseName);

                boolean resultOfTest = performAction(appiumDriver, action, ID, inputData, coordinates);

                // Kiểm tra popup lỗi (chỉ một lần)
                if (!isModemError) {
                    MobileElement popUpNotHaveInfoModem = getElement(wait, "//android.widget.TextView[@text=\"Mất kết nối với Modem\"]");
                    if (popUpNotHaveInfoModem != null && popUpNotHaveInfoModem.isDisplayed()) {
                        MobileElement clickClose = getElement(wait, "//android.widget.TextView[@text=\"Đóng\"]");
                        if (clickClose != null) clickClose.click();
                        System.out.println("⚠️ Vui lòng đổi sang Hợp đồng khác vì Hợp đồng này không có thông tin Modem");
                        Thread.sleep(6000);
                        isModemError = true;
                        System.exit(1);
                    }

                    MobileElement popupSystemError = getElement(wait, "//android.widget.TextView[@text=\"Chưa hiển thị được thông tin, vui lòng thử lại sau.\"]");
                    if (popupSystemError != null && popupSystemError.isDisplayed()) {
                        MobileElement clickClose = getElement(wait, "//android.widget.TextView[@text=\"Đóng\"]");
                        if (clickClose != null) clickClose.click();
                        System.out.println("⚠️ Chưa hiển thị được thông tin");
                        Thread.sleep(6000);
                        isModemError = true;
                        System.exit(1);
                    }

                    MobileElement popupNotHaveInfo = getElement(wait, "//android.widget.TextView[@text=\"Chưa có thông tin\"]");
                    if (popupNotHaveInfo != null && popupNotHaveInfo.isDisplayed()) {
                        MobileElement clickClose = getElement(wait, "//android.widget.TextView[@text=\"Đóng\"]");
                        if (clickClose != null) clickClose.click();
                        System.out.println("⚠️ Chưa có thông tin Modem");
                        Thread.sleep(6000);
                        isModemError = true;
                        System.exit(1);
                    }
                }

                // Ghi nhận kết quả
                if (!resultOfTest) {
                    allTestsPassed = false;
                    System.out.println("❌ Test case failed: " + testCaseName);
                }
            }

            System.out.println(allTestsPassed ? "✅ PASS - All test cases have been run" : "❌ FAIL - Some test cases failed");

        } catch (Exception e) {
            System.err.println("❌ Error reading YAML test data: " + e.getMessage());
        }
    }

    private static boolean performAction(AppiumDriver<MobileElement> appiumDriver, String action, String ID, String inputData, String coordinates) {
        WebDriverWait wait = new WebDriverWait(appiumDriver, 5);

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
}