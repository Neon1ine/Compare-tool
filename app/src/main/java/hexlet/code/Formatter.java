package hexlet.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Formatter {

    public static String convert(List<List<String>> outputList, String format) {
        if (format == null) {
            format = "stylish";
        }
        String result = switch (format) {
            case "plain" -> getPlainString(outputList);
            default -> getStylishString(outputList);
        };
        return result;
    }

    public static String getStylishString(List<List<String>> output) {
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

    public static String getPlainString(List<List<String>> output) {
        Map<String, List<String>> map = getDiffMap(output);
        StringBuilder result = new StringBuilder();
        for (List<String> line : output) {
            String name = line.get(0).substring(2);
            if (map.containsKey(name)) {
                Object data1 = toObject(map.get(name).get(1));
                if (map.get(name).size() == 3) {
                    Object data2 = toObject(map.get(name).get(2));
                    result.append("Property '").append(name).append("' was updated. From ")
                            .append(data1).append(" to ").append(data2).append("\n");
                } else if (map.get(name).get(0).charAt(0) == '-') {
                    result.append("Property '").append(name).append("' was removed").append("\n");
                } else if (map.get(name).get(0).charAt(0) == '+') {
                    result.append("Property '").append(name).append("' was added with value: ")
                            .append(data1).append("\n");
                }
                map.remove(name);
            }
        }

        return result.substring(0, result.length() - 1);
    }

    private static Map<String, List<String>> getDiffMap(List<List<String>> bigList) {
        Map<String, List<String>> result = new HashMap<>();
        bigList.stream()
                .forEach(line -> {
                    char sign = line.get(0).charAt(0);
                    String name = line.get(0).substring(2);
                    String content = line.get(2);
                    if (!result.containsKey(name)) {
                        List<String> values = new ArrayList<>();
                        values.add(String.valueOf(sign));
                        values.add(content);
                        result.put(name, values);
                    } else {
                        result.get(name).add(content);
                    }
                });
        return result;
    }

    private static Object toObject(String str) {
        if (str.equals("true") || str.equals("false") || str.equals("null") || isNumeric(str)) {
            return str;
        } else if ((str.startsWith("[") || (str.startsWith("{")))) {
            return "[complex value]";
        } else {
            return "'" + str + "'";
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

}
