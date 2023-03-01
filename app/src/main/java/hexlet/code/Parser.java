package hexlet.code;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonToken;

public class Parser {
    public static Map<String, String> jsonFileMapper(String fileData) throws Exception {
        JsonParser jsonParser = new JsonFactory().createParser(fileData);
        Map<String, String> mapFile = new HashMap<>();
        jsonParser.nextToken(); //for {
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String name = jsonParser.getCurrentName();
            String value = "";
            JsonToken token = jsonParser.nextToken();
            if (token == JsonToken.START_ARRAY) {
                value = "[";
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    value += jsonParser.getText() + ", ";
                }
                value = value.substring(0, value.length() - ", ".length());
                value += "]";
            } else if (token == JsonToken.START_OBJECT) {
                value = "{";
                while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                    String key = jsonParser.getCurrentName();
                    jsonParser.nextToken();
                    String val = jsonParser.getText();
                    value += key + "=" + val + ", ";
                }
                value = value.substring(0, value.length() - ", ".length());
                value += "}";
            } else {
                value = jsonParser.getText();
            }
            mapFile.put(name, value);
        }
        return mapFile;
    }

    public static Map<String, String> yamlFileMapper(String fileData) throws Exception {
        YAMLMapper mapper = new YAMLMapper();
        Map<String, String> mapFile = mapper.readValue(fileData,
            new TypeReference<Map<String, String>>() { });
        return mapFile;
    }
}
