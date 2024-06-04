package hexlet.code;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Differ {

    public static String findDiff() throws Exception {
        return generate(App.getFilePath1(), App.getFilePath2());
    }

    public static String generate(Path path1, Path path2) throws Exception {
        Map<String, Object> contents1 = Parser.parse(path1);
        Map<String, Object> contents2 = Parser.parse(path2);
        List<List<String>> output = makeDiffMap(contents1, contents2);
        output.sort(Comparator.comparing(line -> line.get(0).substring(2)));
        return listToString(output);
    }

    private static String listToString(List<List<String>> output) {
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

    public static List<List<String>> makeDiffMap(Map<String, Object> map1, Map<String, Object> map2) {
        List<List<String>> result = new ArrayList<>();
        map1.forEach((key, value) -> {
            Object valueNew = map2.get(key);
            if (value == null) {
                value = "null";
            }
            if (valueNew == null) {
                valueNew = "null";
            }
            if (map2.containsKey(key)) {
                if (value.equals(valueNew)) {
                    //same keys, same values
                    result.add(getLine(key, value));
                } else {
                    //same keys, different values
                    result.add(getLine("- " + key, value));
                    result.add(getLine("+ " + key, valueNew));
                }
            } else {
                //key exist in first map, but doesn't exist in second
                result.add(getLine("- " + key, value));
            }
        });

        map2.forEach((key, value) -> {
            if (!map1.containsKey(key)) {
                //key exist in second map, but doesn't exist in first
                result.add(getLine("+ " + key, value));
            }
        });
        return result;
    }

    public static List<String> getLine(String key, Object value) {
        List<String> oneLine = new ArrayList<>();
        oneLine.add(key);
        oneLine.add(": ");
        if (value == null) {
            value = "null";
        }
        oneLine.add(value.toString());
        return oneLine;
    }
}
