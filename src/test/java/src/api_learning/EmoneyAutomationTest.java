package src.api_learning;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import src.driverForEmoney.DriverFactoryForEmoney;
import src.driverForEmoney.Platform;

import java.io.FileInputStream;
import java.io.InputStream;
import java.time.Duration;

public class EmoneyAutomationTest {
    public static void main(String[] args) {
        AppiumDriver<MobileElement> appiumDriver = DriverFactoryForEmoney.getDriver(Platform.ANDROID);

        try {
            //Screen choose Language
            MobileElement ChooseLangBtn
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.FrameLayout/android.widget.LinearLayout"));
            ChooseLangBtn.click();
            Thread.sleep(1500);
            //Click button [Continue] at screen guideline
            MobileElement ContinueBtn
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.TextView[@resource-id=\"com.viettel.vtt.vn.emoneycustomer.dev:id/btnContinue\"]"));
            ContinueBtn.click();
            Thread.sleep(1000);
            ContinueBtn.click();
            Thread.sleep(1000);
            ContinueBtn.click();
            Thread.sleep(1000);
            //Screen enter phone to verify
            MobileElement PhoneVerify
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.EditText[@resource-id=\"com.viettel.vtt.vn.emoneycustomer.dev:id/et_phone_number\"]"));
            PhoneVerify.sendKeys("0315445204");
            Thread.sleep(1500);

            MobileElement VerifyBtn
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout"));
            VerifyBtn.click();
            Thread.sleep(1500);

            //Click field PIN
            int xPinNumber = 242;
            int yPinNumber = 849;
            PointOption startPointPinNumber = new PointOption<>().withCoordinates(xPinNumber, yPinNumber);
            TouchAction touchActionPin = new TouchAction(appiumDriver);
            touchActionPin
                    .press(startPointPinNumber)
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                    .release()
                    .perform();
            Thread.sleep(2000);

