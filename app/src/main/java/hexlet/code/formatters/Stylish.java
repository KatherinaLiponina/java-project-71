package hexlet.code.formatters;

import java.util.Map;

import hexlet.code.Differ.Pair;

public class Stylish {
    public static String formatStylish(Map<String, Pair> mappa) {
        StringBuilder str = new StringBuilder("{\n");
        final char unchanged = ' ';
        final char added = '+';
        final char removed = '-';
        for (Map.Entry<String, Pair> entry : mappa.entrySet()) {
            if (entry.getValue().getFirst().equals(entry.getValue().getSecond())) {
                str.append(makeVerdict(entry, unchanged));
                continue;
            }
            if (!entry.getValue().getFirst().equals("")) {
                str.append(makeVerdict(entry, removed));
            }
            if (!entry.getValue().getSecond().equals("")) {
                str.append(makeVerdict(entry, added));
            }
        }
        str.append("}");
        return str.toString();
    }

    private static String makeVerdict(Map.Entry<String, Pair> entry, char res) {
        String value = entry.getValue().getFirst();
        if (res == '+') {
            value = entry.getValue().getSecond();
        }
        return "  " + res + " " + entry.getKey() + ": " + value + "\n";
    }
}
