package src.AutomationTestApplication;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.yaml.snakeyaml.Yaml;
import src.driverForMdealer.Platform;
import src.driverForMdealer.Report;
import src.driverForMdealer.DriverFactoryForMdealer;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class mDealerAutomationTestReadTCs {
    private static boolean isYamlProcessed = false;
    public static void main(String[] args) {
        if (!isYamlProcessed) {
            AppiumDriver<MobileElement> appiumDriver = DriverFactoryForMdealer.getDriver(Platform.ANDROID);
            WebDriverWait wait = new WebDriverWait(appiumDriver, 60L);
            try {
                //Permission
                MobileElement PermissionCameraAndVideo =
                        (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("com.android.permissioncontroller:id/permission_message")));
                if (PermissionCameraAndVideo.isDisplayed()) {
                    MobileElement AllowCameraAndVideo =
                            (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("com.android.permissioncontroller:id/permission_allow_foreground_only_button")));
                    AllowCameraAndVideo.click();
                }
                //Announcement
                MobileElement Announcement =
                        (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("com.metfone.mdealer:id/image_hot_announcement")));
                if (Announcement.isDisplayed()) {
                    MobileElement ButtonX =
                            (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("com.metfone.mdealer:id/image_hot_announcement_close")));
                    ButtonX.click();
                }
                //Language
                MobileElement ChangeLanguage =
                        (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("com.metfone.mdealer:id/txt_language")));
                if (ChangeLanguage.isDisplayed()) {
                    ChangeLanguage.click();
                }
                //Choose Language = En
                MobileElement ChooseLanguage =
                        (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("com.metfone.mdealer:id/container_language_en")));
                if (ChooseLanguage.isDisplayed()) {
                    ChooseLanguage.click();
                }
                //Xử lí Phone number
                MobileElement PhoneNumber =
                        (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("com.metfone.mdealer:id/editText_phone_number")));
                if (PhoneNumber.isDisplayed()) {
                    PhoneNumber.sendKeys("716657808");
                }
                //Password
                MobileElement Password =
                        (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("com.metfone.mdealer:id/editText_passWord")));
                Password.sendKeys("020990");
                //button Login
                MobileElement buttonLogin =
                        (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("com.metfone.mdealer:id/btn_login")));
                buttonLogin.click();
                //Home screen
                MobileElement PopupWarning =
                        (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("com.metfone.mdealer:id/textView_back")));
                if (PopupWarning.isDisplayed()) {
                    MobileElement buttonClosePopup =
                            (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("com.metfone.mdealer:id/btnClose")));
                    buttonClosePopup.click();
                }
            } catch (Exception e) {
                System.err.println("Error!!!!" + e.getMessage());
            }
            try {
                //Xử lí đọc file YAML
                String yamlFilePath1 = "D:\\ekyc.yaml";
                readTestDataFromYAML(yamlFilePath1, appiumDriver);
                //Xuất report
                String htmlFilePath = "D:\\report.html";
                Report.exportReport(yamlFilePath1, htmlFilePath);
                //Đánh dấu YAML đã được xử lý để không chạy lại
                isYamlProcessed = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("System error. Please try again" + e.getMessage());
            }
        }
    }

    private static void readTestDataFromYAML(String yamlFilePath, AppiumDriver<MobileElement> appiumDriver) throws InterruptedException {
        try (InputStream inputStream = new FileInputStream(yamlFilePath)) {
            Yaml yaml = new Yaml();
            Object yamlObject = yaml.load(inputStream);
            if (yamlObject instanceof Map) {
                Map<String, List<Map<String, String>>> testData = (Map<String, List<Map<String, String>>>) yamlObject;
                // Tiếp tục xử lý dữ liệu YAML ở đây
                for (Map.Entry<String, List<Map<String, String>>> entry : testData.entrySet()) {
                    String testCaseName = entry.getKey();
                    List<Map<String, String>> testStep = entry.getValue();
                    if (!(testStep == null || testStep.isEmpty())) {
                        performTestSteps(appiumDriver, testCaseName, testStep);
                    } else {
                        System.err.println("No test steps found for test case: " + testCaseName);
                    }
                }
                System.out.println("All test cases completed successfully.");
            } else {
                throw new IllegalArgumentException("Invalid YAML file format. Expected a map of test cases.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void performTestSteps(AppiumDriver<MobileElement> appiumDriver, String testCaseName, List<Map<String, String>> testStep) throws InterruptedException {
        //Vòng lặp qua các bước kiểm thử trong Testcase
        WebDriverWait wait = new WebDriverWait(appiumDriver, 10L);
        System.out.println("=====" + testCaseName + "=====");
        for (Map<String, String> step : testStep) {
            String action = step.get("action");
            String ID = step.get("ID");
            String inputData = step.get("inputData");
            String coordinates = step.get("coordinates");
            System.out.println("Action: " + action + ", ID: " + ID + ", Coordinates: " + coordinates + ", InputData: " + inputData);
            performAction(appiumDriver, action, ID, inputData, coordinates);
            Thread.sleep(1500);
        }
    }

    public static void performAction(AppiumDriver<MobileElement> appiumDriver, String action, String ID, String inputData, String coordinates) throws InterruptedException {
        String selector = action.toLowerCase();
        WebDriverWait wait = new WebDriverWait(appiumDriver, 10L);
        switch (selector) {
            case "click":
                try {
                    Thread.sleep(2000);
                    if (ID.startsWith("com")) {
                        wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(ID)));
                        appiumDriver.findElement(MobileBy.id(ID)).click();
                        Thread.sleep(500);
                    } else if (ID.startsWith("//")) {
                        //Nếu selector bắt đầu bằng "//", sử dụng xpath
                        wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.xpath(ID)));
                        appiumDriver.findElement(MobileBy.xpath(ID)).click();
                        Thread.sleep(500);
                    } else if (ID.startsWith("android.widget")) {
                        //Nếu selector bắt đầu bằng "android.widget", sử dụng class_name
                        wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.className(ID)));
                        appiumDriver.findElement(MobileBy.className(ID)).click();
                        Thread.sleep(500);
                    } else {
                        //Mặc định sử dụng ID
                        wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(ID)));
                        appiumDriver.findElement(MobileBy.id(ID)).click();
                        Thread.sleep(500);
                    }
                } catch (Exception e) {
                    System.out.println("Exception: " + e.getMessage());
                }
                break;
            case "tap":
                try {
                    String[] parts = coordinates.split(",");
                    String xPart = parts[0].split("x:")[1].trim();
                    String yPart = parts[1].split("y:")[1].trim();
                    int xValue = Integer.parseInt(xPart);
                    int yValue = Integer.parseInt(yPart);

                    TouchAction touchAction = new TouchAction(appiumDriver);
                    touchAction.tap(PointOption.point(xValue, yValue)).perform();
                } catch (Exception e) {
                    System.out.println("Exception: " + e.getMessage());
                }
                break;
            case "value":
                Thread.sleep(500);
                try {
                    if (ID.startsWith("com")) {
                        //Nếu selector bắt đầu bằng "com", sử dụng resource-id
                        wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(ID)));
                        appiumDriver.findElement(MobileBy.id(ID)).sendKeys(inputData);
                        Thread.sleep(500);
                    } else if (ID.startsWith("//")) {
                        //Nếu selector bắt đầu bằng "//", sử dụng xpath
                        wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.xpath(ID)));
                        appiumDriver.findElement(MobileBy.xpath(ID)).sendKeys(inputData);
                        Thread.sleep(500);
                    } else if (ID.startsWith("android.widget")) {
                        //Nếu selector bắt đầu bằng "android.widget", sử dụng class_name
                        wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.className(ID)));
                        appiumDriver.findElement(MobileBy.className(ID)).sendKeys(inputData);
                        Thread.sleep(500);
                    } else {
                        wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(ID)));
                        appiumDriver.findElement(MobileBy.id(ID)).sendKeys(inputData);
                        Thread.sleep(500);
                    }
                } catch (Exception e) {
                    System.out.println("Exception: " + e.getMessage());
                }
                break;
            default:
                appiumDriver.findElement(MobileBy.id(ID));
                break;
        }
    }
}