            //No save password from Google
            MobileElement NoSavePassword
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.Button[@resource-id=\"android:id/autofill_dialog_no\"]"));
            NoSavePassword.click();
            Thread.sleep(2000);
            //Input 0
            int x0 = 542;
            int y0 = 2138;
            //Input 4
            int x1 = 193;
            int y1 = 1807;
            //Input 0
            int x2 = 542;
            int y2 = 2138;
            //Input 1
            int x3 = 164;
            int y3 = 1651;
            //Input 0
            int x4 = 542;
            int y4 = 2138;
            //Input 0
            int x5 = 542;
            int y5 = 2138;

            MobileElement pinNumberInputElem
                    = appiumDriver.findElement(MobileBy.xpath("(//android.widget.LinearLayout[@resource-id=\"com.viettel.vtt.vn.emoneycustomer.dev:id/lnLoginSignup\"])[2]"));
            pinNumberInputElem.click();

            PointOption startPoint0 = new PointOption<>().withCoordinates(x0, y0);
            TouchAction touchAction0 = new TouchAction(appiumDriver);
            touchAction0
                    .press(startPoint0)
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                    .release()
                    .perform();

            PointOption startPoint1 = new PointOption<>().withCoordinates(x1, y1);
            TouchAction touchAction1 = new TouchAction(appiumDriver);
            touchAction1
                    .press(startPoint1)
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                    .release()
                    .perform();

            PointOption startPoint2 = new PointOption<>().withCoordinates(x2, y2);
            TouchAction touchAction2 = new TouchAction(appiumDriver);
            touchAction2
                    .press(startPoint2)
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                    .release()
                    .perform();

            PointOption startPoint3 = new PointOption<>().withCoordinates(x3, y3);
            TouchAction touchAction3 = new TouchAction(appiumDriver);
            touchAction3
                    .press(startPoint3)
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                    .release()
                    .perform();

            PointOption startPoint4 = new PointOption<>().withCoordinates(x4, y4);
            TouchAction touchAction4 = new TouchAction(appiumDriver);
            touchAction4
                    .press(startPoint4)
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                    .release()
                    .perform();

            PointOption startPoint5 = new PointOption<>().withCoordinates(x5, y5);
            TouchAction touchAction5 = new TouchAction(appiumDriver);
            touchAction5
                    .press(startPoint5)
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                    .release()
                    .perform();

            //Click login button
            MobileElement ClickLoginBtn
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.Button[@resource-id=\"com.viettel.vtt.vn.emoneycustomer.dev:id/btnLogin\"]"));
            ClickLoginBtn.click();
            Thread.sleep(1500);

            //Enter OTP (hardcode)
            MobileElement EnterOTP
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.EditText[@resource-id=\"android:id/input\"]"));
            EnterOTP.sendKeys("1234");
            Thread.sleep(1500);

            //Click button Continue
            MobileElement ClickContinueBtnOTP
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.TextView[@resource-id=\"com.viettel.vtt.vn.emoneycustomer.dev:id/md_buttonDefaultPositive\"]"));
            ClickContinueBtnOTP.click();
            Thread.sleep(1500);

            //Non-face ID
            MobileElement DisBiometricBtn
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.TextView[@resource-id=\"com.viettel.vtt.vn.emoneycustomer.dev:id/md_buttonDefaultNegative\"]"));
            DisBiometricBtn.click();
            Thread.sleep(1500);

            MobileElement NotAllowNotiBtn
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_deny_button\"]"));
            NotAllowNotiBtn.click();
            Thread.sleep(1500);

            //Click icon Transfer at Home eMoney
            MobileElement TransferFeature
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.ImageView[@resource-id=\"com.viettel.vtt.vn.emoneycustomer.dev:id/imgTransfer\"]"));
            TransferFeature.click();
            Thread.sleep(2500);

            //Find element
            MobileElement TransferEmoney
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.LinearLayout[@resource-id=\"com.viettel.vtt.vn.emoneycustomer.dev:id/layoutTransferEmoney\"]"));
            TransferEmoney.click();
            Thread.sleep(1500);

            MobileElement PhoneReciever
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.EditText[@resource-id=\"com.viettel.vtt.vn.emoneycustomer.dev:id/et_target_information\"]"));
            PhoneReciever.sendKeys("855312594390");
            Thread.sleep(1500);

            MobileElement Amount
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.EditText[@resource-id=\"com.viettel.vtt.vn.emoneycustomer.dev:id/edtContact\" and @text=\"Amount\"]"));
            Amount.sendKeys("10");
            Thread.sleep(1500);

            MobileElement Content
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.EditText[@resource-id=\"com.viettel.vtt.vn.emoneycustomer.dev:id/edtContact\" and @text=\"Content\"]"));
            Content.sendKeys("HI");
            Thread.sleep(1500);

            MobileElement ContinueBtnTransfer
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.Button[@text=\"CONTINUE\"]"));
            ContinueBtnTransfer.click();
            Thread.sleep(1500);

            //Find element
            int x6 = 522;
            int y6 = 2138;
            //Input 4
            int x7 = 194;
            int y7 = 1888;
            //Input 0
            int x8 = 522;
            int y8 = 2138;
            //Input 1
            int x9 = 210;
            int y9 = 1739;
            //Input 0
            int x10 = 522;
            int y10 = 2138;
            //Input 0
            int x11 = 522;
            int y11 = 2138;
            MobileElement EnterPin
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.TextView[@text=\"Enter PIN\"]"));
            EnterPin.click();

            PointOption startPoint6 = new PointOption<>().withCoordinates(x6, y6);
            TouchAction touchAction6 = new TouchAction(appiumDriver);
            touchAction6
                    .press(startPoint6)
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                    .release()
                    .perform();

            PointOption startPoint7 = new PointOption<>().withCoordinates(x7, y7);
            TouchAction touchAction7 = new TouchAction(appiumDriver);
            touchAction7
                    .press(startPoint7)
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                    .release()
                    .perform();

            PointOption startPoint8 = new PointOption<>().withCoordinates(x8, y8);
            TouchAction touchAction8 = new TouchAction(appiumDriver);
            touchAction8
                    .press(startPoint8)
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                    .release()
                    .perform();

            PointOption startPoint9 = new PointOption<>().withCoordinates(x9, y9);
            TouchAction touchAction9 = new TouchAction(appiumDriver);
            touchAction9
                    .press(startPoint9)
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                    .release()
                    .perform();

            PointOption startPoint10 = new PointOption<>().withCoordinates(x10, y10);
            TouchAction touchAction10 = new TouchAction(appiumDriver);
            touchAction10
                    .press(startPoint10)
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                    .release()
                    .perform();

            PointOption startPoint11 = new PointOption<>().withCoordinates(x11, y11);
            TouchAction touchAction11 = new TouchAction(appiumDriver);
            touchAction11
                    .press(startPoint11)
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                    .release()
                    .perform();
            Thread.sleep(1500);

            MobileElement ConfirmBtn
                    = appiumDriver.findElement(MobileBy.xpath("//android.widget.Button[@resource-id=\"com.viettel.vtt.vn.emoneycustomer.dev:id/btnConfirm\"]"));
            ConfirmBtn.click();
            Thread.sleep(2000);

            MobileElement ScrollView
                    = appiumDriver.findElement(MobileBy.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.viettel.vtt.vn.emoneycustomer.dev:id/recyclerViewLayout\"]/android.widget.LinearLayout[6]/android.widget.LinearLayout"));
            ScrollView.click();
            //Verification

            //Print the dialog content

            //Debug purpose ONLY
            Thread.sleep(5000);
            System.out.println("Run success");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        appiumDriver.quit();
    }
}
