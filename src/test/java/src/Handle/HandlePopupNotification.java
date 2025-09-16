package src.Handle;

import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static src.driverForHiFPT.getElement.getElement;

public class HandlePopupNotification {
    public static void handlePopUpNotification(WebDriverWait wait) {
        try {
            MobileElement popUp = getElement(wait, "//android.widget.TextView[@text=\"Bật  thông  báo\"]");
            if (popUp != null && popUp.isDisplayed()) {
                MobileElement clickNo = getElement(wait, "//android.widget.TextView[@text=\"Để sau\"]");
                if (clickNo != null) clickNo.click();
                System.out.println("✅ Not allow notification success");
            }
        } catch (Exception ignored) {
        }
        try {
            MobileElement bottomSheet =
                    getElement(wait, "//android.view.ViewGroup[@resource-id=\"android:id/content\"]/android.view.View/android.view.View/android.view.View[1]");
            if (bottomSheet != null && bottomSheet.isDisplayed()) {
                MobileElement tabOutSide =
                        getElement(wait, "//android.view.ViewGroup[@resource-id=\"android:id/content\"]/android.view.View/android.view.View/android.view.View[2]");
                if (tabOutSide != null) tabOutSide.click();
                System.out.println("✅ Close bottom sheet");
            }
        } catch (Exception ignored) {
        }
        System.out.println("✅ Welcome to Hi FPT!!!");
    }
}
