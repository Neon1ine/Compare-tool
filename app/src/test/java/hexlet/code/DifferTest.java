package hexlet.code;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DifferTest {

    private static final Path FIRST_JSON_FILE_PATH = Paths.get("./src/test/resources/file1.json");
    private static final Path SECOND_JSON_FILE_PATH = Paths.get("./src/test/resources/file2.json");
    private static final Path FIRST_YAML_FILE_PATH = Paths.get("./src/test/resources/file1.yml");
    private static final Path SECOND_YAML_FILE_PATH = Paths.get("./src/test/resources/file2.yml");
    private static final Path DIFF_FILE_PATH_STYLISH = Paths.get("./src/test/resources/stylishDiff.txt");
    private static final Path DIFF_FILE_PATH_PLAIN = Paths.get("./src/test/resources/plainDiff.txt");
    private static final Path DIFF_FILE_PATH_JSON = Paths.get("./src/test/resources/jsonDiff.json");

    @Test
    public void testGenerateStylishFromJson() throws Exception {
        String actual = Differ.generate(FIRST_JSON_FILE_PATH.toString(), SECOND_JSON_FILE_PATH.toString());
        String expected = Files.readString(DIFF_FILE_PATH_STYLISH);
        assertThat(actual).isEqualTo(expected);
        actual = Differ.generate(FIRST_JSON_FILE_PATH.toString(), SECOND_JSON_FILE_PATH.toString(), "stylish");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testGenerateStylishFromYaml() throws Exception {
        String actual = Differ.generate(FIRST_YAML_FILE_PATH.toString(), SECOND_YAML_FILE_PATH.toString());
        String expected = Files.readString(DIFF_FILE_PATH_STYLISH);
        assertThat(actual).isEqualTo(expected);
        actual = Differ.generate(FIRST_YAML_FILE_PATH.toString(), SECOND_YAML_FILE_PATH.toString(), "stylish");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testGenerateStylishFromDifferentFiles() throws Exception {
        String actual = Differ.generate(FIRST_JSON_FILE_PATH.toString(), SECOND_YAML_FILE_PATH.toString());
        String expected = Files.readString(DIFF_FILE_PATH_STYLISH);
        assertThat(actual).isEqualTo(expected);
        actual = Differ.generate(FIRST_YAML_FILE_PATH.toString(), SECOND_JSON_FILE_PATH.toString());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testGeneratePlainFromJson() throws Exception {
        String actual = Differ.generate(FIRST_JSON_FILE_PATH.toString(), SECOND_JSON_FILE_PATH.toString(), "plain");
        assertThat(actual).isEqualTo(Files.readString(DIFF_FILE_PATH_PLAIN));
    }

    @Test
    public void testGeneratePlainFromYaml() throws Exception {
        String actual = Differ.generate(FIRST_YAML_FILE_PATH.toString(), SECOND_YAML_FILE_PATH.toString(), "plain");
        assertThat(actual).isEqualTo(Files.readString(DIFF_FILE_PATH_PLAIN));
    }

    @Test
    public void testGeneratePlainFromDifferentFiles() throws Exception {
        String actual = Differ.generate(FIRST_JSON_FILE_PATH.toString(), SECOND_YAML_FILE_PATH.toString(), "plain");
        String expected = Files.readString(DIFF_FILE_PATH_PLAIN);
        assertThat(actual).isEqualTo(expected);
        actual = Differ.generate(FIRST_YAML_FILE_PATH.toString(), SECOND_JSON_FILE_PATH.toString(), "plain");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testGenerateJsonFormatFromJsonFiles() throws Exception {
        String actualJson = Differ.generate(FIRST_JSON_FILE_PATH.toString(), SECOND_JSON_FILE_PATH.toString(), "json");
        String expectedJson = Files.readString(DIFF_FILE_PATH_JSON);
        JSONAssert.assertEquals(expectedJson, actualJson, JSONCompareMode.STRICT);
    }

    @Test
    public void testGenerateJsonFormatFromYamlFiles() throws Exception {
        String actualJson = Differ.generate(FIRST_YAML_FILE_PATH.toString(), SECOND_YAML_FILE_PATH.toString(), "json");
        String expectedJson = Files.readString(DIFF_FILE_PATH_JSON);
        JSONAssert.assertEquals(expectedJson, actualJson, JSONCompareMode.STRICT);
    }

    @Test
    public void testGenerateJsonFormatFromDifferentFiles() throws Exception {
        String actualJson = Differ.generate(FIRST_JSON_FILE_PATH.toString(), SECOND_YAML_FILE_PATH.toString(), "json");
        String expectedJson = Files.readString(DIFF_FILE_PATH_JSON);
        JSONAssert.assertEquals(expectedJson, actualJson, JSONCompareMode.STRICT);
        actualJson = Differ.generate(FIRST_YAML_FILE_PATH.toString(), SECOND_JSON_FILE_PATH.toString(), "json");
        JSONAssert.assertEquals(expectedJson, actualJson, JSONCompareMode.STRICT);
    }

    @Test
    public void testMakeJsonDiffList() throws Exception {
        Map<String, Object> contents1 = Parser.parse(Files.readString(FIRST_JSON_FILE_PATH), "json");
        Map<String, Object> contents2 = Parser.parse(Files.readString(SECOND_JSON_FILE_PATH), "json");
        String actualJson = Formatter.convert(Utils.makeDiffList(contents1, contents2), "json");
        String expectedJson = Files.readString(DIFF_FILE_PATH_JSON);
        JSONAssert.assertEquals(expectedJson, actualJson, JSONCompareMode.STRICT);
    }

}
