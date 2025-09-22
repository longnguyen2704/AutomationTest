package src.Handle;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import java.time.Duration;

public class SwipeUpAndDown {

    // Scroll từ dưới lên trên (scroll up)
    public static void scrollUp(AppiumDriver<MobileElement> appiumDriver, int times) {
        int height = appiumDriver.manage().window().getSize().height;
        int width = appiumDriver.manage().window().getSize().width;

        int startX = width / 2;
        int startY = (int) (height * 0.65);
        int endY = (int) (height * 0.35);

        for (int i = 0; i < times; i++) {
            new TouchAction<>(appiumDriver)
                    .press(PointOption.point(startX, startY))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
                    .moveTo(PointOption.point(startX, endY))
                    .release()
                    .perform();
        }
        System.out.println("✅ Scroll success");
    }
}
