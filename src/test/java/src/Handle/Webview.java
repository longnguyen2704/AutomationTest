package src.Handle;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import java.util.Optional;
import java.util.Set;

public class Webview {

    /**
     * Chuyển sang WebView.
     *
     * @param appiumDriver     AppiumDriver
     * @param timeoutSeconds   thời gian chờ (giây)
     * @param preferredPackage nếu != null sẽ ưu tiên chọn WEBVIEW chứa chuỗi này (ví dụ "com.my.app")
     * @return true nếu switch thành công, false nếu không tìm thấy WebView trong timeout
     */
    public static boolean switchToWebView(AppiumDriver<MobileElement> appiumDriver, int timeoutSeconds, String preferredPackage) {
        long end = System.currentTimeMillis() + timeoutSeconds * 1000L;

        while (System.currentTimeMillis() < end) {
            Set<String> contexts = appiumDriver.getContextHandles();
            System.out.println("👉 Available contexts: " + contexts);

            Optional<String> webview = contexts.stream()
                    .filter(c -> c != null && c.toUpperCase().contains("WEBVIEW"))
                    .filter(c -> preferredPackage == null || c.toLowerCase().contains(preferredPackage.toLowerCase()))
                    .findFirst();

            if (!webview.isPresent()) {
                webview = contexts.stream()
                        .filter(c -> c != null && c.toUpperCase().contains("WEBVIEW"))
                        .findFirst();
            }

            if (webview.isPresent()) {
                appiumDriver.context(webview.get());
                System.out.println("✅ Switched to WebView: " + appiumDriver.getContext());

                // 👉 Chờ thêm 2-3s sau khi switch context
                try {
                    Thread.sleep(2500); // 2.5s
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                return true;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println("⚠️ No WebView context found after " + timeoutSeconds + "s");
        return false;
    }

    public static boolean switchToNative(AppiumDriver<MobileElement> appiumDriver, int timeoutSeconds) {
        long end = System.currentTimeMillis() + timeoutSeconds * 1000L;

        while (System.currentTimeMillis() < end) {
            Set<String> contexts = appiumDriver.getContextHandles();
            System.out.println("👉 Available contexts: " + contexts);

            Optional<String> nativeCtx = contexts.stream()
                    .filter(c -> c != null && c.equalsIgnoreCase("NATIVE_APP"))
                    .findFirst();

            if (nativeCtx.isPresent()) {
                appiumDriver.context(nativeCtx.get());
                System.out.println("✅ Switched to Native app: " + appiumDriver.getContext());
                return true;
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println("⚠️ No Native app context found after " + timeoutSeconds + "s");
        return false;
    }
}
