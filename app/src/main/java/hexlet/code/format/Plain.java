package hexlet.code.format;

import hexlet.code.Utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Plain {

    public static String getString(List<Map<String, Object>> output) {
        StringBuilder result = new StringBuilder();
        output.forEach(map -> {
            String name = map.get("name").toString();
            String type = map.get("type").toString();
            switch (type) {
                case "changed" -> {
                    String value1 = toSimpleString(map.get(Utils.VALUE1_NAME));
                    String value2 = toSimpleString(map.get(Utils.VALUE2_NAME));
                    result.append("Property '").append(name).append("' was updated. From ")
                            .append(value1).append(" to ").append(value2).append("\n");
                }
                case "deleted" -> result.append("Property '").append(name).append("' was removed").append("\n");
                case "added" -> {
                    String value = toSimpleString(map.get(Utils.VALUE_NAME));
                    result.append("Property '").append(name).append("' was added with value: ")
                            .append(value).append("\n");
                }
                case "unchanged" -> {}
                default -> throw new IllegalStateException("Unexpected format: " + type);
            }
        });
        return result.substring(0, result.length() - 1);
    }

    private static String toSimpleString(Object obj) {
        String str = String.valueOf(obj);
        if (obj instanceof Boolean || Objects.equals(obj, null) || obj instanceof Integer) {
            return str;
        } else if (obj instanceof Collection<?> || obj instanceof Map<?, ?>) {
            return "[complex value]";
        } else {
            return "'" + str + "'";
        }
    }
}
