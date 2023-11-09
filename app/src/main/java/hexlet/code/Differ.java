package hexlet.code;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Differ {
    public static String findDiff() throws Exception {
        return generate(App.getFilePath1(), App.getFilePath2());
    }

    public static String generate(Path path1, Path path2) throws Exception {
        Map<String, Object> finalData = new HashMap<>();
        Map<String, Object> contents1 = Utils.parse(path1);
        Map<String, Object> contents2 = Utils.parse(path2);
        contents1.forEach((key, value) -> {
            if (contents2.containsKey(key)) {
                if (contents1.get(key).equals(contents2.get(key))) {
                    //same keys, same values
                    finalData.put("  " + key, contents1.get(key));
                } else {
                    //same keys, different values
                    finalData.put("- " + key, contents1.get(key));
                    finalData.put("+ " + key, contents2.get(key));
                }
            } else {
                //key exist in first map, but doesn't exist in second
                finalData.put("- " + key, contents1.get(key));
            }
        });

        contents2.forEach((key, value) -> {
            if (!contents1.containsKey(key)) {
                //key exist in second map, but doesn't exist in first
                finalData.put("+ " + key, contents2.get(key));
            }
        });

        StringBuilder result = new StringBuilder();
        result.append("{");
        finalData.entrySet().stream()
                .sorted(Comparator.comparing(entry -> entry.getKey().substring(2)))
                .forEach(entry -> result.append("\n  ").append(entry.getKey()).append(": ").append(entry.getValue()));
        result.append("\n}");
        return result.toString();
    }
}
