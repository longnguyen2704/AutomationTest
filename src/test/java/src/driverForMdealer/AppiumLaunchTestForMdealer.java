package src.driverForMdealer;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class AppiumLaunchTestForMdealer implements MobileCapabilityTypeEx {
    public static void main(String[] args) throws InterruptedException {
        AppiumDriver<MobileElement> driver = null;

        //Desired caps
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(PLATFORM_NAME, "Android");
        desiredCapabilities.setCapability(AUTOMATION_NAME, "uiautomator2");
        desiredCapabilities.setCapability(UDID, "R7AWA03KZ0M");
        desiredCapabilities.setCapability(APP_PACKAGE, "com.metfone.mdealer");
        desiredCapabilities.setCapability(APP_ACTIVITY, "com.metfone.mdealer.login.Login");
        desiredCapabilities.setCapability(NO_RESET, true);

        // Specify Appium Server URL
        URL appiumServer = null;

        try {
            appiumServer = new URL("http://localhost:4723");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (appiumServer == null) {
            throw new RuntimeException("[ERR] Somehow, we couldn't construct Appium server URL");
        }
        driver = new AndroidDriver<MobileElement>(appiumServer, desiredCapabilities);

        // Debug purpose only
        Thread.sleep(3000);
        driver.quit();
    }
}
