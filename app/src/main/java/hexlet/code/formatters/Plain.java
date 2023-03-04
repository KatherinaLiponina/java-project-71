package hexlet.code.formatters;

import java.util.Map;
import java.util.regex.Pattern;

import hexlet.code.Differ.Pair;

public class Plain {
    public static String formatPlain(Map<String, Pair> mappa) {
        StringBuilder str = new StringBuilder("");
        for (Map.Entry<String, Pair> entry : mappa.entrySet()) {
            if (entry.getValue().getFirst().equals(entry.getValue().getSecond())) {
                continue;
            } else {
                str.append("Property '" + entry.getKey());
                if (!entry.getValue().getFirst().equals("") && !entry.getValue().getSecond().equals("")) {
                    str.append("' was updated. From " + checkValue(entry.getValue()
                        .getFirst()) + " to " + checkValue(entry.getValue().getSecond()) + "\n");
                } else if (!entry.getValue().getSecond().equals("")) {
                    str.append("' was added with value: " + checkValue(entry.getValue().getSecond()) + "\n");
                } else {
                    str.append("'  was removed\n");
                }
            }
        }
        return str.toString().substring(0, str.length() - 1);
    }

    private static String checkValue(String str) {
        final String complexValue = "[complex value]";
        if (str.charAt(0) == '[' || str.charAt(0) == '{') {
            return complexValue;
        }
        if (!str.equals("null") && !str.equals("true") && !str.equals("false") && !isNumeric(str)) {
            return "'" + str + "'";
        }
        return str;
    }

    private static Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    private static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
}
