package hexlet.code.format;

import hexlet.code.Differ;

import java.util.List;
import java.util.Map;

public class Plain {

    public static final int LIST_MAX_SIZE = 3;

    public static String getString(List<List<String>> output) {
        Map<String, List<String>> map = Differ.getDiffMap(output);
        StringBuilder result = new StringBuilder();
        for (List<String> line : output) {
            String name = line.get(0).substring(2);
            if (map.containsKey(name)) {
                Object data1 = toSimpleObject(map.get(name).get(1));
                if (map.get(name).size() == LIST_MAX_SIZE) {
                    Object data2 = toSimpleObject(map.get(name).get(2));
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

    private static Object toSimpleObject(String str) {
        if (str.equals("true") || str.equals("false") || str.equals("null") || isNumeric(str)) {
            return str;
        } else if ((str.startsWith("[") || (str.startsWith("{")))) {
            return "[complex value]";
        } else {
            return "'" + str + "'";
        }
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
