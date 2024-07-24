package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

//Применение интеграционных тестов, например с помощью mockito, улучшит тесты.

public class DifferTest {

    private static final Path FIRST_JSON_FILE_PATH = findPath("file1.json");
    private static final Path SECOND_JSON_FILE_PATH = findPath("file2.json");
    private static final Path FIRST_YAML_FILE_PATH = findPath("file1.yml");
    private static final Path SECOND_YAML_FILE_PATH = findPath("file2.yml");
    private static final Path DIFF_FILE_PATH_STYLISH = findPath("stylishDiff.txt");
    private static final Path DIFF_FILE_PATH_PLAIN = findPath("plainDiff.txt");
    private static final Path DIFF_FILE_PATH_JSON = findPath("jsonDiff.json");
    private static String stylishDiff;
    private static String plainDiff;
    private static String jsonDiff;

    private static Path findPath(String fileName) {
        return Paths.get("./src/test/resources/" + fileName);
    }

    @BeforeAll
    public static void init() throws IOException {
        stylishDiff = Files.readString(DIFF_FILE_PATH_STYLISH);
        plainDiff = Files.readString(DIFF_FILE_PATH_PLAIN);
        jsonDiff = Files.readString(DIFF_FILE_PATH_JSON);
    }

    @Test
    public void testGenerateStylishFromJson() throws Exception {
        String actual = Differ.generate(FIRST_JSON_FILE_PATH.toString(), SECOND_JSON_FILE_PATH.toString());
        String expected = stylishDiff;
        assertThat(actual).isEqualTo(expected);
        actual = Differ.generate(FIRST_JSON_FILE_PATH.toString(), SECOND_JSON_FILE_PATH.toString(), "stylish");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testGenerateStylishFromYaml() throws Exception {
        String actual = Differ.generate(FIRST_YAML_FILE_PATH.toString(), SECOND_YAML_FILE_PATH.toString());
        String expected = stylishDiff;
        assertThat(actual).isEqualTo(expected);
        actual = Differ.generate(FIRST_YAML_FILE_PATH.toString(), SECOND_YAML_FILE_PATH.toString(), "stylish");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testGenerateStylishFromDifferentFiles() throws Exception {
        String actual = Differ.generate(FIRST_JSON_FILE_PATH.toString(), SECOND_YAML_FILE_PATH.toString());
        String expected = stylishDiff;
        assertThat(actual).isEqualTo(expected);
        actual = Differ.generate(FIRST_YAML_FILE_PATH.toString(), SECOND_JSON_FILE_PATH.toString());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testGeneratePlainFromJson() throws Exception {
        String actual = Differ.generate(FIRST_JSON_FILE_PATH.toString(), SECOND_JSON_FILE_PATH.toString(), "plain");
        assertThat(actual).isEqualTo(plainDiff);
    }

    @Test
    public void testGeneratePlainFromYaml() throws Exception {
        String actual = Differ.generate(FIRST_YAML_FILE_PATH.toString(), SECOND_YAML_FILE_PATH.toString(), "plain");
        assertThat(actual).isEqualTo(plainDiff);
    }

    @Test
    public void testGeneratePlainFromDifferentFiles() throws Exception {
        String actual = Differ.generate(FIRST_JSON_FILE_PATH.toString(), SECOND_YAML_FILE_PATH.toString(), "plain");
        String expected = plainDiff;
        assertThat(actual).isEqualTo(expected);
        actual = Differ.generate(FIRST_YAML_FILE_PATH.toString(), SECOND_JSON_FILE_PATH.toString(), "plain");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testGenerateJsonFormatFromJsonFiles() throws Exception {
        String actualJson = Differ.generate(FIRST_JSON_FILE_PATH.toString(), SECOND_JSON_FILE_PATH.toString(), "json");
        JSONAssert.assertEquals(jsonDiff, actualJson, false);
    }

    @Test
    public void testGenerateJsonFormatFromYamlFiles() throws Exception {
        String actualJson = Differ.generate(FIRST_YAML_FILE_PATH.toString(), SECOND_YAML_FILE_PATH.toString(), "json");
        JSONAssert.assertEquals(jsonDiff, actualJson, false);
    }

    @Test
    public void testGenerateJsonFormatFromDifferentFiles() throws Exception {
        String actualJson = Differ.generate(FIRST_JSON_FILE_PATH.toString(), SECOND_YAML_FILE_PATH.toString(), "json");
        String expectedJson = jsonDiff;
        JSONAssert.assertEquals(expectedJson, actualJson, false);
        actualJson = Differ.generate(FIRST_YAML_FILE_PATH.toString(), SECOND_JSON_FILE_PATH.toString(), "json");
        JSONAssert.assertEquals(expectedJson, actualJson, false);
    }

}
