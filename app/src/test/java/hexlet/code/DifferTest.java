package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DifferTest {
    private static String contentsOfFile1;
    private static String contentsOfFile2;

    @BeforeAll
    public static void beforeAll() {
        contentsOfFile1 = """
                {
                  "host": "hexlet.io",
                  "timeout": 50,
                  "proxy": "123.234.53.22",
                  "follow": false
                }
                """;

        contentsOfFile2 = """
                {
                  "timeout": 20,
                  "verbose": true,
                  "host": "hexlet.io"
                }
                """;
    }

    @Test
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
    }
}
