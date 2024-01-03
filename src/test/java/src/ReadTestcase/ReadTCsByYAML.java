package src.ReadTestcase;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class ReadTCsByYAML {
    public static void main(String[] args) {
        // Đường dẫn đến tệp YAML cần đọc
        String filePath = "path/to/your/file.yaml";

        try (InputStream inputStream = new FileInputStream(filePath)) {
            // Sử dụng thư viện SnakeYAML để đọc tệp YAML
            Yaml yaml = new Yaml();
            // Phương thức load trả về một đối tượng Map đại diện cho cấu trúc YAML
            Map<String, Object> yamlData = yaml.load(inputStream);

            // In ra dữ liệu đọc được từ tệp YAML
            System.out.println(yamlData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
