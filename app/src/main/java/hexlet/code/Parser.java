package hexlet.code;

import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

public class Parser {
    public static Map<String, String> jsonFileMapper(String fileData) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> mapFile = mapper.readValue(fileData,
            new TypeReference<Map<String, String>>() { });
        return mapFile;
    }

    public static Map<String, String> yamlFileMapper(String fileData) throws Exception {
        YAMLMapper mapper = new YAMLMapper();
        Map<String, String> mapFile = mapper.readValue(fileData,
            new TypeReference<Map<String, String>>() { });
        return mapFile;
    }
}
