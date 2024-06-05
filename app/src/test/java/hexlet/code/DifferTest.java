package hexlet.code;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DifferTest {

    private static final Path FIRST_JSON_FILE_PATH = Paths.get("./src/test/resources/file1.json");
    private static final Path SECOND_JSON_FILE_PATH = Paths.get("./src/test/resources/file2.json");
    private static final Path FIRST_YAML_FILE_PATH = Paths.get("./src/test/resources/file1.yml");
    private static final Path SECOND_YAML_FILE_PATH = Paths.get("./src/test/resources/file2.yml");
    private static final Path DIFF_FILE_PATH_STYLISH = Paths.get("./src/test/resources/stylishDiff.txt");
    private static final Path DIFF_FILE_PATH_PLAIN = Paths.get("./src/test/resources/plainDiff.txt");
    private static final Path DIFF_FILE_PATH_JSON = Paths.get("./src/test/resources/jsonDiff.json");

    private static String expectedDiff;

    private void initStylishDiff() throws IOException {
        expectedDiff = Files.readString(DIFF_FILE_PATH_STYLISH);
    }

    @Test
    public void testGenerateJson() throws Exception {
        initStylishDiff();
        String actual = Differ.generate(FIRST_JSON_FILE_PATH.toString(), SECOND_JSON_FILE_PATH.toString());
        assertThat(actual).isEqualTo(expectedDiff);
    }

    @Test
    public void testGenerateYaml() throws Exception {
        initStylishDiff();
        String actual = Differ.generate(FIRST_YAML_FILE_PATH.toString(), SECOND_YAML_FILE_PATH.toString());
        assertThat(actual).isEqualTo(expectedDiff);
    }

    @Test
    public void testFileExtensionFinder() {
        assertThat(Parser.findFileExtension(FIRST_JSON_FILE_PATH)).isEqualTo("json");
        assertThat(Parser.findFileExtension(FIRST_YAML_FILE_PATH)).isEqualTo("yml");
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
    public void testPlainFormatter() throws Exception {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        App.setFilepath1(FIRST_JSON_FILE_PATH.toString());
        App.setFilepath2(SECOND_JSON_FILE_PATH.toString());
        App.setFormat("plain");
        App app = new App();
        app.call();
        String actual = outContent.toString().trim();
        String expected = Files.readString(DIFF_FILE_PATH_PLAIN);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testJsonFormatter() throws Exception {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        App.setFilepath1(FIRST_JSON_FILE_PATH.toString());
        App.setFilepath2(SECOND_JSON_FILE_PATH.toString());
        App.setFormat("json");
        App app = new App();
        app.call();
        String actual = outContent.toString().trim();
        String expected = Files.readString(DIFF_FILE_PATH_JSON);
        assertThat(actual).isEqualTo(expected);
    }

}
