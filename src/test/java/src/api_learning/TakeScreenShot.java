package src.api_learning;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

public class TakeScreenShot {

    static void takeScreenshot(AppiumDriver<MobileElement> appiumDriver, String fileName) {
        // Check if the AppiumDriver supports screenshots
        if (appiumDriver instanceof TakesScreenshot) {
            // Convert the AppiumDriver instance to TakesScreenshot
            TakesScreenshot screenshotDriver = (TakesScreenshot) appiumDriver;
            // Capture the screenshot as a file
            File screenshot = screenshotDriver.getScreenshotAs(OutputType.FILE);
            // Specify the destination file path
            File destinationFile = new File(fileName);
            try {
                File screenshotFile = ((TakesScreenshot) appiumDriver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(screenshotFile, new File(fileName));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to capture the final screenshot: " + e.getMessage());
            }
        } else {
            System.out.println("Screenshot not supported for this AppiumDriver");
        }
    }
}
