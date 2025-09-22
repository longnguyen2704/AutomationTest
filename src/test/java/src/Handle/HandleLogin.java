package src.Handle;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

import static src.driverForHiFPT.getElements.getElement;

public class HandleLogin {
    public static void LoginScreen(AppiumDriver<MobileElement> driver) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        try {
            // Nhập số điện thoại
            MobileElement loginScreenOTP = (MobileElement) wait.until(
                    ExpectedConditions.presenceOfElementLocated(MobileBy.xpath("//android.widget.EditText"))
            );
            if (loginScreenOTP.isDisplayed()) {
                loginScreenOTP.sendKeys("0908418782");
                System.out.println("✅ Input phone number successfully");
            }

            // Click "Tiếp tục"
            MobileElement buttonContinue = (MobileElement) wait.until(
                    ExpectedConditions.presenceOfElementLocated(MobileBy.xpath("//android.widget.TextView[@text=\"Tiếp tục\"]"))
            );
            if (buttonContinue.isDisplayed() || buttonContinue.isEnabled()) {
                buttonContinue.click();
                System.out.println("✅ Click button continue success");
            }

            // Popup "Khóa đăng nhập"
            MobileElement popupBlockSignUp = getElement(driver, "//android.widget.TextView[@text=\"Khóa đăng nhập\"]");
            if (popupBlockSignUp != null && popupBlockSignUp.isDisplayed()) {
                MobileElement clickClose = getElement(driver, "//android.widget.TextView[@text=\"Đóng\"]");
                if (clickClose != null) clickClose.click();
                System.out.println("❌ Login unsuccessfully because system is showing error popup");
                System.exit(1);
                return;
            }

            // Kiểm tra bàn phím ảo
            MobileElement showKeyboard = getElement(driver, "android.view.View");
            if (showKeyboard != null && !showKeyboard.isDisplayed()) {
                showKeyboard.click();
            }

            // Nhập PIN
            MobileElement inputPIN = getElement(driver, "//android.widget.TextView[@text=\"Nhập mã PIN\"]");
            if (inputPIN != null) {
                inputPIN.click();
                String PIN = "123456";
                for (char digit : PIN.toCharArray()) {
                    Runtime.getRuntime().exec("adb shell input text " + digit);
                    Thread.sleep(1000);
                }
                System.out.println("✅ Input PIN successfully");
            }

            // Nhập OTP
            MobileElement inputOTP = getElement(driver, "//android.widget.TextView[@text=\"Mã OTP vừa được gửi đến số điện thoại\"]");
            if (inputOTP != null && inputOTP.isDisplayed()) {
                inputOTP.click();
                String OTP = "1309";
                for (char digit : OTP.toCharArray()) {
                    Runtime.getRuntime().exec("adb shell input text " + digit);
                    Thread.sleep(1000);
                }
                System.out.println("✅ Input OTP successfully");
            }

            // Popup force update
            MobileElement forceUpdate = getElement(driver, "//android.widget.TextView[@text=\"Không nhắc lại\"]");
            if (forceUpdate != null && forceUpdate.isDisplayed()) {
                forceUpdate.click();
                System.out.println("✅ Close popup force update");
            }

            // Popup system error
            MobileElement systemError = getElement(driver, "//android.widget.TextView[@text=\"Chưa hiển thị được thông tin, vui lòng thử lại sau.\"]");
            if (systemError != null && systemError.isDisplayed()) {
                System.exit(1);
            }

        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        Thread.sleep(4000);
        System.out.println("✅ Welcome to Hi FPT!!!");
    }
}
