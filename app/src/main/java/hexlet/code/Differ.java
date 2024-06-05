package hexlet.code;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Differ {

    public static String findDiff() throws Exception {
        return generate(App.getFilePath1(), App.getFilePath2(), App.getFormat());
    }

    public static String generate(Path path1, Path path2, String format) throws Exception {
        Map<String, Object> contents1 = Parser.parse(path1);
        Map<String, Object> contents2 = Parser.parse(path2);
        List<List<String>> output = sortList(makeDiffList(contents1, contents2));
        return Formatter.convert(output, format);
    }

    public static List<List<String>> sortList(List<List<String>> list) {
        list.sort(Comparator.comparing(line -> line.get(0).substring(2)));
        return list;
    }

    public static List<List<String>> makeDiffList(Map<String, Object> map1, Map<String, Object> map2) {
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
