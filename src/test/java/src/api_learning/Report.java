package src.api_learning;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.List;
import java.util.Map;

public class Report {
    public static void exportReport(String yamlFilePath, String reportFilePath) {
        try (InputStream inputStream = new FileInputStream(yamlFilePath);
             BufferedWriter writer = new BufferedWriter(new FileWriter(reportFilePath))) {
            Yaml yaml = new Yaml();
            Object yamlObject = yaml.load(inputStream);
            if (yamlObject instanceof Map) {
                Map<String, List<Map<String, String>>> testData = (Map<String, List<Map<String, String>>>) yamlObject;
                for (Map.Entry<String, List<Map<String, String>>> entry : testData.entrySet()) {
                    String testCaseName = entry.getKey();
                    writer.write("Test Case: " + testCaseName);
                    writer.newLine();
                    List<Map<String, String>> testSteps = entry.getValue();
                    for (Map<String, String> step : testSteps) {
                        String action = step.get("action");
                        String ID = step.get("ID");
                        String inputData = step.get("inputData");
                        String coordinates = step.get("coordinates");
                        String result;

                        // Thực hiện hành động kiểm thử và kiểm tra điều kiện để xác định kết quả
                        if (ID != null) {
                            result = "Success";
                        } else {
                            result = "Fail";
                        }
                        writer.write("Action: " + action);
                        writer.newLine();
                        writer.write("ID: " + ID);
                        writer.newLine();
                        writer.write("Coordinates: " + coordinates);
                        writer.newLine();
                        if (inputData != null) {
                            writer.write("Input Data: " + inputData);
                            writer.newLine();
                        }
                        writer.write("Result: " + result);
                        writer.newLine();
                        writer.newLine();
                    }
                }
                System.out.println("Report exported successfully to " + reportFilePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
