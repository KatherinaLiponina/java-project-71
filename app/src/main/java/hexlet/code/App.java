package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
         description = "Compares two configuration files and shows a difference.")
public class App implements Callable<Integer> {
    @Parameters(index = "0", description = "path to first file")
    private String filepath1;
    @Parameters(index = "1", description = "path to second file")
    private String filepath2;
    @Option(names = {"-f", "--format"}, description = "output format", defaultValue = "stylish")
    private String format = "stylish";

    @Override
    public Integer call() throws Exception {
        final Path path1 = Paths.get(filepath1);
        final Path path2 = Paths.get(filepath2);
        if (!Files.exists(path1)) {
            System.out.println("File 1 does not exist!");
        } else if (!Files.exists(path2)) {
            System.out.println("File 2 does not exist!");
        }
        String file1 = Files.readString(path1);
        String file2 = Files.readString(path2);

        String result = differ(fileMapper(file1), fileMapper(file2));
        System.out.println(result);

        return 0;
    }

    public static Map<String, String> fileMapper(String fileData) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> mapFile = mapper.readValue(fileData,
            new TypeReference<Map<String, String>>() { });
        return mapFile;
    }

    public static String differ(Map<String, String> file1, Map<String, String> file2) {
        StringBuilder diff = new StringBuilder("{\n");

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
                    diff.append("    " + entry1.getKey() + ": " + entry1.getValue() + "\n");
                } else {
                    diff.append("  - " + entry1.getKey() + ": " + entry1.getValue() + "\n");
                    diff.append("  + " + entry2.getKey() + ": " + entry2.getValue() + "\n");
                }
                entry1 = iterator1.hasNext() ? iterator1.next() : null;
                entry2 = iterator2.hasNext() ? iterator2.next() : null;
            } else if (entry1 != null && (entry2 == null || entry1.getKey().compareTo(entry2.getKey()) < 0)) {
                diff.append("  - " + entry1.getKey() + ": " + entry1.getValue() + "\n");
                entry1 = iterator1.hasNext() ? iterator1.next() : null;
            } else if (entry2 != null && (entry1 == null || entry1.getKey().compareTo(entry2.getKey()) > 0)) {
                diff.append("  + " + entry2.getKey() + ": " + entry2.getValue() + "\n");
                entry2 = iterator2.hasNext() ? iterator2.next() : null;
            } else {
                entry1 = iterator1.hasNext() ? iterator1.next() : null;
                entry2 = iterator2.hasNext() ? iterator2.next() : null;
            }

        }
        diff.append("}");

        return diff.toString();
    }

    public static void main(String[] args) {
        //System.out.println("Hello World!");
        if (args.length != 0) {
            new CommandLine(new App()).execute(args);
        }
    }

}
