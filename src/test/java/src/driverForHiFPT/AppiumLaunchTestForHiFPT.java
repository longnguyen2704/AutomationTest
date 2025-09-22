package src.driverForHiFPT;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class AppiumLaunchTestForHiFPT implements MobileCapabilityTypeEx {
    public static void main(String[] args) throws InterruptedException, MalformedURLException {
        AppiumDriver<MobileElement> driver;
        //Desired caps
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(PLATFORM_NAME, "Android");
        desiredCapabilities.setCapability(AUTOMATION_NAME, "uiautomator2");
        desiredCapabilities.setCapability(UDID, "R7AWA03KZ0M");
        desiredCapabilities.setCapability(APP_PACKAGE, "com.rad.hifpt");
        desiredCapabilities.setCapability(APP_ACTIVITY, "com.rad.hifpt.activities.MainAppActivity");
        desiredCapabilities.setCapability("chromedriverAutodownload", true);
        desiredCapabilities.setCapability("chromedriverExecutable","/Users/baymax/.cache/selenium/chromedriver/mac-arm64/140.0.7339.82/chromedriver");

        // Specify Appium Server URL
        URL appiumServer = new URL("http://0.0.0.0:4723/wd/hub");

        try {
            appiumServer = new URL("http://0.0.0.0:4723/wd/hub");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (appiumServer == null) {
            throw new RuntimeException("[ERR] Somehow, we couldn't construct Appium server URL");
        }
        driver = new AndroidDriver<>(appiumServer, desiredCapabilities);

        // Debug purpose only
        Thread.sleep(3000);
        driver.quit();
    }
}
