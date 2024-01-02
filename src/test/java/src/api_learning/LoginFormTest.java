package src.api_learning;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import src.driver.DriverFactory;
import src.driver.Platform;

public class LoginFormTest {
    public static void main(String[] args) {
        AppiumDriver<MobileElement> appiumDriver = DriverFactory.getDriver(Platform.ANDROID);

        try {
            //Navigate to Login screen
            MobileElement ClickLoginBtnElem = appiumDriver.findElement(MobileBy.xpath("//android.widget.TextView[@text=\"Click here to login\"]"));
            ClickLoginBtnElem.click();

            //Find Login form elements
            MobileElement phoneNumberInputElem = appiumDriver.findElement(MobileBy.xpath("//android.widget.EditText[@resource-id=\"com.metfone.selfcare:id/etNumber\"]"));

            MobileElement getOTPBtnElem = appiumDriver.findElement(MobileBy.xpath("//android.widget.TextView[@resource-id=\"com.metfone.selfcare:id/btnLogin\"]"));

            MobileElement OTPInputElem = appiumDriver.findElement(MobileBy.xpath("//android.widget.EditText[@resource-id=\"com.metfone.selfcare:id/etOTP\"]"));

            MobileElement loginBtnElem = appiumDriver.findElement(MobileBy.xpath("//android.widget.TextView[@resource-id=\"com.metfone.selfcare:id/btnLogin\"]"));

            //Interact with Login form | fill phone number and OTP
            phoneNumberInputElem.sendKeys("0315445204");
            getOTPBtnElem.click();
            Thread.sleep(7000);

            MobileElement notAutoFillOTP = appiumDriver.findElement(MobileBy.xpath("//android.widget.Button[@resource-id=\"com.google.android.gms:id/negative_button\"]"));
            notAutoFillOTP.click();
            Thread.sleep(7000);

            OTPInputElem.sendKeys("020990");

            loginBtnElem.click();
            Thread.sleep(5000);


            //Verification

            //Print the dialog content

            //Debug purpose ONLY
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        appiumDriver.quit();
    }
}
