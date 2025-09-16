package src.driverForHiFPT;

import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class getElement {
    public static MobileElement getElement(WebDriverWait wait, String xpath) {
        try {
            return (MobileElement) wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(xpath))
            );
        } catch (Exception e) {
            return null;
        }
    }
}
