package hexlet.code;

import hexlet.code.format.Json;
import hexlet.code.format.Plain;
import hexlet.code.format.Stylish;
import java.util.List;

public class Formatter {

    public static String convert(List<List<String>> outputList, String format) throws Exception {
        String result = switch (fixFormat(format)) {
            case "plain" -> Plain.getString(outputList);
            case "json" -> Json.getString(outputList);
            case "stylish" -> Stylish.getString(outputList);
            default -> throw new IllegalStateException("Unexpected format: " + format);
        };
        return result;
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
