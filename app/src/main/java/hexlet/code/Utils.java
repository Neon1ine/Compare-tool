package hexlet.code;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {
    private static final Path firstJsonFilePath = Paths.get("./src/test/resources/file1.json");
    private static final Path secondJsonFilePath = Paths.get("./src/test/resources/file2.json");

    public static Map<String, Object> parse(Path path) throws Exception {
        if (!Files.exists(path)) {
            throw new Exception("File '" + path + "' does not exist");
        }

        return castFileContentsIntoMap(path);
    }

    public static Map<String, Object> castFileContentsIntoMap(Path path) throws Exception {
        List<String> lines = Files.readAllLines(path);
        if (lines.isEmpty()) throw new Exception("nothing to read in file");

        Map<String, Object> result = new HashMap<>();

        for (String oneLine : lines) {
            if (oneLine.length() == 1) {
                continue;
            }
            List<String> lineContents = List.of(oneLine.split("\""));
            String key = lineContents.get(1);
            String strValue = lineContents.get(2);
            if (lineContents.get(2).equals(": ")) {
                strValue = lineContents.get(3);
            } else {
                strValue = strValue.substring(2);
                if (strValue.endsWith(",")) {
                    strValue = strValue.substring(0, strValue.length() - 1);
                }
            }
            result.put(key, castStringIntoObject(strValue));
        }

        return result;
    }

    private static Object castStringIntoObject(String str) {
        if (str.equals("false")) {
            return false;
        } else if (str.equals("true")) {
            return true;
        } else if (isNumeric(str)) {
            return Integer.parseInt(str);
        } else {
            return str;
        }
    }

    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Path getFirstJsonFilePath() {
        return firstJsonFilePath;
    }

    public static Path getSecondJsonFilePath() {
        return secondJsonFilePath;
    }

}
