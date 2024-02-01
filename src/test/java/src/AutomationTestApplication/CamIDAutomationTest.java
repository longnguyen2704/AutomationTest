package src.AutomationTestApplication;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import src.driverForCamID.DriverFactoryForCamID;
import src.driverForCamID.Platform;

public class CamIDAutomationTest {
    public static void main(String[] args) {
        AppiumDriver<MobileElement> appiumDriver
                = DriverFactoryForCamID.getDriver(Platform.ANDROID);

        try {
            //Navigate to Login screen
            MobileElement SkipBtnElemLuckySpinGuideLine
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.TextView[@resource-id=\"com.metfone.selfcare:id/tvSkip\"]"));
            SkipBtnElemLuckySpinGuideLine.click();
            Thread.sleep(3000);

            MobileElement AllowNotiBtn
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_button\"]"));
            AllowNotiBtn.click();
            Thread.sleep(3000);

            MobileElement ClickLoginBtnElem
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.TextView[@text=\"Click here to login\"]"));
            ClickLoginBtnElem.click();

            //Find Login form elements
            MobileElement phoneNumberInputElem
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.EditText[@resource-id=\"com.metfone.selfcare:id/etNumber\"]"));

            MobileElement getOTPBtnElem
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.TextView[@resource-id=\"com.metfone.selfcare:id/btnLogin\"]"));

            MobileElement loginBtnElem
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.TextView[@resource-id=\"com.metfone.selfcare:id/btnLogin\"]"));

            //Interact with Login form | fill phone number and OTP
            phoneNumberInputElem.sendKeys("0315445204");
            getOTPBtnElem.click();
            Thread.sleep(7000);

            MobileElement AutoFillOTP
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.Button[@resource-id=\"com.google.android.gms:id/positive_button\"]"));
            AutoFillOTP.click();
            Thread.sleep(7000);

            loginBtnElem.click();
            Thread.sleep(3000);

            //Logged in and in tab Metfone+
            MobileElement ClickMetfoneIcon
                    = appiumDriver.findElement(MobileBy.xpath("(//android.widget.FrameLayout[@resource-id=\"com.metfone.selfcare:id/fixed_bottom_navigation_container\"])[3]"));
            ClickMetfoneIcon.click();
            Thread.sleep(3000);

            MobileElement ClickTabMobile
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.TextView[@text=\"Mobile\"]"));
            ClickTabMobile.click();

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
