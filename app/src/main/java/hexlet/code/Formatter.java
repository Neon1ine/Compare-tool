package hexlet.code;

import hexlet.code.format.Json;
import hexlet.code.format.Plain;
import hexlet.code.format.Stylish;
import java.util.List;

public class Formatter {

    public static String convert(List<List<String>> outputList, String format) throws Exception {
        return switch (fixFormat(format)) {
            case "plain" -> Plain.getString(outputList);
            case "json" -> Json.getString(outputList);
            case "stylish" -> Stylish.getString(outputList);
            default -> throw new IllegalStateException("Unexpected format: " + format);
        };
    }

    private static String fixFormat(String format) {
        String result = format.toLowerCase().trim();
        if (result.startsWith("[") && result.endsWith("]")) {
            result = result.substring(1, result.length() - 1);
        }
        if (result.isEmpty()) {
            result = "stylish";
        }
        return result;
    }

}
