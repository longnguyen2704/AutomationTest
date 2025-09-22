package src.driverForHiFPT;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactoryForHiFPT implements MobileCapabilityTypeEx {
    public static AppiumDriver<MobileElement> getDriver(Platform platform) throws MalformedURLException {
        AppiumDriver<MobileElement> driver;
        // Desired Caps
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
        switch (platform) {
            case ANDROID -> driver = new AndroidDriver<>(appiumServer, desiredCapabilities);
            case IOS -> driver = new IOSDriver<>(appiumServer, desiredCapabilities);
            default -> throw new IllegalArgumentException("Platform type can't be null");
        }
        return driver;
    }
}
