package src.driverForMdealer;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.List;
import java.util.Map;


public interface Report {
    static void exportReport(String yamlFilePath, String htmlFilePath) {
        AppiumDriver<MobileElement> appiumDriver = DriverFactoryForMdealer.getDriver(Platform.ANDROID);
        try (InputStream inputStream = new FileInputStream(yamlFilePath);
             BufferedWriter writer = new BufferedWriter(new FileWriter(htmlFilePath))) {
            Yaml yaml = new Yaml();
            Object yamlObject = yaml.load(inputStream);
            if (yamlObject instanceof Map) {
                Map<String, List<Map<String, String>>> testData = (Map<String, List<Map<String, String>>>) yamlObject;

                writer.write("<html>");
                writer.write("<head>");
                writer.write("<title>Test Report</title>");
                writer.write("<style>");
                writer.write("table {border-collapse: collapse; width: 100%; table-layout: fixed;}");
                writer.write("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px; width: 20%; word-wrap: break-word; white-space: pre-wrap;}");
                writer.write("tr:nth-child(even) {background-color: #f2f2f2;}");
                writer.write("h2 {color: #333333;}");
                writer.write("</style>");
                writer.write("</head>");
                writer.write("<body>");

                for (Map.Entry<String, List<Map<String, String>>> entry : testData.entrySet()) {
                    String testCaseName = entry.getKey();
                    List<Map<String, String>> testSteps = entry.getValue();
                    String result = "Null";

                    writer.write("<h2>" + "Case: " + testCaseName + "</h2>");
                    writer.write("<table>");
                    writer.write("<tr>");
                    writer.write("<th>Action</th>");
                    writer.write("<th>ID</th>");
                    writer.write("<th>Input Data</th>");
                    writer.write("<th>Coordinates</th>");
                    writer.write("<th>Result</th>");
                    writer.write("</tr>");

                    for (Map<String, String> step : testSteps) {
                        String action = step.get("action");
                        String ID = step.get("ID");
                        String inputData = step.get("inputData");
                        String coordinates = step.get("coordinates");



                        writer.write("<tr>");
                        writer.write("<td>" + action + "</td>");
                        writer.write("<td>" + ID + "</td>");
                        writer.write("<td>" + inputData + "</td>");
                        writer.write("<td>" + coordinates + "</td>");
                        writer.write("<td>" + result + "</td>");
                        writer.write("</tr>");
                    }
                }
                writer.write("</body>");
                writer.write("</html>");
                System.out.println("Report exported successfully to " + htmlFilePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
