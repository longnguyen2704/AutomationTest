package src.Handle;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import static src.Handle.ReadTestcaseByFileYaml.getElement;

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
            // Popup system error
            MobileElement popupSystemError = getElement(driver,
                    "//android.widget.TextView[@text=\"Chưa hiển thị được thông tin, vui lòng thử lại sau.\"]");
            if (popupSystemError != null && popupSystemError.isDisplayed()){
                System.out.println("System error. Pleaser try again!");
                driver.quit();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
