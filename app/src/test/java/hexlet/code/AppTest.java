package hexlet.code;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {
    String file1;
    String file2;
    String file3;
    String file4;
    String file1YAML;
    String file2YAML;

    @BeforeEach
    public void init() throws Exception {
        final Path path1 = Paths.get("src/test/resources/testfile1.json");
        final Path path2 = Paths.get("src/test/resources/testfile2.json");
        final Path path3 = Paths.get("src/test/resources/testfile3.json");
        final Path path4 = Paths.get("src/test/resources/testfile4.json");
        final Path path1Y = Paths.get("src/test/resources/testfile1.yaml");
        final Path path2Y = Paths.get("src/test/resources/testfile2.yaml");
        file1 = Files.readString(path1);
        file2 = Files.readString(path2);
        file3 = Files.readString(path3);
        file4 = Files.readString(path4);
        file1YAML = Files.readString(path1Y);
        file2YAML = Files.readString(path2Y);
    }

    @Test
    public void test12() throws Exception {
        assertEquals("{\n  - believe: [1, 2, 4]\n  + believe: [1, 2, 3]\n  - follow: false\n  + follow: true\n"
            + "  - ip: 123.234.53.22\n  - key1: value2\n  + key1: value1\n  - obj1: {nestedKey=value, isNested=false}\n"
            + "  + obj1: {nestedKey=value, isNested=true}\n  - time: 50\n  + timeout: 20\n  + verbose: true\n}",
            Formatter.stylishFormatter(Differ.diff(Parser.jsonFileMapper(file1), Parser.jsonFileMapper(file2))));
    }

    @Test
    public void test13() throws Exception {
        assertEquals("{\n  - believe: [1, 2, 4]\n  - follow: false\n"
            + "  - ip: 123.234.53.22\n  - key1: value2\n  - obj1: {nestedKey=value, isNested=false}\n  - time: 50\n}",
            Formatter.stylishFormatter(Differ.diff(Parser.jsonFileMapper(file1), Parser.jsonFileMapper(file3))));
    }

    @Test
    public void test42() throws Exception {
        assertEquals("{\n  - believe: [1, 2, 4]\n  + believe: [1, 2, 3]\n  - follow: false\n  + follow: true\n"
            + "  + key1: value1\n    obj1: {nestedKey=value, isNested=true}\n  - proxy: 123.234.53.22\n"
            + "  + timeout: 20\n  + verbose: true\n}",
            Formatter.stylishFormatter(Differ.diff(Parser.jsonFileMapper(file4), Parser.jsonFileMapper(file2))));
    }

    @Test
    public void test14() throws Exception {
        assertEquals("{\n    believe: [1, 2, 4]\n    follow: false\n  - ip: 123.234.53.22\n  - key1: value2\n"
            + "  - obj1: {nestedKey=value, isNested=false}\n  + obj1: {nestedKey=value, isNested=true}\n"
            + "  + proxy: 123.234.53.22\n}",
            Formatter.stylishFormatter(Differ.diff(Parser.jsonFileMapper(file1), Parser.jsonFileMapper(file4))));
    }

    @Test
    public void test11() throws Exception {
        assertEquals("{\n    believe: [1, 2, 4]\n    follow: false\n    ip: 123.234.53.22\n"
            + "    key1: value2\n    obj1: {nestedKey=value, isNested=false}\n    time: 50\n}",
            Formatter.stylishFormatter(Differ.diff(Parser.jsonFileMapper(file1), Parser.jsonFileMapper(file1))));
    }

    @Test
    public void testYAML() throws Exception {
        assertEquals("{\n  - follow: false\n    host: hexlet.io\n  - proxy: 123.234.53.22\n"
            + "  - timeout: 50\n  + timeout: 20\n  + verbose: true\n}",
            Formatter.stylishFormatter(Differ.diff(Parser.yamlFileMapper(file1YAML),
            Parser.yamlFileMapper(file2YAML))));
    }

}
