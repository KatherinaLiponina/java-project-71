package hexlet.code;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Differ {

    public static final class Pair {
        private String first;
        private String second;

        public String getFirst() {
            return first;
        }
        public String getSecond() {
            return second;
        }
        public Pair(String f, String s) {
            first = f;
            second = s;
        }
    }

    public static String generate(String filepath1, String filepath2) throws Exception {
        return generate(filepath1, filepath2, "stylish");
    }

    public static String generate(String filepath1, String filepath2, String format) throws Exception {
        return Formatter.callFormatter(Differ.diff(Parser.callParser(filepath1),
        Parser.callParser(filepath2)), format);
    }

    public static Map<String, Pair> diff(Map<String, String> file1, Map<String, String> file2) throws Exception {
        Iterator<Map.Entry<String, String>> iterator1 = normalize(file1);
        Iterator<Map.Entry<String, String>> iterator2 = normalize(file2);

        boolean flag = iterator1.hasNext() || iterator2.hasNext();
        Map.Entry<String, String> entry1 = nextEntry(iterator1);
        Map.Entry<String, String> entry2 = nextEntry(iterator2);
        Map<String, Pair> differ = new HashMap<>();
        while (flag) {
            flag = iterator1.hasNext() || iterator2.hasNext();
            if (entry1 != null && entry2 != null && entry1.getKey().equals(entry2.getKey())) {
                differ.put(entry1.getKey(), new Pair(entry1.getValue(), entry2.getValue()));
                entry1 = nextEntry(iterator1);
                entry2 = nextEntry(iterator2);
            } else if (entry1 != null && (entry2 == null || entry1.getKey().compareTo(entry2.getKey()) < 0)) {
                differ.put(entry1.getKey(), new Pair(entry1.getValue(), ""));
                entry1 = nextEntry(iterator1);
            } else if (entry2 != null) {
                differ.put(entry2.getKey(), new Pair("", entry2.getValue()));
                entry2 = nextEntry(iterator2);
            } else {
                throw new Exception("Something went really wrong");
            }
        }
        return differ;
    }

    private static Map.Entry<String, String> nextEntry(Iterator<Map.Entry<String, String>> it) {
        return it.hasNext() ? it.next() : null;
    }

    private static Iterator<Map.Entry<String, String>> normalize(Map<String, String> file) {
        Map<String, String> fileSorted = new TreeMap<>(file);
        Set<Map.Entry<String, String>> fileset = fileSorted.entrySet();
        Iterator<Map.Entry<String, String>> iterator = fileset.iterator();
        return iterator;
    }

}
