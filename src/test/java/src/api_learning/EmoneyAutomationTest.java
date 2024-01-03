package src.api_learning;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import src.driverForEmoney.DriverFactoryForEmoney;
import src.driverForEmoney.Platform;

import java.time.Duration;

public class EmoneyAutomationTest {
    public static void main(String[] args) {
        AppiumDriver<MobileElement> appiumDriver
                = DriverFactoryForEmoney.getDriver(Platform.ANDROID);

        try {
            //Navigate to Login screen
            MobileElement ClickLoginBtn
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.TextView[@text=\"Login\"]"));
            ClickLoginBtn.click();

            //Find Login form elements and interact
            int xPhoneNumber = 272;
            int yPhoneNumber = 700;
            PointOption startPointPhoneNumber = new PointOption<>().withCoordinates(xPhoneNumber, yPhoneNumber);

            TouchAction touchActionPhone = new TouchAction(appiumDriver);
            touchActionPhone
                    .press(startPointPhoneNumber)
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                    .release()
                    .perform();
            MobileElement phoneNumberInputElem
                    = appiumDriver.findElement(MobileBy.xpath("(//android.widget.LinearLayout[@resource-id=\"com.viettel.vtt.vn.emoneycustomer.dev:id/lnLoginSignup\"])[2]"));
            phoneNumberInputElem.sendKeys("0315445204");
            Thread.sleep(1000);

            int xPinNumber = 242;
            int yPinNumber = 849;
            PointOption startPointPinNumber = new PointOption<>().withCoordinates(xPinNumber, yPinNumber);
            TouchAction touchActionPin = new TouchAction(appiumDriver);
            touchActionPin
                    .press(startPointPinNumber)
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                    .release()
                    .perform();
            MobileElement pinNumberInputElem
                    = appiumDriver.findElement(MobileBy.xpath("(//android.widget.LinearLayout[@resource-id=\"com.viettel.vtt.vn.emoneycustomer.dev:id/lnLoginSignup\"])[2]"));
            pinNumberInputElem.sendKeys("040100");
            Thread.sleep(1000);

//            //Interact with Login form | fill phone number and OTP
//            getOTPBtnElem.click();
//            Thread.sleep(7000);
//
//            MobileElement AutoFillOTP
//                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.Button[@resource-id=\"com.google.android.gms:id/positive_button\"]"));
//            AutoFillOTP.click();
//            Thread.sleep(7000);
//
//            loginBtnElem.click();
//            Thread.sleep(3000);
//
//            //Logged in and in tab Metfone+
//            MobileElement ClickMetfoneIcon
//                    = appiumDriver.findElement(MobileBy.xpath("(//android.widget.FrameLayout[@resource-id=\"com.metfone.selfcare:id/fixed_bottom_navigation_container\"])[3]"));
//            ClickMetfoneIcon.click();
//            Thread.sleep(3000);
//
//            MobileElement ClickTabMobile
//                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.TextView[@text=\"Mobile\"]"));
//            ClickTabMobile.click();

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
