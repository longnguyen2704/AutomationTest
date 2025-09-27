package src.Handle;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

import static src.Handle.Webview.*;


public class ReadTestcaseByFileYaml {
    public static void readTestDataFromYaml(String yamlFilePath, AppiumDriver<MobileElement> appiumDriver) {
        boolean allTestsPassed = true;

        // 👉 Quay lại Native
        switchToNative(appiumDriver, 3);
        // 👉 Switch sang WebView
        switchToWebView(appiumDriver, 3, null);

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

                Thread.sleep(2500);
                System.out.println("⮑ Running case: " + testCaseName);

                boolean resultOfTest = performAction(appiumDriver, action, ID, inputData, coordinates);

                if (!resultOfTest) {
                    allTestsPassed = false;
                    System.out.println("❌ Test case failed: " + testCaseName);
                } else {
                    System.out.println("✅ Test case success: " + testCaseName);
                }
            }

            System.out.println(allTestsPassed ? "✅ PASS - All test cases have been run" : "❌ FAIL - Some test cases failed");

        } catch (Exception e) {
            System.err.println("❌ Error reading YAML test data: " + e.getMessage());
        }
    }

    // Số lần retry mặc định
    private static final int MAX_RETRY = 3;
    private static final int RETRY_DELAY_MS = 2000;

    public static boolean performAction(AppiumDriver<MobileElement> appiumDriver,
                                        String action,
                                        String ID,
                                        String inputData,
                                        String coordinates) {

        for (int attempt = 1; attempt <= MAX_RETRY; attempt++) {
            try {
                boolean result = false;

                switch (action.toLowerCase()) {
                    case "click":
                        result = clickElement(appiumDriver, ID);
                        break;

                    case "value":
                        result = inputText(appiumDriver, ID, inputData);
                        break;

                    case "tap":
                        if (coordinates != null && !coordinates.isEmpty()) {
                            result = tapCoordinates(appiumDriver, coordinates);
                        }
                        break;

                    case "switch_webview":
                        result = Webview.switchToWebView(appiumDriver, 15, inputData);
                        if (result) {
                            System.out.println("🌐 Current context: " + appiumDriver.getContext());
                        }
                        break;

                    case "switch_native":
                        result = Webview.switchToNative(appiumDriver, 10);
                        if (result) {
                            System.out.println("📱 Current context: " + appiumDriver.getContext());
                        }
                        break;

                    default:
                        System.out.println("⚠️ Invalid action: '" + action + "'");
                        return false;
                }

                if (result) return true;

                System.out.println("⚠️ Attempt " + attempt + " failed for action: " + action);
                Thread.sleep(RETRY_DELAY_MS);

            } catch (Exception e) {
                System.out.println("❌ Exception at attempt " + attempt + " - Action: " + action + " - " + e.getMessage());
                try {
                    Thread.sleep(RETRY_DELAY_MS);
                } catch (InterruptedException ignored) {
                }
            }
        }
        return false;
    }

    public static boolean clickElement(AppiumDriver<MobileElement> driver, String id) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            String currentContext = driver.getContext();
            System.out.println("👉 Current context: " + currentContext);

            if (currentContext != null && currentContext.toUpperCase().contains("WEBVIEW")) {
                // Case 2: WebView (HTML DOM)
                WebElement element;
                if (id.startsWith("//")) {
                    element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(id)));
                } else if (id.startsWith("#") || id.contains(".")) {
                    // nếu bạn muốn hỗ trợ CSS selector
                    element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(id)));
                } else {
                    element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
                }

                wait.until(ExpectedConditions.elementToBeClickable(element));
                element.click();
                System.out.println("✅ [WebView] Click success at: " + id);
                return true;

            } else {
                // Case 1: Native app
                MobileElement element;
                if (id.startsWith("com")) {
                    element = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id(id)));
                } else if (id.startsWith("//")) {
                    element = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.xpath(id)));
                } else if (id.startsWith("00000000")) {
                    element = (MobileElement) driver.findElementsById(id);
                } else {
                    element = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.className(id)));
                }

                wait.until(ExpectedConditions.elementToBeClickable(element));
                element.click();
                System.out.println("✅ [Native] Click success at: " + id);
                return true;
            }

        } catch (Exception e) {
            System.out.println("❌ Click failed at: " + id + " - " + e.getMessage());
            return false;
        }
    }

    public static boolean inputText(AppiumDriver<MobileElement> driver, String id, String inputData) {
        if (inputData == null) return false;

        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            String currentContext = driver.getContext();
            System.out.println("👉 Current context: " + currentContext);

            if (currentContext != null && currentContext.toUpperCase().contains("WEBVIEW")) {
                // Case 2: WebView (HTML DOM)
                WebElement inputField;
                if (id.startsWith("//")) {
                    inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(id)));
                } else if (id.startsWith("#") || id.contains(".")) {
                    // CSS selector
                    inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(id)));
                } else {
                    inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
                }

                inputField.clear();
                inputField.sendKeys(inputData);

                System.out.println("✅ [WebView] Input success at: " + id + " -> " + inputData);
                return true;

            } else {
                // Case 1: Native app
                MobileElement inputField;
                if (id.startsWith("com")) {
                    inputField = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(id)));
                } else if (id.startsWith("//")) {
                    inputField = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.xpath(id)));
                } else {
                    inputField = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.className(id)));
                }

                inputField.clear();
                inputField.sendKeys(inputData);

                System.out.println("✅ [Native] Input success at: " + id + " -> " + inputData);
                return true;
            }

        } catch (Exception e) {
            System.out.println("❌ Input failed at: " + id + " - " + e.getMessage());
            return false;
        }
    }

    public static boolean tapCoordinates(AppiumDriver<MobileElement> appiumDriver, String coordinates) {
        if (coordinates == null || coordinates.trim().isEmpty()) return false;

        try {
            String[] parts = coordinates.split(",");
            if (parts.length == 2) {
                int x = Integer.parseInt(parts[0].trim());
                int y = Integer.parseInt(parts[1].trim());
                new TouchAction<>(appiumDriver).tap(PointOption.point(x, y)).perform();
                return true;
            }
        } catch (Exception e) {
            System.out.println("❌ Tap failed at coordinates: " + coordinates + " - " + e.getMessage());
        }
        return false;
    }

    public static final Set<String> failedElements = new HashSet<>(); // Lưu ID đã lỗi

    public static MobileElement getElement(AppiumDriver<MobileElement> driver, String id) {
        if (id == null || id.trim().isEmpty()) return null;

        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            String currentContext = driver.getContext();

            // 👉 Nếu đang ở WebView -> dùng By của Selenium (DOM HTML)
            if (currentContext != null && currentContext.toUpperCase().contains("WEBVIEW")) {
                if (id.startsWith("//")) {
                    return (MobileElement) wait.until(
                            ExpectedConditions.presenceOfElementLocated(By.xpath(id))
                    );
                } else if (id.startsWith("css=")) {
                    String css = id.replaceFirst("css=", "");
                    return (MobileElement) wait.until(
                            ExpectedConditions.presenceOfElementLocated(By.cssSelector(css))
                    );
                } else { // fallback id
                    return (MobileElement) wait.until(
                            ExpectedConditions.presenceOfElementLocated(By.id(id))
                    );
                }
            }

            // 👉 Nếu đang ở Native -> dùng MobileBy
            if (id.startsWith("com")) { // resource-id
                return (MobileElement) wait.until(
                        ExpectedConditions.presenceOfElementLocated(MobileBy.id(id))
                );
            } else if (id.startsWith("//")) { // xpath
                return (MobileElement) wait.until(
                        ExpectedConditions.presenceOfElementLocated(MobileBy.xpath(id))
                );
            } else { // className
                return (MobileElement) wait.until(
                        ExpectedConditions.presenceOfElementLocated(MobileBy.className(id))
                );
            }

        } catch (Exception e) {
            if (!failedElements.contains(id)) {
//                System.out.println("⚠️ Element not found: " + id + " - " + e.getMessage());
                failedElements.add(id);
            }
            return null;
        }
    }
}
