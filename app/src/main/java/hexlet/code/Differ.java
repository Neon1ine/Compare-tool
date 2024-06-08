package hexlet.code;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Differ {

    public static String generate(String path1, String path2) throws Exception {
        Path firstPath = Path.of(path1);
        Path secondPath = Path.of(path2);
        Map<String, Object> contents1 = Parser.parse(Files.readString(firstPath), findFileExtension(firstPath));
        Map<String, Object> contents2 = Parser.parse(Files.readString(secondPath), findFileExtension(secondPath));
        List<Map<String, Object>> output = makeDiffList(contents1, contents2);
        return Formatter.convert(sortList(output), "stylish");
    }

    public static String generate(String path1, String path2, String format) throws Exception {
        Path firstPath = Path.of(path1);
        Path secondPath = Path.of(path2);
        Map<String, Object> contents1 = Parser.parse(Files.readString(firstPath), findFileExtension(firstPath));
        Map<String, Object> contents2 = Parser.parse(Files.readString(secondPath), findFileExtension(secondPath));
        List<Map<String, Object>> output = makeDiffList(contents1, contents2);
        return Formatter.convert(sortList(output), format);
    }

    public static List<Map<String, Object>> sortList(List<Map<String, Object>> list) {
        list.sort(Comparator.comparing(line -> line.get("name").toString()));
        return list;
    }

    public static List<Map<String, Object>> makeDiffList(Map<String, Object> map1, Map<String, Object> map2) {
        List<Map<String, Object>> result = new ArrayList<>();
        Set<String> keys = new TreeSet<>(Comparator.naturalOrder());
        keys.addAll(map1.keySet());
        keys.addAll(map2.keySet());

        keys.forEach(key -> {
            Object value1 = null;
            if (map1.containsKey(key)) {
                value1 = map1.get(key);
            }
            Object value2 = null;
            if (map2.containsKey(key)) {
                value2 = map2.get(key);
            }
            if (!map2.containsKey(key)) {
                //key exist in first map, but doesn't exist in second
                result.add(makeElement(key, value1, "", "deleted"));
            } else if (!map1.containsKey(key)) {
                //key exist in second map, but doesn't exist in first
                result.add(makeElement(key, "", value2, "added"));
            } else {
                //key exist in both maps
                if (value1 != null && value1.equals(value2)) {
                    //same keys, same values
                    result.add(makeElement(key, value1, value2, "unchanged"));
                } else if (value1 == null && value2 == null) {
                    result.add(makeElement(key, value1, value2, "unchanged"));
                } else {
                    //same keys, different values
                    result.add(makeElement(key, value1, value2, "changed"));
                }
            }
        });
        return result;
    }

    public static Map<String, Object> makeElement(String key, Object value1, Object value2, String type) {
        Map<String, Object> element = new LinkedHashMap<>();
        element.put("name", key);
        if (type.equals("changed") || type.equals("added")) {
            element.put("newValue", value2);
        }
        if (!type.contains("added")) {
            element.put("oldValue", value1);
        }
        element.put("type", type);
        return element;
    }

    public static String findFileExtension(Path path) {
        String str = path.toString();
        int index = str.lastIndexOf('.');
        return str.substring(index + 1);
    }
}
