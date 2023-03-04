package hexlet.code;

import java.util.Map;
import java.util.TreeMap;

import hexlet.code.Differ.Pair;
import hexlet.code.formatters.Stylish;
import hexlet.code.formatters.Plain;

public class Formatter {

    public static String callFormatter(Map<String, Pair> differ, String format) throws Exception {
        differ = new TreeMap<>(differ);
        if (format.equals("stylish")) {
            return Stylish.formatStylish(differ);
        } else if (format.equals("plain")) {
            return Plain.formatPlain(differ);
        } else {
            throw new Exception("Unrecognized printing format");
        }
    }
}
