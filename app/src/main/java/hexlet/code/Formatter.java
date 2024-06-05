package hexlet.code;

import hexlet.code.format.Json;
import hexlet.code.format.Plain;
import hexlet.code.format.Stylish;
import java.util.List;

public class Formatter {

    public static String convert(List<List<String>> outputList, String format) throws Exception {
        format = format.toLowerCase().trim();
        if (format.isEmpty() || format.equals("[]")) {
            format = "stylish";
        }
        String result = switch (format) {
            case "plain" -> Plain.getString(outputList);
            case "json" -> Json.getString(outputList);
            case "stylish" -> Stylish.getString(outputList);
            default -> throw new IllegalStateException("Unexpected format: " + format);
        };
        return result;
    }

}
