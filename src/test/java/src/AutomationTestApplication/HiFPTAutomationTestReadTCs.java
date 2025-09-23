package src.AutomationTestApplication;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import src.Handle.*;
import src.driverForHiFPT.DriverFactoryForHiFPT;
import src.driverForHiFPT.Platform;

import java.net.MalformedURLException;

import static src.Handle.ReadTestcaseByFileYaml.readTestDataFromYaml;

public class HiFPTAutomationTestReadTCs {

    public static void main(String[] args) throws MalformedURLException {

        AppiumDriver<MobileElement> appiumDriver = DriverFactoryForHiFPT.getDriver(Platform.ANDROID);
        WebDriverWait wait = new WebDriverWait(appiumDriver, 10);

        try {
            System.out.println("=====Start running Automation Test=====");

            // Xử lý đăng nhập
            HandleLogin.LoginScreen(appiumDriver);

            // Kiểm tra popup thông báo
            HandlePopupNotification.handlePopUpNotification(appiumDriver);

            // Handle popup Lost Connection
            HandleLostConnection.handleLostConnection(appiumDriver);

            // Handle swipe up and down
            SwipeUpAndDown.scrollUp(appiumDriver,1);

            // Handle click banner
            HandleClickBanner.ClickBanner(appiumDriver,4);

            // Handle swipe banner
            HandleClickBanner.swipeBanner(appiumDriver);

            // Đọc file YAML & thực thi test cases
            String fileYAML = "/Users/baymax/test.yaml"; //Remember to change
            if (Webview.switchToWebView(appiumDriver,10, "")){
                readTestDataFromYaml(fileYAML, appiumDriver);
            }

            // Stay in app 6s before stop process
            Thread.sleep(6000);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            appiumDriver.quit();
        }
    }
}