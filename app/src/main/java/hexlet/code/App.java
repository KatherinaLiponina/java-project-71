package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.io.File;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
         description = "Compares two configuration files and shows a difference.")
public class App {
    @Parameters(index = "0", description = "path to first file")
    private File filepath1;
    @Parameters(index = "1", description = "path to second file")
    private File filepath2;
    @Option(names = {"-f", "--format"}, description = "output format", defaultValue="stylish")
    private String format = "stylish";

    public static void main(String[] args) {
        System.out.println("Hello World!");
        if (args.length != 0) {
            new CommandLine(new App()).execute(args);
        }
    }
    
}