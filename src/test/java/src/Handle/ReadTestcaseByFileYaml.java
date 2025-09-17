package src.Handle;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static src.AutomationTestApplication.HiFPTAutomationTestReadTCs.performAction;
import static src.driverForHiFPT.getElement.getElement;

public class ReadTestcaseByFileYaml {
    public static void readTestDataFromYaml(String yamlFilePath, AppiumDriver<MobileElement> appiumDriver, WebDriverWait wait) {
        boolean allTestsPassed = true;
//        boolean isModemError = false;

        try (InputStream inputStream = new FileInputStream(yamlFilePath)) {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);

            List<Map<String, Object>> testCases = (List<Map<String, Object>>) data.get("testcases");

            for (Map<String, Object> tc : testCases) {
                String testCaseName = (String) tc.getOrDefault("name", "");
                String action = (String) tc.getOrDefault("action", "");
                String ID = (String) tc.getOrDefault("id", "");
                String coordinates = (String) tc.getOrDefault("coordinates", "");
                String inputData = (String) tc.getOrDefault("input", "");

                if (testCaseName.trim().isEmpty() || action.trim().isEmpty()) {
                    System.out.println("⚠️ Invalid test case format: " + tc);
                    continue;
                }

                Thread.sleep(1500);
                System.out.println("⮑ Running case: " + testCaseName);

                boolean resultOfTest = performAction(appiumDriver, action, ID, inputData, coordinates);
//
//                // Kiểm tra popup lỗi (chỉ một lần)
//                if (!isModemError) {
//                    MobileElement popUpNotHaveInfoModem = getElement(wait, "//android.widget.TextView[@text=\"Mất kết nối với Modem\"]");
//                    if (popUpNotHaveInfoModem != null && popUpNotHaveInfoModem.isDisplayed()) {
//                        MobileElement clickClose = getElement(wait, "//android.widget.TextView[@text=\"Đóng\"]");
//                        if (clickClose != null) clickClose.click();
//                        System.out.println("⚠️ Vui lòng đổi sang Hợp đồng khác vì Hợp đồng này không có thông tin Modem");
//                        Thread.sleep(6000);
//                        isModemError = true;
//                        System.exit(1);
//                    }
//
//                    MobileElement popupSystemError = getElement(wait, "//android.widget.TextView[@text=\"Chưa hiển thị được thông tin, vui lòng thử lại sau.\"]");
//                    if (popupSystemError != null && popupSystemError.isDisplayed()) {
//                        MobileElement clickClose = getElement(wait, "//android.widget.TextView[@text=\"Đóng\"]");
//                        if (clickClose != null) clickClose.click();
//                        System.out.println("⚠️ Chưa hiển thị được thông tin");
//                        Thread.sleep(6000);
//                        isModemError = true;
//                        System.exit(1);
//                    }
//
//                    MobileElement popupNotHaveInfo = getElement(wait, "//android.widget.TextView[@text=\"Chưa có thông tin\"]");
//                    if (popupNotHaveInfo != null && popupNotHaveInfo.isDisplayed()) {
//                        MobileElement clickClose = getElement(wait, "//android.widget.TextView[@text=\"Đóng\"]");
//                        if (clickClose != null) clickClose.click();
//                        System.out.println("⚠️ Chưa có thông tin Modem");
//                        Thread.sleep(6000);
//                        isModemError = true;
//                        System.exit(1);
//                    }
//                }

                // Ghi nhận kết quả
                if (!resultOfTest) {
                    allTestsPassed = false;
                    System.out.println("❌ Test case failed: " + testCaseName);
                }
            }

            System.out.println(allTestsPassed ? "✅ PASS - All test cases have been run" : "❌ FAIL - Some test cases failed");

        } catch (Exception e) {
            System.err.println("❌ Error reading YAML test data: " + e.getMessage());
        }
    }
}
