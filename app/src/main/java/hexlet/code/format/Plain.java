package hexlet.code.format;

import hexlet.code.Utils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Plain {

    public static String getString(List<Map<String, Object>> output) {
        StringBuilder result = new StringBuilder();
        output.forEach(map -> {
            String name = map.get("name").toString();
            String type = map.get("type").toString();
            String value1 = toSimpleString(map.get(Utils.VALUE1_NAME));
            String value2 = toSimpleString(map.get(Utils.VALUE2_NAME));
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
        if (obj instanceof Boolean || Objects.equals(obj, null) || obj instanceof Integer) {
            return str;
        } else if (obj instanceof String) {
            return "'" + str + "'";
        } else {
            return "[complex value]";
        }
    }
}
