package hexlet.code.formatters;

import java.util.Map;

import hexlet.code.Differ.Pair;

public class Json {
    public static String formatJson(Map<String, Pair> mappa) throws Exception {
        StringBuilder str = new StringBuilder("{\n");
        for (Map.Entry<String, Pair> entry : mappa.entrySet()) {
            str.append("  \"" + entry.getKey() + "\": {\n    ");
            if (entry.getValue().getFirst().equals("")) {
                str.append("\"added\": " + entry.getValue().getSecond() + "\n  }\n");
            } else if (entry.getValue().getSecond().equals("")) {
                str.append("\"removed\": " + entry.getValue().getFirst() + "\n  }\n");
            } else {
                str.append("\"replaced\": " + entry.getValue().getFirst()
                    + ",\n    \"new value\": " + entry.getValue().getSecond() + "\n  }\n");
            }
        }
        return str.append("}").toString();
    }
}
