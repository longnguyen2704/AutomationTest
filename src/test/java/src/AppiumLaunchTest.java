package src;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class AppiumLaunchTest {
    public static void main(String[] args) throws InterruptedException {
        AppiumDriver<MobileElement> driver = null;

        // Desired Caps
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("automationName", "uiautomator2");
        desiredCapabilities.setCapability("udid", "R7AWA03KZ0M");
        desiredCapabilities.setCapability("appPackage", "com.metfone.selfcare");
        desiredCapabilities.setCapability("appActivity", "com.metfone.selfcare.activity.HomeActivity");

        // Specify Appium Server URL
        URL appiumServer = null;

        try {
            appiumServer = new URL("http://localhost:4723/wd/hub");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(appiumServer == null){
            throw new RuntimeException("[ERR] Somehow, we couldn't construct Appium server URL");
        }
        driver = new AndroidDriver<MobileElement>(appiumServer, desiredCapabilities);

        // Debug purpose only
        Thread.sleep(3000);
        driver.quit();
    }
}
