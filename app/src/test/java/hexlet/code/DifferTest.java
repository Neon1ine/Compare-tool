package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DifferTest {
    private static Map<String, Object> contentsOfFile1;
    private static Map<String, Object> contentsOfFile2;

    @BeforeAll
    public static void beforeAll() throws Exception {
        contentsOfFile1 = Utils.getContentsOfFile(1);
        contentsOfFile2 = Utils.getContentsOfFile(2);
    }

    /*@Test
    public void testDiff() {
        String expected = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }
                """;
        assertThat(Differ.findDiff(contentsOfFile1, contentsOfFile2)).isEqualTo(expected);
    }*/

    @Test
    public void testCastingStringToMap1() throws Exception {
        Map<String, Object> expected = new HashMap<>();
        expected.put("host", "hexlet.io");
        expected.put("timeout", 50);
        expected.put("proxy", "123.234.53.22");
        expected.put("follow", false);

        List<String> fileLines = List.of("{", "  \"host\": \"hexlet.io\"", "  \"timeout\": 50",
                "  \"proxy\": \"123.234.53.22\"", "  \"follow\": false", "}");

        assertThat(Utils.castFileContentsIntoMap(fileLines)).isEqualTo(expected);
    }

    @Test
    public void testCastingStringToMap2() throws Exception {
        Map<String, Object> expected = new HashMap<>();
        expected.put("timeout", 20);
        expected.put("verbose", true);
        expected.put("host", "hexlet.io");

        List<String> fileLines = List.of("{", "  \"timeout\": 20",
                "  \"verbose\": true", "  \"host\": \"hexlet.io\"", "}");

        assertThat(Utils.castFileContentsIntoMap(fileLines)).isEqualTo(expected);
    }
}
