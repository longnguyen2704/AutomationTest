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


    }
}
