package src.AutomationTestApplication;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import src.Handle.HandleLogin;
import src.Handle.HandlePopupNotification;
import src.Handle.SwipeUpAndDown;
import src.driverForHiFPT.DriverFactoryForHiFPT;
import src.driverForHiFPT.Platform;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import static src.Handle.ReadTestcaseByFileYaml.readTestDataFromYaml;

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
            SwipeUpAndDown.scrollUp(appiumDriver,3);
//            SwipeUpAndDown.scrollDown(appiumDriver);

            // Đọc file Excel & thực thi test cases
            String fileYAML = "/Users/baymax/test.yaml"; //Remember to change
            readTestDataFromYaml(fileYAML, appiumDriver, wait);

            // Stay in app 6s before stop process
            Thread.sleep(6000);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            appiumDriver.quit();
        }
    }

    public static boolean performAction(AppiumDriver<MobileElement> appiumDriver, String action, String ID, String inputData, String coordinates) throws MalformedURLException {
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
        } else {
            System.out.println("⚠️ Invalid action: '" + action + "' at ID: " + ID);
        }
        return false;
    }
    public static void clickElement(WebDriverWait wait, String ID) {
        MobileElement element = getElement(wait, ID);
        if (element != null) element.click();
    }

    public static void inputText(WebDriverWait wait, String ID, String inputData) {
        if (inputData == null) return;
        MobileElement inputField = getElement(wait, ID);
        if (inputField != null) inputField.sendKeys(inputData);
    }

    public static void tapCoordinates(AppiumDriver<MobileElement> appiumDriver, String coordinates) {
        if (coordinates == null || coordinates.trim().isEmpty()) return;

        String[] parts = coordinates.split(",");
        if (parts.length == 2) {
            int x = Integer.parseInt(parts[0].trim());
            int y = Integer.parseInt(parts[1].trim());
            new TouchAction<>(appiumDriver).tap(PointOption.point(x, y)).perform();
        }
    }

    public static final Set<String> failedElements = new HashSet<>(); // Lưu ID đã lỗi

    public static MobileElement getElement(WebDriverWait wait, String ID) {
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