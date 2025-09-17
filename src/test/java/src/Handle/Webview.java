package src.Handle;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.util.Set;

public class Webview {
    public static void switchToWebView(AppiumDriver<MobileElement> appiumDriver) {
        Set<String> contexts = appiumDriver.getContextHandles();
        System.out.println("👉 Available contexts: " + contexts);

        for (String context : contexts) {
            if (context.toLowerCase().contains("webview")) {
                appiumDriver.context(context);
                System.out.println("✅ Switched to WebView: " + context);
                return;
            }
        }
        System.out.println("⚠️ No WebView context found!");
    }

    public static void switchToNative(AppiumDriver<MobileElement> appiumDriver) {
        Set<String> contexts = appiumDriver.getContextHandles();
        System.out.println("👉 Available contexts: " + contexts);

        for (String context : contexts)
            if (context.toLowerCase().contains("native_app")){
                appiumDriver.context(context);
                System.out.println("✅ Switched to Native app: " + context);
                return;
            }
        System.out.println("⚠️ No Native app context found!");
    }
}
