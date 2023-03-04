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

    @BeforeEach
    public void init() throws Exception {
        final Path path1 = Paths.get("src/test/resources/testfile1.json");
        final Path path2 = Paths.get("src/test/resources/testfile2.json");
        final Path path3 = Paths.get("src/test/resources/testfile3.json");
        final Path path4 = Paths.get("src/test/resources/testfile4.json");
        file1 = Files.readString(path1);
        file2 = Files.readString(path2);
        file3 = Files.readString(path3);
        file4 = Files.readString(path4);
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
        assertEquals("{\n    chars1: [a, b, c]\n  - chars2: [d, e, f]\n  + chars2: false\n  - checked: false\n"
            + "  + checked: true\n  - default: null\n  + default: [value1, value2]\n  - id: 45\n  + id: null\n"
            + "  - key1: value1\n  + key2: value2\n    numbers1: [1, 2, 3, 4]\n  - numbers2: [2, 3, 4, 5]\n"
            + "  + numbers2: [22, 33, 44, 55]\n  - numbers3: [3, 4, 5]\n  + numbers4: [4, 5, 6]\n"
            + "  + obj1: {nestedKey=value, isNested=true}\n  - setting1: Some value\n  + setting1: Another value\n"
            + "  - setting2: 200\n  + setting2: 300\n  - setting3: true\n  + setting3: none\n}",
            Formatter.stylishFormatter(Differ.diff(Parser.yamlFileMapper("src/test/resources/testfile1.yaml"),
            Parser.yamlFileMapper("src/test/resources/testfile2.yaml"))));
    }
}
