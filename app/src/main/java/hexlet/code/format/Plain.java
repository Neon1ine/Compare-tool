package hexlet.code.format;

import java.util.List;
import java.util.Map;

public class Plain {

    public static String getString(List<Map<String, Object>> output) {
        StringBuilder result = new StringBuilder();
        output.forEach(map -> {
            String name = map.get("name").toString();
            String type = map.get("type").toString();
            String value1 = toSimpleString(map.get("oldValue"));
            String value2 = toSimpleString(map.get("newValue"));
            if (type.equals("changed")) {
                result.append("Property '").append(name).append("' was updated. From ")
                        .append(value1).append(" to ").append(value2).append("\n");
            } else if (type.equals("deleted")) {
                result.append("Property '").append(name).append("' was removed").append("\n");
            } else if (type.equals("added")) {
                result.append("Property '").append(name).append("' was added with value: ")
                    .append(value2).append("\n");
            } else if (!type.equals("unchanged")) {
                throw new IllegalStateException("Unexpected format: " + type);
            }
        });
        return result.substring(0, result.length() - 1);
    }

    private static String toSimpleString(Object obj) {
        String str = Stylish.toNotNullString(obj);
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
