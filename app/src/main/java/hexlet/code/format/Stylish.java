package hexlet.code.format;

import java.util.List;

public class Stylish {

    public static String getString(List<List<String>> output) {
        StringBuilder result = new StringBuilder();
        result.append("{\n");
        output.stream()
                .forEach(line -> {
                    result.append("  ");
                    if (line.get(0).charAt(0) != '-' && line.get(0).charAt(0) != '+') {
                        result.append("  ");
                    }
                    result
                        .append(line.get(0))
                        .append(line.get(1))
                        .append(line.get(2))
                        .append("\n");
                });
        result.append("}");
        return result.toString();
    }

}
