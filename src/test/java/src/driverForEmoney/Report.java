package src.driverForEmoney;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.List;
import java.util.Map;

public interface Report {
    static void exportReport(String yamlFilePath, String htmlFilePath) {
        try (InputStream inputStream = new FileInputStream(yamlFilePath);
             BufferedWriter writer = new BufferedWriter(new FileWriter(htmlFilePath))) {
            Yaml yaml = new Yaml();
            Object yamlObject = yaml.load(inputStream);
            if (yamlObject instanceof Map) {
                Map<String, List<Map<String, String>>> testData = (Map<String, List<Map<String, String>>>) yamlObject;
                writer.write("<html><head><title>Test Report</title></head><body>");

                for (Map.Entry<String, List<Map<String, String>>> entry : testData.entrySet()) {
                    String testCaseName = entry.getKey();
                    List<Map<String, String>> testSteps = entry.getValue();

                    writer.write("<h2>" + testCaseName + "</h2>");
                    writer.write("<table border='1'>");
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
                        String result = (!action.isEmpty() || !ID.isEmpty() || !inputData.isEmpty() || !coordinates.isEmpty()) ? "Success" : "Fail";

                        writer.write("<tr>");
                        writer.write("<td>" + action + "</td>");
                        writer.write("<td>" + ID + "</td>");
                        writer.write("<td>" + inputData + "</td>");
                        writer.write("<td>" + coordinates + "</td>");
                        writer.write("<td>" + result + "</td>");
                        writer.write("</tr>");
                    }

                    writer.write("</table>");
                }

                writer.write("</body></html>");
                System.out.println("Report exported successfully to " + htmlFilePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
