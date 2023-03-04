package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.Callable;

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

        String result = Formatter.stylishFormatter(Differ.diff(preParsing(filepath1, file1),
            preParsing(filepath2, file2)));
        System.out.println(result);

        return 0;
    }

    Map<String, String> preParsing(String filepath, String filedata) throws Exception {
        Map<String, String> mappa;
        String fileFormat = filepath.substring(filepath.length() - ".json".length());
        if (fileFormat.equals(".json")) {
            mappa = Parser.jsonFileMapper(filedata);
        } else if (fileFormat.equals(".yaml")) {
            mappa = Parser.yamlFileMapper(filepath);
        } else {
            mappa = null;
            throw new Exception("Unrecognized format");
        }
        return mappa;
    }

    public static void main(String[] args) {
        //System.out.println("Hello World!");
        if (args.length != 0) {
            new CommandLine(new App()).execute(args);
        }
    }

}
