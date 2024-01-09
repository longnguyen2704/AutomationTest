package src.driverForEmoney;

public class handleGetSelector {
    public static String getSelectorType(String selector) {
        if (selector.startsWith("//") || selector.startsWith("xpath://") || selector.startsWith("(")) {
            return "xpath";
        } else if (selector.startsWith("class")) {
            return "class name";
        } else if (selector.startsWith("id=")) {
            return "id";
        } else if (selector.startsWith("css=")) {
            return "css selector";
        } else if (selector.startsWith("com.viettel.vtt.vn.emoneycustomer.dev:id/")) {
            return "id";
        } else {
            return "unknown";
        }
    }
}
