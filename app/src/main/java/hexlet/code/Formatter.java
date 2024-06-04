package hexlet.code;

import java.util.List;

public class Formatter {

    public static String convert(List<List<String>> outputList, String format) {
        if (format == null) {
            format = "stylish";
        }
        String result = "";
        if (format.equals("stylish")) {
            result = listToString(outputList);
        }
        return result;
    }

    private static String listToString(List<List<String>> output) {
        StringBuilder result = new StringBuilder();
        result.append("{\n");
        output.stream()
                .forEach(line -> result
                        .append("  ")
                        .append(line.get(0))
                        .append(line.get(1))
                        .append(line.get(2))
                        .append("\n"));
        result.append("}");
        return result.toString();
    }
}
