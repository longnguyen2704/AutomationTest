package src.driverForEmoney;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class DriverFactoryForEmoney implements MobileCapabilityTypeEx {
    public static AppiumDriver<MobileElement> getDriver(Platform platform) {
        AppiumDriver<MobileElement> driver;

        // Desired Caps
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(PLATFORM_NAME, "Android");
        desiredCapabilities.setCapability(AUTOMATION_NAME, "uiautomator2");
        desiredCapabilities.setCapability(UDID, "R7AWA03KZ0M");
        desiredCapabilities.setCapability(APP_PACKAGE, "com.viettel.vtt.vn.emoneycustomer.dev");
        desiredCapabilities.setCapability(APP_ACTIVITY, "com.viettel.vtt.vn.emoneycustomer.feature.splash.SplashActivity");
        desiredCapabilities.setCapability(NO_RESET, true);
        // Log
        desiredCapabilities.setCapability("autoGrantPermissions", "true");
        desiredCapabilities.setCapability("autoAcceptAlerts", "true");
        desiredCapabilities.setCapability("autoDismissAlerts", "true");


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
        switch (platform) {
            case ANDROID -> driver = new AndroidDriver<>(appiumServer, desiredCapabilities);
            case IOS -> driver = new IOSDriver<>(appiumServer, desiredCapabilities);
            default -> throw new IllegalArgumentException("Platform type can't be null");
        }
        return driver;
    }
}
