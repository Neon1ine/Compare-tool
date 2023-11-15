package hexlet.code;

import org.junit.jupiter.api.Test;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DifferTest {

    private static final Path FIRST_JSON_FILE_PATH = Paths.get("./src/test/resources/file1.json");
    private static final Path SECOND_JSON_FILE_PATH = Paths.get("./src/test/resources/file2.json");

    @Test
    public void testGenerate() throws Exception {
        String expected = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";
        assertThat(Differ.generate(FIRST_JSON_FILE_PATH, SECOND_JSON_FILE_PATH)).isEqualTo(expected);
    }

    @Test
    public void testCastingStringToMap1() throws Exception {
        Map<String, Object> expected = new HashMap<>();
        expected.put("host", "hexlet.io");
        expected.put("timeout", 50);
        expected.put("proxy", "123.234.53.22");
        expected.put("follow", false);

        assertThat(Utils.castFileContentsIntoMap(FIRST_JSON_FILE_PATH)).isEqualTo(expected);
    }

    @Test
    public void testCastingStringToMap2() throws Exception {
        Map<String, Object> expected = new HashMap<>();
        expected.put("timeout", 20);
        expected.put("verbose", true);
        expected.put("host", "hexlet.io");

        assertThat(Utils.castFileContentsIntoMap(SECOND_JSON_FILE_PATH)).isEqualTo(expected);
    }
}
