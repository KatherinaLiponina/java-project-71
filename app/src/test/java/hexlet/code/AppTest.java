package hexlet.code;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final class AppTest {
    private String file1;
    private String file2;
    private String file3;
    private String file4;

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
            Formatter.callFormatter(Differ.diff(Parser.mapJsonFile(file1), Parser.mapJsonFile(file2)),
            "stylish"));
    }

    @Test
    public void test12plain() throws Exception {
        assertEquals("Property 'believe' was updated. From [complex value] to [complex value]\nProperty 'follow' was"
            + " updated. From false to true\nProperty 'ip' was removed\nProperty 'key1' was updated. From 'value2' "
            + "to 'value1'\nProperty 'obj1' was updated. From [complex value] to [complex value]\nProperty 'time' "
            + "was removed\nProperty 'timeout' was added with value: 20\n"
            + "Property 'verbose' was added with value: true",
            Formatter.callFormatter(Differ.diff(Parser.mapJsonFile(file1), Parser.mapJsonFile(file2)),
            "plain"));
    }

    @Test
    public void test13() throws Exception {
        assertEquals("{\n  - believe: [1, 2, 4]\n  - follow: false\n"
            + "  - ip: 123.234.53.22\n  - key1: value2\n  - obj1: {nestedKey=value, isNested=false}\n  - time: 50\n}",
            Formatter.callFormatter(Differ.diff(Parser.mapJsonFile(file1), Parser.mapJsonFile(file3)),
            "stylish"));
    }

    @Test
    public void test42() throws Exception {
        assertEquals("{\n  - believe: [1, 2, 4]\n  + believe: [1, 2, 3]\n  - follow: false\n  + follow: true\n"
            + "  + key1: value1\n    obj1: {nestedKey=value, isNested=true}\n  - proxy: 123.234.53.22\n"
            + "  + timeout: 20\n  + verbose: true\n}",
            Formatter.callFormatter(Differ.diff(Parser.mapJsonFile(file4), Parser.mapJsonFile(file2)),
            "stylish"));
    }

    @Test
    public void test14() throws Exception {
        assertEquals("{\n    believe: [1, 2, 4]\n    follow: false\n  - ip: 123.234.53.22\n  - key1: value2\n"
            + "  - obj1: {nestedKey=value, isNested=false}\n  + obj1: {nestedKey=value, isNested=true}\n"
            + "  + proxy: 123.234.53.22\n}",
            Formatter.callFormatter(Differ.diff(Parser.mapJsonFile(file1), Parser.mapJsonFile(file4)),
            "stylish"));
    }

    @Test
    public void test11() throws Exception {
        assertEquals("{\n    believe: [1, 2, 4]\n    follow: false\n    ip: 123.234.53.22\n"
            + "    key1: value2\n    obj1: {nestedKey=value, isNested=false}\n    time: 50\n}",
            Formatter.callFormatter(Differ.diff(Parser.mapJsonFile(file1), Parser.mapJsonFile(file1)),
            "stylish"));
    }

    @Test
    public void testYAML() throws Exception {
        assertEquals("{\n    chars1: [a, b, c]\n  - chars2: [d, e, f]\n  + chars2: false\n  - checked: false\n"
            + "  + checked: true\n  - default: null\n  + default: [value1, value2]\n  - id: 45\n  + id: null\n"
            + "  - key1: value1\n  + key2: value2\n    numbers1: [1, 2, 3, 4]\n  - numbers2: [2, 3, 4, 5]\n"
            + "  + numbers2: [22, 33, 44, 55]\n  - numbers3: [3, 4, 5]\n  + numbers4: [4, 5, 6]\n"
            + "  + obj1: {nestedKey=value, isNested=true}\n  - setting1: Some value\n  + setting1: Another value\n"
            + "  - setting2: 200\n  + setting2: 300\n  - setting3: true\n  + setting3: none\n}",
            Formatter.callFormatter(Differ.diff(Parser.mapYamlFile("src/test/resources/testfile1.yml"),
            Parser.mapYamlFile("src/test/resources/testfile2.yml")),
            "stylish"));
    }

    @Test
    public void testYamlSamePlain() throws Exception {
        assertEquals("", Formatter.callFormatter(Differ.diff(
            Parser.mapYamlFile("src/test/resources/testfile1.yml"),
            Parser.mapYamlFile("src/test/resources/testfile1.yml")), "plain"));
    }

    @Test
    public void testYamlToJson() throws Exception {
        assertEquals(Files.readString(Paths.get("src/test/resources/answerfile1.json")),
            Differ.generate("src/test/resources/testfile1.yml", "src/test/resources/testfile1.yml", "json") + "\n");
    }

    @Test
    public void testJsonToJson() throws Exception {
        assertEquals(Files.readString(Paths.get("src/test/resources/answerfile2.json")),
            Differ.generate("src/test/resources/testfile1.json", "src/test/resources/testfile2.json", "json") + "\n");
    }

    @Test
    public void testJsonAndYaml() throws Exception {
        assertEquals(Files.readString(Paths.get("src/test/resources/answerfile3")),
            Differ.generate("src/test/resources/testfile1.json", "src/test/resources/testfile2.yml") + "\n\n");
    }
}
