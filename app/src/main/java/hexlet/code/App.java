package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
         description = "Compares two configuration files and shows a difference.")
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        if (args.length != 0) {
            int exitCode = new CommandLine(new App()).execute(args);
            System.out.println(exitCode);
        }
    }
    
}