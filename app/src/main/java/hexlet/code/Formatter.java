package hexlet.code;

import java.util.Map;
import java.util.TreeMap;

import hexlet.code.Differ.Pair;

public class Formatter {
    public static String stylishFormatter(Map<String, Pair> mappa) {
        Map<String, Pair> mappaSorted = new TreeMap<>(mappa);
        StringBuilder str = new StringBuilder("{\n");
        for (Map.Entry<String, Pair> entry : mappaSorted.entrySet()) {
            if (entry.getValue().getFirst().equals(entry.getValue().getSecond())) {
                str.append("    " + entry.getKey() + ": " + entry.getValue().getFirst() + "\n");
            } else {
                if (!entry.getValue().getFirst().equals("")) {
                    str.append("  - " + entry.getKey() + ": " + entry.getValue().getFirst() + "\n");
                }
                if (!entry.getValue().getSecond().equals("")) {
                    str.append("  + " + entry.getKey() + ": " + entry.getValue().getSecond() + "\n");
                }
            }
        }
        str.append("}");
        return str.toString();
    }
}
