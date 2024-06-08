package hexlet.code.format;

import java.util.List;
import java.util.Map;

public class Stylish {

    public static String getString(List<Map<String, Object>> output) {
        StringBuilder result = new StringBuilder();
        result.append("{\n");
        output.forEach(map -> {
            String name = map.get("name").toString();
            String type = map.get("type").toString();
            Object value1 = map.get("oldValue");
            Object value2 = map.get("newValue");
            if (type.equals("unchanged")) {
                result.append("    ").append(name).append(": ").append(toNotNullString(value1)).append("\n");
            } else if (type.equals("changed")) {
                result.append("  - ").append(name).append(": ").append(toNotNullString(value1)).append("\n");
                result.append("  + ").append(name).append(": ").append(toNotNullString(value2)).append("\n");
            } else if (type.equals("deleted")) {
                result.append("  - ").append(name).append(": ").append(toNotNullString(value1)).append("\n");
            } else if (type.equals("added")) {
                result.append("  + ").append(name).append(": ").append(toNotNullString(value2)).append("\n");
            } else {
                throw new IllegalStateException("Unexpected format: " + type);
            }
        });
        result.append("}");
        return result.toString();
    }

    public static String toNotNullString(Object obj) {
        try {
            return obj.toString();
        } catch (Exception e) {
            return "null";
        }
    }

}
