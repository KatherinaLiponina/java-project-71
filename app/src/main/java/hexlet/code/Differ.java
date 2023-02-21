package hexlet.code;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Differ {
    public static String diff(Map<String, String> file1, Map<String, String> file2) {
        StringBuilder diffString = new StringBuilder("{\n");

        Map<String, String> file1Sorted = new TreeMap<>(file1);
        Map<String, String> file2Sorted = new TreeMap<>(file2);
        Set<Map.Entry<String, String>> file1set = file1Sorted.entrySet();
        Set<Map.Entry<String, String>> file2set = file2Sorted.entrySet();
        Iterator<Map.Entry<String, String>> iterator1 = file1set.iterator();
        Iterator<Map.Entry<String, String>> iterator2 = file2set.iterator();

        Map.Entry<String, String> entry1 = null;
        Map.Entry<String, String> entry2 = null;
        boolean flag = iterator1.hasNext() || iterator2.hasNext();
        while (flag) {
            flag = iterator1.hasNext() || iterator2.hasNext();

            if (entry1 != null && entry2 != null
                && entry1.getKey().equals(entry2.getKey())) {
                if (entry1.getValue().equals(entry2.getValue())) {
                    diffString.append("    " + entry1.getKey() + ": " + entry1.getValue() + "\n");
                } else {
                    diffString.append("  - " + entry1.getKey() + ": " + entry1.getValue() + "\n");
                    diffString.append("  + " + entry2.getKey() + ": " + entry2.getValue() + "\n");
                }
                entry1 = iterator1.hasNext() ? iterator1.next() : null;
                entry2 = iterator2.hasNext() ? iterator2.next() : null;
            } else if (entry1 != null && (entry2 == null || entry1.getKey().compareTo(entry2.getKey()) < 0)) {
                diffString.append("  - " + entry1.getKey() + ": " + entry1.getValue() + "\n");
                entry1 = iterator1.hasNext() ? iterator1.next() : null;
            } else if (entry2 != null && (entry1 == null || entry1.getKey().compareTo(entry2.getKey()) > 0)) {
                diffString.append("  + " + entry2.getKey() + ": " + entry2.getValue() + "\n");
                entry2 = iterator2.hasNext() ? iterator2.next() : null;
            } else {
                entry1 = iterator1.hasNext() ? iterator1.next() : null;
                entry2 = iterator2.hasNext() ? iterator2.next() : null;
            }

        }
        diffString.append("}");

        return diffString.toString();
    }
}
