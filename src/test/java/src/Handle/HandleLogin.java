package src.Handle;

import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

import static src.driverForHiFPT.getElement.getElement;

public class HandleLogin {
    public static void LoginScreen (WebDriverWait wait){
        try {
            MobileElement loginScreenOTP = getElement(wait, "//android.widget.EditText");
            if (loginScreenOTP != null) loginScreenOTP.sendKeys("0764543021");
            System.out.println("✅ Input phone number successfully");

            MobileElement buttonContinue = getElement(wait, "//android.widget.TextView[@text=\"Tiếp tục\"]");
            assert buttonContinue != null;
            if (buttonContinue.isDisplayed() || buttonContinue.isEnabled())buttonContinue.click();
            System.out.println("✅ Click success");

            MobileElement PopupBlockSignUp = getElement(wait, "//android.widget.TextView[@text=\"Khóa đăng nhập\"]");
            if (PopupBlockSignUp != null && PopupBlockSignUp.isDisplayed()) {
                MobileElement clickClose = getElement(wait, "//android.widget.TextView[@text=\"Đóng\"]");
                if (clickClose != null) clickClose.click();
                System.out.println("❌ Login unsuccessfully because system is showing error popup");

                // Dừng chương trình ngay lập tức
                System.exit(1);  // Thoát chương trình với mã lỗi 1
                return;
            }

            MobileElement ShowKeyBoard = getElement(wait, "android.view.View");
            if (ShowKeyBoard != null && !ShowKeyBoard.isDisplayed()) {
                ShowKeyBoard.click();
            }

            MobileElement inputPIN = getElement(wait, "//android.widget.TextView[@text=\"Nhập mã PIN\"]");
            if (inputPIN != null) {
                inputPIN.click();
                String PIN = "123456";
                for (char digit : PIN.toCharArray()) {
                    Runtime.getRuntime().exec("adb shell input text " + digit);
                    Thread.sleep(1000);
                }
                System.out.println("✅ Input PIN successfully");
            }
            MobileElement inputOTP = getElement(wait, "//android.widget.TextView[@text=\"Mã OTP vừa được gửi đến số điện thoại\"]");
            if (inputOTP != null && inputOTP.isDisplayed()) {
                inputOTP.click();
                String OTP = "1309";
                for (char digit : OTP.toCharArray()) {
                    Runtime.getRuntime().exec("adb shell input text " + digit);
                    Thread.sleep(1000);
                }
                System.out.println("✅ Input OTP successfully");
            }

            MobileElement popupForceUpdate = getElement(wait, "//android.widget.TextView[@text=\"Không nhắc lại\"]");
            assert popupForceUpdate != null;
            if (popupForceUpdate.isDisplayed())popupForceUpdate.click();
            else {
                System.out.println("No have popup force update");
            }

        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
