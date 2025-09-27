package src.Handle;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import java.time.Duration;

public class HandleClickBanner {
    // Remember change this path too
    public static final String ClassName =
            "android.widget.ImageView";

    public static void ClickBanner(AppiumDriver<MobileElement> driver, int swipeTimes) {
        try {
            // Swipe đến banner cuối
            for (int i = 0; i < swipeTimes; i++) {
                swipeBanner(driver);
                Thread.sleep(1000); // chờ load banner tiếp theo
            }

            // Lấy lại element mới nhất sau khi swipe
            MobileElement banner = driver.findElement(MobileBy.className(ClassName));
            if (banner != null && banner.isDisplayed()) {
                banner.click();
                System.out.println("✅ Click banner cuối cùng thành công");
                System.out.println("✅ Swipe success");
            } else {
                System.out.println("⚠️ Không tìm thấy banner cuối cùng");
            }
        } catch (Exception e) {
            System.out.println("❌ Lỗi khi click banner cuối cùng: " + e.getMessage());
        }
    }

    public static void swipeBanner(AppiumDriver<MobileElement> driver){
        int height = driver.manage().window().getSize().height;
        int width = driver.manage().window().getSize().width;

        int startX = (int) (width * 0.2);
        int endX = (int) (width * 0.8);
        int y = height / 2;

        new TouchAction<>(driver)
                .press(PointOption.point(startX, y))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(600)))
                .moveTo(PointOption.point(endX, y))
                .release()
                .perform();
    }
}
