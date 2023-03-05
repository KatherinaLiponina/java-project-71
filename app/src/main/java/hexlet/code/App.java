package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
         description = "Compares two configuration files and shows a difference.")
public final class App implements Callable<Integer> {
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

        String result = Differ.generate(filepath1, filepath2, format);
        if (!result.equals("")) {
            System.out.println(result);
        }

        return 0;
    }

    public static void main(String[] args) {
        //System.out.println("Hello World!");
        if (args.length != 0) {
            new CommandLine(new App()).execute(args);
        }
    }

}
