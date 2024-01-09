package src.ReadTestcase;

import org.yaml.snakeyaml.Yaml;
import src.driverForEmoney.ElementType;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReadTCsByYAML {
    public static List<ElementType> handleGetTestcaseYaml(String yamlFilePath) {
        List<ElementType> arrElm = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(yamlFilePath)) {
            Yaml yaml = new Yaml();
            Map<String, List<Map<String, String>>> testData = yaml.load(inputStream);
            for (Map.Entry<String, List<Map<String, String>>> entry : testData.entrySet()) {
                List<Map<String, String>> testSteps = entry.getValue();
                for (Map<String, String> step : testSteps) {
                    String action = step.get("action");
                    String ID = step.get("ID");
                    String inputData = step.get("inputData");
                    ElementType newObj = new ElementType();
                    arrElm.add(newObj);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrElm;
    }

    public static void main(String[] args) {
        // Đường dẫn đến tệp YAML cần đọc
        String filePath = "D:\\test.yaml";

        try (InputStream inputStream = new FileInputStream(filePath)) {
            // Sử dụng thư viện SnakeYAML để đọc tệp YAML
            Yaml yaml = new Yaml();
            // Phương thức load trả về một đối tượng Map đại diện cho cấu trúc YAML
            LinkedHashMap<String, Object> yamlData = yaml.load(inputStream);
            // In ra dữ liệu đọc được từ tệp YAML
            System.out.println(yamlData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
