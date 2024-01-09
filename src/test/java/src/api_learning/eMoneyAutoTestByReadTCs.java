package src.api_learning;

import io.appium.java_client.*;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.yaml.snakeyaml.Yaml;
import src.driverForEmoney.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class eMoneyAutoTestByReadTCs {
    public static void main(String[] args) throws IOException {
        AppiumDriver<MobileElement> appiumDriver
                = DriverFactoryForEmoney.getDriver(Platform.ANDROID);
        WebDriverWait wait = new WebDriverWait(appiumDriver, 5L);
        //Screen choose Language
        try {
            MobileElement ChooseLangBtn
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.FrameLayout/android.widget.LinearLayout"));
            if (ChooseLangBtn.isDisplayed()) {
                wait.until(ExpectedConditions
                        .visibilityOfElementLocated(MobileBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.FrameLayout/android.widget.LinearLayout")));
                ChooseLangBtn.click();
                Thread.sleep(1500);
            } else {
                appiumDriver.quit();
            }
            //Click button [Continue] 3 times at screen guideline
            MobileElement ContinueBtn
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.TextView[@resource-id=\"com.viettel.vtt.vn.emoneycustomer.dev:id/btnContinue\"]"));
            wait.until(ExpectedConditions
                    .visibilityOfElementLocated(MobileBy.xpath("//android.widget.TextView[@resource-id=\"com.viettel.vtt.vn.emoneycustomer.dev:id/btnContinue\"]")));
            final int MAX_CLICK = 3;
            for (int i = 0; i < MAX_CLICK; i++) {
                ContinueBtn.click();
            }
            Thread.sleep(1000);
            //Screen enter phone to verify
            MobileElement PhoneVerify
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.EditText[@resource-id=\"com.viettel.vtt.vn.emoneycustomer.dev:id/et_phone_number\"]"));
            wait.until(ExpectedConditions
                    .visibilityOfElementLocated(MobileBy.xpath("//android.widget.EditText[@resource-id=\"com.viettel.vtt.vn.emoneycustomer.dev:id/et_phone_number\"]")));
            PhoneVerify.sendKeys("0315445204");
            Thread.sleep(1500);

            MobileElement VerifyBtn
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout"));
            VerifyBtn.click();
            Thread.sleep(1500);

            //Click field PIN
            int xPinNumber = 242;
            int yPinNumber = 849;
            PointOption startPointPinNumber = new PointOption<>().withCoordinates(xPinNumber, yPinNumber);
            TouchAction touchActionPin = new TouchAction(appiumDriver);
            touchActionPin
                    .press(startPointPinNumber)
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                    .release()
                    .perform();
            Thread.sleep(2000);

            //No save password from Google
            MobileElement NoSavePassword
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.Button[@resource-id=\"android:id/autofill_dialog_no\"]"));
            Thread.sleep(2000);
            wait.until(ExpectedConditions
                    .visibilityOfElementLocated(MobileBy.xpath("//android.widget.Button[@resource-id=\"android:id/autofill_dialog_no\"]")));
            NoSavePassword.click();
            Thread.sleep(2000);
            //Input 0
            int x0 = 542;
            int y0 = 2138;
            //Input 4
            int x1 = 193;
            int y1 = 1807;
            //Input 0
            int x2 = 542;
            int y2 = 2138;
            //Input 1
            int x3 = 164;
            int y3 = 1651;
            //Input 0
            int x4 = 542;
            int y4 = 2138;
            //Input 0
            int x5 = 542;
            int y5 = 2138;

            MobileElement pinNumberInputElem
                    = appiumDriver.findElement(MobileBy.xpath("(//android.widget.LinearLayout[@resource-id=\"com.viettel.vtt.vn.emoneycustomer.dev:id/lnLoginSignup\"])[2]"));
            pinNumberInputElem.click();

            PointOption startPoint0 = new PointOption<>().withCoordinates(x0, y0);
            TouchAction touchAction0 = new TouchAction(appiumDriver);
            touchAction0
                    .press(startPoint0)
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                    .release()
                    .perform();

            PointOption startPoint1 = new PointOption<>().withCoordinates(x1, y1);
            TouchAction touchAction1 = new TouchAction(appiumDriver);
            touchAction1
                    .press(startPoint1)
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                    .release()
                    .perform();

            PointOption startPoint2 = new PointOption<>().withCoordinates(x2, y2);
            TouchAction touchAction2 = new TouchAction(appiumDriver);
            touchAction2
                    .press(startPoint2)
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                    .release()
                    .perform();

            PointOption startPoint3 = new PointOption<>().withCoordinates(x3, y3);
            TouchAction touchAction3 = new TouchAction(appiumDriver);
            touchAction3
                    .press(startPoint3)
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                    .release()
                    .perform();

            PointOption startPoint4 = new PointOption<>().withCoordinates(x4, y4);
            TouchAction touchAction4 = new TouchAction(appiumDriver);
            touchAction4
                    .press(startPoint4)
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                    .release()
                    .perform();

            PointOption startPoint5 = new PointOption<>().withCoordinates(x5, y5);
            TouchAction touchAction5 = new TouchAction(appiumDriver);
            touchAction5
                    .press(startPoint5)
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                    .release()
                    .perform();

            //Click login button
            MobileElement ClickLoginBtn
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.Button[@resource-id=\"com.viettel.vtt.vn.emoneycustomer.dev:id/btnLogin\"]"));
            wait.until(ExpectedConditions
                    .visibilityOfElementLocated(MobileBy.xpath("//android.widget.Button[@resource-id=\"com.viettel.vtt.vn.emoneycustomer.dev:id/btnLogin\"]")));
            ClickLoginBtn.click();
            Thread.sleep(1500);

            //Enter OTP (hardcode)
            MobileElement EnterOTP
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.EditText[@resource-id=\"android:id/input\"]"));
            if (EnterOTP.isDisplayed()) {
                wait.until(ExpectedConditions
                        .visibilityOfElementLocated(MobileBy.xpath("//android.widget.EditText[@resource-id=\"android:id/input\"]")));
                Thread.sleep(1500);
                EnterOTP.sendKeys("1234");
                Thread.sleep(1500);
            } else {
                appiumDriver.close();
            }

            //Click button Continue
            MobileElement ClickContinueBtnOTP
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.TextView[@resource-id=\"com.viettel.vtt.vn.emoneycustomer.dev:id/md_buttonDefaultPositive\"]"));
            ClickContinueBtnOTP.click();
            Thread.sleep(1500);

            //Non-face ID
            MobileElement DisBiometricBtn
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.TextView[@resource-id=\"com.viettel.vtt.vn.emoneycustomer.dev:id/md_buttonDefaultNegative\"]"));
            wait.until(ExpectedConditions
                    .visibilityOfElementLocated(MobileBy.xpath("//android.widget.TextView[@resource-id=\"com.viettel.vtt.vn.emoneycustomer.dev:id/md_buttonDefaultNegative\"]")));
            DisBiometricBtn.click();
            Thread.sleep(1500);

            String yamlFilePath = "D:\\testcase.yaml";
            readTestDataFromYAML(yamlFilePath, appiumDriver);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        appiumDriver.quit();
    }

    private static void readTestDataFromYAML(String yamlFilePath, AppiumDriver<MobileElement> appiumDriver) throws IOException, InterruptedException {
        try (InputStream inputStream = new FileInputStream(yamlFilePath)) {
            Yaml yaml = new Yaml();
            Map<String, List<Map<String, String>>> testData = yaml.load(inputStream);
            // Vòng lặp qua các bước kiểm thử từ dữ liệu YAML
            for (Map.Entry<String, List<Map<String, String>>> entry : testData.entrySet()) {
                String testCaseName = entry.getKey();
                List<Map<String, String>> testStep = entry.getValue();
                // Thực hiện các bước kiểm thử trong testcase từ dữ liệu YAML
                performTestSteps(appiumDriver, testCaseName, testStep);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static void performTestSteps(AppiumDriver<MobileElement> appiumDriver, String testCaseName, List<Map<String, String>> testStep) throws InterruptedException {
        System.out.println("Executing Test Case: " + testCaseName);
        //Vòng lặp qua các bước kiểm thử trong Testcase
        for (Map<String, String> step : testStep) {
            String action = step.get("action");
            String ID = step.get("ID");
            String inputData = step.get("inputData");
            System.out.println("Executing step - Action: " + action + ", ID: " + ID + ", InputData: " + inputData);
            performAction(appiumDriver, action, ID, inputData);
            System.out.println("Step executed successfully.");
        }
    }

    private static void performAction(AppiumDriver<MobileElement> appiumDriver, String action, String ID, String inputData) throws InterruptedException {
        String elemAction = action.toLowerCase();
        WebDriverWait wait = new WebDriverWait(appiumDriver, 10L);
        switch (elemAction) {
            case "click":
                wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id(ID)));
                Thread.sleep(5000);
                appiumDriver.findElement(MobileBy.id(ID)).click();
                break;
            case "tap":
                Thread.sleep(3000);
                TouchAction touchAction = getValueXYFromElem(ID);
                appiumDriver.performTouchAction(touchAction);
                break;
            case "sendKeys":
                MobileElement PhoneReceiver = appiumDriver.findElement(By.id(ID));
                try {
                    wait.until(ExpectedConditions.visibilityOf(PhoneReceiver));
                    if (PhoneReceiver.isDisplayed()) {
                        PhoneReceiver.sendKeys(inputData);
                        System.out.println("Entered text: " + inputData);
                    } else {
                        System.out.println("Phone receiver is not displayed or not enabled.");
                    }
                } catch (Exception e) {
                    System.out.println("Exception: " + e.getMessage());
                }
                break;
            default:
                appiumDriver.findElementsById(ID);
                break;
        }
    }

    private static TouchAction getValueXYFromElem(String element) {
        AppiumDriver<MobileElement> appiumDriver
                = DriverFactoryForEmoney.getDriver(Platform.ANDROID);
        String[] parts = element.split(",");
        String xPart = parts[0].split("x:")[1].trim();
        String yPart = parts[1].split("y:")[1].trim();

        int xValue = Integer.parseInt(xPart);
        int yValue = Integer.parseInt(yPart);

        return new TouchAction(appiumDriver).tap(PointOption.point(xValue, yValue));
    }
}