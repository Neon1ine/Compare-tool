package hexlet.code.format;

import hexlet.code.Utils;

import java.util.List;
import java.util.Map;

public class Stylish {

    public static String getString(List<Map<String, Object>> output) {
        StringBuilder result = new StringBuilder();
        result.append("{\n");
        output.forEach(map -> {
            String name = map.get("name").toString();
            String type = map.get("type").toString();
            switch (type) {
                case "unchanged" -> result.append("    ").append(name).append(": ")
                        .append(map.get(Utils.VALUE_NAME)).append("\n");
                case "changed" -> {
                    result.append("  - ").append(name).append(": ")
                            .append(map.get(Utils.VALUE1_NAME)).append("\n");
                    result.append("  + ").append(name).append(": ")
                            .append(map.get(Utils.VALUE2_NAME)).append("\n");
                }
                case "deleted" -> result.append("  - ").append(name).append(": ")
                        .append(map.get(Utils.VALUE_NAME)).append("\n");
                case "added" -> result.append("  + ").append(name).append(": ")
                        .append(map.get(Utils.VALUE_NAME)).append("\n");
                default -> throw new IllegalStateException("Unexpected format: " + type);
            }
        });
        result.append("}");
        return result.toString();
    }

}
