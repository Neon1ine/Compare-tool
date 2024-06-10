package hexlet.code;

import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Utils {

    public static List<Map<String, Object>> makeDiffList(Map<String, Object> map1, Map<String, Object> map2) {
        List<Map<String, Object>> result = new ArrayList<>();
        findSortedKeys(map1, map2).forEach(key -> {
            //some Objects might == null, so they will be changed to Strings "null"
            if (map1.containsKey(key) && map1.get(key) == null) {
                map1.put(key, "null");
            }
            if (map2.containsKey(key) && map2.get(key) == null) {
                map2.put(key, "null");
            }
            String type;
            Map<String, Object> element = new LinkedHashMap<>();
            element.put("name", key);
            if (map2.get(key) == null) {
                //key exist in first map, but doesn't exist in second
                type = "deleted";
                element.put("oldValue", map1.get(key));
            } else if (map1.get(key) == null) {
                //key exist in second map, but doesn't exist in first
                type = "added";
                element.put("newValue", map2.get(key));
            } else {
                //key exist in both maps
                if (map1.get(key).equals(map2.get(key))) {
                    //same keys, same values
                    type = "unchanged";
                    element.put("oldValue", map1.get(key));
                } else {
                    //same keys, different values
                    element.put("newValue", map2.get(key));
                    element.put("oldValue", map1.get(key));
                    type = "changed";
                }
            }
            element.put("type", type);
            result.add(element);
        });
        return refactorNullValues(result);
    }

    private static List<Map<String, Object>> refactorNullValues(List<Map<String, Object>> list) {
        list.forEach(element -> {
            if (element.containsKey("newValue") && element.get("newValue").equals("null")) {
                element.put("newValue", null);
            }
            if (element.containsKey("oldValue") && element.get("oldValue").equals("null")) {
                element.put("oldValue", null);
            }
        });
        return list;
    }

    private static Set<String> findSortedKeys(Map<String, Object> map1, Map<String, Object> map2) {
        Set<String> keys = new TreeSet<>(Comparator.naturalOrder());
        keys.addAll(map1.keySet());
        keys.addAll(map2.keySet());
        return keys;
    }
}
