package src.Handle;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import static src.Handle.ReadTestcaseByFileYaml.getElement;

public class HandleLostConnection {
    public static void handleLostConnection(AppiumDriver<MobileElement> driver) {
        MobileElement lostConnection =
                getElement(driver, "//android.widget.TextView[@text=\"Mất kết nối Internet\"]");

        if (lostConnection != null && lostConnection.isDisplayed()) {
            MobileElement buttonClose =
                    getElement(driver, "//android.widget.TextView[@text=\"Đóng\"]");

            if (buttonClose != null && buttonClose.isDisplayed()) {
                buttonClose.click();
            }

            System.out.println("❌ Lost connection");
            System.exit(1);
        }
    }
}
