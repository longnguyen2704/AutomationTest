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
    public static void main(String[] args) {
        AppiumDriver<MobileElement> appiumDriver = DriverFactoryForMdealer.getDriver(Platform.ANDROID);
        WebDriverWait wait = new WebDriverWait(appiumDriver, 10L);
        try {
            //Home screen mDealer
            MobileElement HomeScreenMdealer = appiumDriver.findElement(MobileBy.id("com.metfone.mdealer:id/image_hot_announcement"));
            wait.until(ExpectedConditions.visibilityOf(HomeScreenMdealer));
            Thread.sleep(1500);
            try {
                //Xử lí case mỗi lần vô app Mdealer
                MobileElement clickButtonX = appiumDriver.findElement(MobileBy.id("com.metfone.mdealer:id/image_hot_announcement_close"));
                clickButtonX.click();
                //Xử lí Phone number
                List<MobileElement> phoneNumber = appiumDriver.findElements(MobileBy.id("com.metfone.mdealer:id/editText_phone_number"));
                if (!phoneNumber.isEmpty()) {
                    MobileElement phoneNumbers = phoneNumber.get(0);
                    if (phoneNumbers.isDisplayed() && phoneNumbers.getText().isEmpty()) {
                        phoneNumbers.sendKeys("716657808");
                        wait.until(ExpectedConditions.visibilityOf(phoneNumbers));
                    }
                    Thread.sleep(1000);
                }
                //Xử lí Password
                MobileElement Password = appiumDriver.findElement(MobileBy.id("com.metfone.mdealer:id/editText_passWord"));
                Password.sendKeys("020990");
                Thread.sleep(1000);

                //Xử lí button Login
                MobileElement buttonLogin = appiumDriver.findElement(MobileBy.id("com.metfone.mdealer:id/btn_login"));
                buttonLogin.click();
                Thread.sleep(1000);

                //Xử lí màn Home
                MobileElement PopupWarning = appiumDriver.findElement(MobileBy.id("com.metfone.mdealer:id/txtMessage"));
                wait.until(ExpectedConditions.visibilityOf(PopupWarning));
                MobileElement buttonClosePopup = appiumDriver.findElement(MobileBy.id("com.metfone.mdealer:id/btnClose"));
                if (PopupWarning.isDisplayed()) {
                    buttonClosePopup.click();
                }
                MobileElement HomeScreen = appiumDriver.findElement(MobileBy.id("com.metfone.mdealer:id/imageView_openSlider"));
                wait.until(ExpectedConditions.visibilityOf(HomeScreen));
            } catch (InterruptedException e) {
                e.getMessage();
            }
            //Xử lí đọc file YAML
            try {
                String yamlFilePath1 = "D:\\ekyc.yaml";
                readTestDataFromYAML(yamlFilePath1, appiumDriver);
                //Xuất report
                String htmlFilePath = "D:\\report.html";
                Report.exportReport(yamlFilePath1, htmlFilePath);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("System error. Please try again" + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readTestDataFromYAML(String yamlFilePath, AppiumDriver<MobileElement> appiumDriver) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(appiumDriver, 10L);
        try (InputStream inputStream = new FileInputStream(yamlFilePath)) {
            Yaml yaml = new Yaml();
            Object yamlObject = yaml.load(inputStream);
            if (yamlObject instanceof Map) {
                Map<String, List<Map<String, String>>> testData = (Map<String, List<Map<String, String>>>) yamlObject;
                // Tiếp tục xử lý dữ liệu YAML ở đây
                for (Map.Entry<String, List<Map<String, String>>> entry : testData.entrySet()) {
                    String testCaseName = entry.getKey();
                    List<Map<String, String>> testStep = entry.getValue();
                    performTestSteps(appiumDriver, testCaseName, testStep);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void performTestSteps(AppiumDriver<MobileElement> appiumDriver, String testCaseName, List<Map<String, String>> testStep) throws InterruptedException {
        //Vòng lặp qua các bước kiểm thử trong Testcase
        System.out.println("=====" + testCaseName + "=====");
        for (Map<String, String> step : testStep) {
            String action = step.get("action");
            String ID = step.get("ID");
            String inputData = step.get("inputData");
            String coordinates = step.get("coordinates");
            System.out.println("Executing step - Action: " + action + ", ID: " + ID + ", Coordinates: " + coordinates + ", InputData: " + inputData);
            performAction(appiumDriver, action, ID, inputData, coordinates);
            Thread.sleep(1500);
        }
    }

    private static void performAction(AppiumDriver<MobileElement> appiumDriver, String action, String ID, String inputData, String coordinates) throws InterruptedException {
        String selector = action.toLowerCase();
        WebDriverWait wait = new WebDriverWait(appiumDriver, 5L);
        switch (selector) {
            case "click":
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
