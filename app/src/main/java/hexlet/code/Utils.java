package hexlet.code;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {
    public static final String firstFilePath = "./src/test/resources/file1.json";
    public static final String secondFilePath = "./src/test/resources/file2.json";

    public static Map<String, Object> getContentsOfFile(int n) throws Exception{
        Path path;
        switch (n) {
            case 1: {
                path = Paths.get(firstFilePath);
                break;
            }
            case 2: {
                path = Paths.get(secondFilePath);
                break;
            }
            default: throw new Exception("File number '" + n + "' does not exists");
        }

        if (!Files.exists(path)) {
            throw new Exception("File '" + path + "' does not exist");
        }

        List<String> lines = Files.readAllLines(path);
        return castFileContentsIntoMap(lines);
    }

    public static Map<String, Object> castFileContentsIntoMap(List<String>  lines) throws Exception {
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

            if (strValue.equals("false")) {
                result.put(key, false);
            } else if (strValue.equals("true")) {
                result.put(key, true);
            } else if (isNumeric(strValue)) {
                result.put(key, Integer.parseInt(strValue));
            } else {
                result.put(key, strValue);
            }
        }

        return result;
    }

    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
