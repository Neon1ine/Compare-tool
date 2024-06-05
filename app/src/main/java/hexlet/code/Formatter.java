package hexlet.code;

import hexlet.code.format.Json;
import hexlet.code.format.Plain;
import hexlet.code.format.Stylish;
import java.util.List;

public class Formatter {

    public static String convert(List<List<String>> outputList, String format) throws Exception {
        if (format == null || format.isEmpty()) {
            format = "stylish";
        }
        String result = switch (format) {
            case "plain" -> Plain.getString(outputList);
            case "json" -> Json.getString(outputList);
            default -> Stylish.getString(outputList);
        };
        return result;
    }

}
