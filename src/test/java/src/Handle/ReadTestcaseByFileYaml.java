package src.Handle;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
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

                Thread.sleep(1500);
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

            // Tìm element theo resource-id
            MobileElement element = (MobileElement) wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.id(id))
            );
            // Chờ element có thể click
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            System.out.println("✅ Click success at: " + id);
            return true;

        } catch (Exception e) {
            System.out.println("❌ Click failed at: " + id + " - " + e.getMessage());
            return false;
        }
    }

    public static boolean inputText(AppiumDriver<MobileElement> driver, String id, String inputData) {
        if (inputData == null) return false;

        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);

            // Chờ element xuất hiện và hiển thị
            MobileElement inputField = (MobileElement) wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id(id))
            );

            inputField.clear();
            inputField.sendKeys(inputData);

            System.out.println("✅ Input success at: " + id + " -> " + inputData);
            return true;

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

    public static MobileElement getElement(AppiumDriver<MobileElement> driver, String locator) {
        if (locator == null || locator.trim().isEmpty()) return null;

        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            String currentContext = driver.getContext();

            // 👉 Nếu đang ở WebView -> dùng By của Selenium (DOM HTML)
            if (currentContext != null && currentContext.toUpperCase().contains("WEBVIEW")) {
                if (locator.startsWith("//")) {
                    return (MobileElement) wait.until(
                            ExpectedConditions.presenceOfElementLocated(By.xpath(locator))
                    );
                } else if (locator.startsWith("css=")) {
                    String css = locator.replaceFirst("css=", "");
                    return (MobileElement) wait.until(
                            ExpectedConditions.presenceOfElementLocated(By.cssSelector(css))
                    );
                } else { // fallback id
                    return (MobileElement) wait.until(
                            ExpectedConditions.presenceOfElementLocated(By.id(locator))
                    );
                }
            }

            // 👉 Nếu đang ở Native -> dùng MobileBy
            if (locator.startsWith("com")) { // resource-id
                return (MobileElement) wait.until(
                        ExpectedConditions.presenceOfElementLocated(MobileBy.id(locator))
                );
            } else if (locator.startsWith("//")) { // xpath
                return (MobileElement) wait.until(
                        ExpectedConditions.presenceOfElementLocated(MobileBy.xpath(locator))
                );
            } else { // className
                return (MobileElement) wait.until(
                        ExpectedConditions.presenceOfElementLocated(MobileBy.className(locator))
                );
            }

        } catch (Exception e) {
            if (!failedElements.contains(locator)) {
                System.out.println("⚠️ Element not found: " + locator + " - " + e.getMessage());
                failedElements.add(locator);
            }
            return null;
        }
    }
}
