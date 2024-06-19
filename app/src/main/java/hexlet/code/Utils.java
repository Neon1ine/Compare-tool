package hexlet.code;

import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Objects;

public class Utils {

    public static final String VALUE1_NAME = "value1";
    public static final String VALUE2_NAME = "value2";
    public static final String VALUE_NAME = "value";

    public static List<Map<String, Object>> makeDiffList(Map<String, Object> map1, Map<String, Object> map2) {
        List<Map<String, Object>> result = new ArrayList<>();
        findSortedKeys(map1, map2).forEach(key -> {
            String type;
            Map<String, Object> element = new LinkedHashMap<>();
            element.put("name", key);
            if (!map2.containsKey(key)) {
                type = "deleted";
                element.put(VALUE_NAME, map1.get(key));
            } else if (!map1.containsKey(key)) {
                type = "added";
                element.put(VALUE_NAME, map2.get(key));
            } else if (Objects.equals(map1.get(key), map2.get(key))) {
                type = "unchanged";
                element.put(VALUE_NAME, map1.get(key));
            } else {
                element.put(VALUE2_NAME, map2.get(key));
                element.put(VALUE1_NAME, map1.get(key));
                type = "changed";
            }

            element.put("type", type);
            result.add(element);
        });
        return result;
    }

    private static Set<String> findSortedKeys(Map<String, Object> map1, Map<String, Object> map2) {
        Set<String> keys = new TreeSet<>(Comparator.naturalOrder());
        keys.addAll(map1.keySet());
        keys.addAll(map2.keySet());
        return keys;
    }
}
