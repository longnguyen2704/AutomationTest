package src.AutomationTestApplication;

import org.yaml.snakeyaml.Yaml;
import io.appium.java_client.*;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import src.driverForEmoney.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


public class eMoneyAutoTestByReadTCs implements Report {
    public static void main(String[] args) {
        AppiumDriver<MobileElement> appiumDriver = DriverFactoryForEmoney.getDriver(Platform.ANDROID);
        WebDriverWait wait = new WebDriverWait(appiumDriver, 5L);
        try {
            //Home screen eMoney
            MobileElement HomeScreenEmoney = appiumDriver.findElement(MobileBy.id("com.viettel.vtt.vn.emoneycustomer.dev:id/icTabbarHome"));
            wait.until(ExpectedConditions.visibilityOf(HomeScreenEmoney));
            Thread.sleep(1500);

            //Xử lý các thứ liên quan về file YAML
            try {
                // Đọc dữ liệu từ file YAML
                String yamlFilePath1 = "D:\\ExceedTransfer.yaml";
                readTestDataFromYAML(yamlFilePath1, appiumDriver);
                //Xuất report
//                String reportFilePath = "TestingReport" + ".txt";
                String htmlFilePath = "D:\\report.html";
                Report.exportReport(yamlFilePath1, htmlFilePath);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("Đã xảy ra lỗi trong quá trình đọc dữ liệu từ file YAML: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readTestDataFromYAML(String yamlFilePath, AppiumDriver<MobileElement> appiumDriver) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(appiumDriver, 5L);
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
                    try {
                        // Nếu như đang đọc file YAML mà bị hết session thì xử lí trong đây
                        List<MobileElement> phoneNumber = appiumDriver.findElements(MobileBy.id("com.viettel.vtt.vn.emoneycustomer.dev:id/edtPhone"));
                        if (!phoneNumber.isEmpty()) {
                            MobileElement phoneNumbers = phoneNumber.get(0);
                            if (phoneNumbers.isDisplayed() && phoneNumbers.getText().isEmpty()) {
                                phoneNumbers.sendKeys("0315445204");
                                wait.until(ExpectedConditions.visibilityOf(phoneNumbers));
                            }
                            //Enter PIN
                            MobileElement pinElem = appiumDriver.findElement(MobileBy.id("com.viettel.vtt.vn.emoneycustomer.dev:id/edtPass"));
                            if (pinElem.isDisplayed()) {
                                wait.until(ExpectedConditions.visibilityOf(pinElem));
                                pinElem.sendKeys("040100");
                            }
                            // Click button Login sau khi nhập PIN
                            MobileElement clickButtonLogin = appiumDriver.findElement(MobileBy.id("com.viettel.vtt.vn.emoneycustomer.dev:id/btnLogin"));
                            if (clickButtonLogin.isDisplayed()) {
                                wait.until(ExpectedConditions.visibilityOf(clickButtonLogin));
                                clickButtonLogin.click();
                            }
                            List<MobileElement> enterOTPElement = appiumDriver.findElements(MobileBy.id("android:id/input"));
                            if (!enterOTPElement.isEmpty()) {
                                MobileElement enterOTP = enterOTPElement.get(0);
                                if (enterOTP.isDisplayed()) {
                                    wait.until(ExpectedConditions.visibilityOf(enterOTP));
                                    enterOTP.sendKeys("1234");
                                }
                                MobileElement clickBtnContinue = appiumDriver.findElement(MobileBy.id("com.viettel.vtt.vn.emoneycustomer.dev:id/md_buttonDefaultPositive"));
                                if (clickBtnContinue.isDisplayed()) {
                                    wait.until(ExpectedConditions.visibilityOf(clickBtnContinue));
                                    clickBtnContinue.click();
                                }
                            }
                        }
                        List <MobileElement> offBiometricElements = appiumDriver.findElements(MobileBy.id("com.viettel.vtt.vn.emoneycustomer.dev:id/md_buttonDefaultNegative"));
                        if (!offBiometricElements.isEmpty()) {
                            MobileElement offBiometric = offBiometricElements.get(0);
                            wait.until(ExpectedConditions.visibilityOf(offBiometric));
                            offBiometric.click();
                        }
                        List <MobileElement> buttonBackOfFeedBack = appiumDriver.findElements(MobileBy.id("com.viettel.vtt.vn.emoneycustomer.dev:id/imgCloseSurvey"));
                        if (buttonBackOfFeedBack.isEmpty()) {
                            MobileElement buttonOffFeedback = buttonBackOfFeedBack.get(0);
                            wait.until(ExpectedConditions.visibilityOf(buttonOffFeedback));
                            buttonOffFeedback.click();
                        }
                        List <MobileElement> popUpError = appiumDriver.findElements(MobileBy.id("com.viettel.vtt.vn.emoneycustomer.dev:id/txt_title"));
                        if (popUpError.isEmpty()) {
                            MobileElement popUpErrors = popUpError.get(0);
                            wait.until(ExpectedConditions.visibilityOf(popUpErrors));
                        }
                        else {
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                // Xử lý trường hợp không phải là Map
                System.out.println("Dữ liệu YAML không đúng định dạng.");
                System.out.println("===> " + yamlFilePath + ": run fail");
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