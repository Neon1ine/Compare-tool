package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DifferTest {

    private static final Path FIRST_JSON_FILE_PATH = Paths.get("./src/test/resources/file1.json");
    private static final Path SECOND_JSON_FILE_PATH = Paths.get("./src/test/resources/file2.json");
    private static final Path FIRST_YAML_FILE_PATH = Paths.get("./src/test/resources/file1.yml");
    private static final Path SECOND_YAML_FILE_PATH = Paths.get("./src/test/resources/file2.yml");

    private static final String EXPECTED_DIFF = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

    private static Map<String, Object> firstFileContents;
    private static Map<String, Object> secondFileContents;

    @BeforeAll
    public static void setupMaps() {
        firstFileContents = new HashMap<>();
        firstFileContents.put("host", "hexlet.io");
        firstFileContents.put("timeout", 50);
        firstFileContents.put("proxy", "123.234.53.22");
        firstFileContents.put("follow", false);

        secondFileContents = new HashMap<>();
        secondFileContents.put("timeout", 20);
        secondFileContents.put("verbose", true);
        secondFileContents.put("host", "hexlet.io");
    }

    @Test
    public void testGenerateJson() throws Exception {
        assertThat(Differ.generate(FIRST_JSON_FILE_PATH, SECOND_JSON_FILE_PATH)).isEqualTo(EXPECTED_DIFF);
    }

    @Test
    public void testGenerateYaml() throws Exception {
        assertThat(Differ.generate(FIRST_YAML_FILE_PATH, SECOND_YAML_FILE_PATH)).isEqualTo(EXPECTED_DIFF);
    }

    @Test
    public void testCastingStringToMap1() throws Exception {
        assertThat(Utils.castFileContentsIntoMap(FIRST_JSON_FILE_PATH)).isEqualTo(firstFileContents);
    }

    @Test
    public void testCastingStringToMap2() throws Exception {
        assertThat(Utils.castFileContentsIntoMap(SECOND_JSON_FILE_PATH)).isEqualTo(secondFileContents);
    }

    @Test
    public void testFileExtensionFinder() {
        assertThat(Parser.findFileExtension(FIRST_JSON_FILE_PATH)).isEqualTo("json");
        assertThat(Parser.findFileExtension(FIRST_YAML_FILE_PATH)).isEqualTo("yml");
        //other file extensions
    }

    @Test
    public void testYamlParser() throws Exception {
        assertThat(Parser.yamlFileToMap(FIRST_YAML_FILE_PATH)).isEqualTo(firstFileContents);
    }
}
