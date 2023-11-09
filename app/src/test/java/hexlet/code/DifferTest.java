package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DifferTest {
    private static Path path1;
    private static Path path2;

    @BeforeAll
    public static void beforeAll() {
        path1 = Utils.getFirstJsonFilePath();
        path2 = Utils.getSecondJsonFilePath();
    }

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
        assertThat(Differ.generate(path1, path2)).isEqualTo(expected);
    }

    @Test
    public void testCastingStringToMap1() throws Exception {
        Map<String, Object> expected = new HashMap<>();
        expected.put("host", "hexlet.io");
        expected.put("timeout", 50);
        expected.put("proxy", "123.234.53.22");
        expected.put("follow", false);

        assertThat(Utils.castFileContentsIntoMap(path1)).isEqualTo(expected);
    }

    @Test
    public void testCastingStringToMap2() throws Exception {
        Map<String, Object> expected = new HashMap<>();
        expected.put("timeout", 20);
        expected.put("verbose", true);
        expected.put("host", "hexlet.io");

        assertThat(Utils.castFileContentsIntoMap(path2)).isEqualTo(expected);
    }
}
