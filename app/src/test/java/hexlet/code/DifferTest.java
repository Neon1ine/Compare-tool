package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Comparator;
import java.util.List;

import static hexlet.code.Differ.makeDiffList;
import static org.assertj.core.api.Assertions.assertThat;

public class DifferTest {

    private static final Path FIRST_JSON_FILE_PATH = Paths.get("./src/test/resources/file1.json");
    private static final Path SECOND_JSON_FILE_PATH = Paths.get("./src/test/resources/file2.json");
    private static final Path FIRST_YAML_FILE_PATH = Paths.get("./src/test/resources/file1.yml");
    private static final Path SECOND_YAML_FILE_PATH = Paths.get("./src/test/resources/file2.yml");
    private static final Path DIFF_FILE_PATH_STYLISH = Paths.get("./src/test/resources/stylishDiff.txt");
    private static final Path DIFF_FILE_PATH_PLAIN = Paths.get("./src/test/resources/plainDiff.txt");

    private static String expectedDiff;

    @BeforeAll
    public static void setup() throws IOException {
        expectedDiff = Files.readString(DIFF_FILE_PATH_STYLISH);
    }

    @Test
    public void testGenerateJson() throws Exception {
        String actual = Differ.generate(FIRST_JSON_FILE_PATH, SECOND_JSON_FILE_PATH, "stylish");
        assertThat(actual).isEqualTo(expectedDiff);
    }

    @Test
    public void testGenerateYaml() throws Exception {
        String actual = Differ.generate(FIRST_YAML_FILE_PATH, SECOND_YAML_FILE_PATH, "stylish");
        assertThat(actual).isEqualTo(expectedDiff);
    }

    @Test
    public void testFileExtensionFinder() {
        assertThat(Parser.findFileExtension(FIRST_JSON_FILE_PATH)).isEqualTo("json");
        assertThat(Parser.findFileExtension(FIRST_YAML_FILE_PATH)).isEqualTo("yml");
        //add other file extensions
    }

    @Test
    public void testGetLine() {
        List<String> expected = new ArrayList<>();
        expected.add("+ key2");
        expected.add(": ");
        expected.add("value2");
        List<String> actual = Differ.getLine("+ key2", "value2");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testFormatter() throws Exception {
        Map<String, Object> contents1 = Parser.parse(FIRST_JSON_FILE_PATH);
        Map<String, Object> contents2 = Parser.parse(SECOND_JSON_FILE_PATH);
        List<List<String>> output = makeDiffList(contents1, contents2);
        output.sort(Comparator.comparing(line -> line.get(0).substring(2)));

        String expected = Files.readString(DIFF_FILE_PATH_PLAIN);
        String actual = Formatter.getPlainString(output);
        assertThat(actual).isEqualTo(expected);
    }

}
