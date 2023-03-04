package hexlet.code;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonToken;

import org.yaml.snakeyaml.Yaml;

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

    public static Map<String, String> yamlFileMapper(String filepath) throws Exception {
        InputStream inputStream = new FileInputStream(new File(filepath));
        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(inputStream);
        Map<String, String> mappa = new HashMap<>();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (entry.getValue() == null) {
                mappa.put(entry.getKey(), "null");
            } else if (entry.getValue().toString().charAt(1) == '{') {
                mappa.put(entry.getKey(), rebracket(entry.getValue().toString()));
            } else {
                mappa.put(entry.getKey(), entry.getValue().toString());
            }
        }
        return mappa;
    }

    private static String rebracket(String str) {
        StringBuilder s = new StringBuilder("{");
        str = str.substring(1, str.length() - 1);
        String[] settings = str.split(",");
        for (String setting : settings) {
            s.append(setting.trim().substring(1, setting.length() - 1) + ", ");
        }
        return s.toString().substring(0, s.length() - ", ".length());
    }
}