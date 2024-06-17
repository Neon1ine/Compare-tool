package hexlet.code.format;

import hexlet.code.Utils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Stylish {

    public static String getString(List<Map<String, Object>> output) {
        StringBuilder result = new StringBuilder();
        result.append("{\n");
        output.forEach(map -> {
            String name = map.get("name").toString();
            String type = map.get("type").toString();
            if (type.equals("unchanged")) {
                result.append("    ").append(name).append(": ")
                        .append(toNotNullString(map.get(Utils.VALUE_NAME))).append("\n");
            } else if (type.equals("changed")) {
                result.append("  - ").append(name).append(": ")
                        .append(toNotNullString(map.get(Utils.VALUE1_NAME))).append("\n");
                result.append("  + ").append(name).append(": ")
                        .append(toNotNullString(map.get(Utils.VALUE2_NAME))).append("\n");
            } else if (type.equals("deleted")) {
                result.append("  - ").append(name).append(": ")
                        .append(toNotNullString(map.get(Utils.VALUE_NAME))).append("\n");
            } else if (type.equals("added")) {
                result.append("  + ").append(name).append(": ")
                        .append(toNotNullString(map.get(Utils.VALUE_NAME))).append("\n");
            } else {
                throw new IllegalStateException("Unexpected format: " + type);
            }
        });
        result.append("}");
        return result.toString();
    }

    public static String toNotNullString(Object obj) {
        if (Objects.equals(obj, null)) {
            return "null";
        }
        return obj.toString();
    }

}
