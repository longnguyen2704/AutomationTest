package src.api_learning;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import src.driverForEmoney.DriverFactoryForEmoney;
import src.driverForEmoney.Platform;

import java.awt.*;
import java.time.Duration;

public class EmoneyAutomationTest {
    public static void main(String[] args) {
        AppiumDriver<MobileElement> appiumDriver
                = DriverFactoryForEmoney.getDriver(Platform.ANDROID);

        try {
            //Screen choose Language
            MobileElement ChooseLangBtn
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.FrameLayout/android.widget.LinearLayout"));
            ChooseLangBtn.click();
            Thread.sleep(1500);
            //Click button [Continue] at screen guideline
            MobileElement ContinueBtn
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.TextView[@resource-id=\"com.viettel.vtt.vn.emoneycustomer.dev:id/btnContinue\"]"));
            ContinueBtn.click();
            Thread.sleep(1000);
            ContinueBtn.click();
            Thread.sleep(1000);
            ContinueBtn.click();
            //Screen enter phone to verify
            MobileElement PhoneVerify
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.EditText[@resource-id=\"com.viettel.vtt.vn.emoneycustomer.dev:id/et_phone_number\"]"));
            PhoneVerify.sendKeys("0315445204");

            MobileElement VerifyBtn
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout"));
            VerifyBtn.click();
            Thread.sleep(1500);

            //appiumDriver.quit();

            //Find Login form elements and interact
//            int xPhoneNumber = 272;
//            int yPhoneNumber = 700;
//            PointOption startPointPhoneNumber = new PointOption<>().withCoordinates(xPhoneNumber, yPhoneNumber);
//
//            TouchAction touchActionPhone = new TouchAction(appiumDriver);
//            touchActionPhone
//                    .press(startPointPhoneNumber)
//                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
//                    .release()
//                    .perform();
//            MobileElement phoneNumberInputElem
//                    = appiumDriver.findElement(MobileBy.xpath("(//android.widget.LinearLayout[@resource-id=\"com.viettel.vtt.vn.emoneycustomer.dev:id/lnLoginSignup\"])[2]"));
//            phoneNumberInputElem.sendKeys("0315445204");
//            Thread.sleep(1000);

            int xPinNumber = 242;
            int yPinNumber = 849;
            PointOption startPointPinNumber = new PointOption<>().withCoordinates(xPinNumber, yPinNumber);
            TouchAction touchActionPin = new TouchAction(appiumDriver);
            touchActionPin
                    .press(startPointPinNumber)
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                    .release()
                    .perform();
            MobileElement NoSavePassword
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.Button[@resource-id=\"android:id/autofill_dialog_no\"]"));
            NoSavePassword.click();
            //Input 0
            int x0 = 502;
            int y0 = 2138;
            //Input 4
            int x1 = 193;
            int y1 = 1807;
            //Input 0
            int x2 = 502;
            int y2 = 2138;
            //Input 1
            int x3 = 164;
            int y3 = 1651;
            //Input 0
            int x4 = 502;
            int y4 = 2138;
            //Input 0
            int x5 = 502;
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

            MobileElement ClickLoginBtn
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.Button[@resource-id=\"com.viettel.vtt.vn.emoneycustomer.dev:id/btnLogin\"]"));
            ClickLoginBtn.click();
            //Verification

            //Print the dialog content

            //Debug purpose ONLY
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        appiumDriver.quit();
    }
}
