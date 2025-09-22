package src.Handle;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import static src.driverForHiFPT.getElements.getElement;

public class HandlePopupNotification {
    public static void handlePopUpNotification(AppiumDriver driver) {
        try {
            // Popup bật thông báo
            MobileElement popUp = getElement(driver, "//android.widget.TextView[@text=\"Bật  thông  báo\"]");
            if (popUp != null && popUp.isDisplayed()) {
                MobileElement clickNo = getElement(driver, "//android.widget.TextView[@text=\"Để sau\"]");
                if (clickNo != null) clickNo.click();
                System.out.println("✅ Not allow notification success");
            }
            // Bottom sheet
            MobileElement bottomSheet = getElement(driver,
                    "//android.view.ViewGroup[@resource-id=\"android:id/content\"]/android.view.View/android.view.View/android.view.View[1]");
            if (bottomSheet != null && bottomSheet.isDisplayed()) {
                MobileElement tabOutSide = getElement(driver,
                        "//android.view.ViewGroup[@resource-id=\"android:id/content\"]/android.view.View/android.view.View/android.view.View[2]");
                if (tabOutSide != null) tabOutSide.click();
                System.out.println("✅ Close bottom sheet");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
