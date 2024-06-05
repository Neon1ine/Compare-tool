package hexlet.code.format;

import hexlet.code.Differ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Json {

    public static String getString(List<List<String>> output) throws Exception {
        Map<String, List<String>> map = Differ.getDiffMap(output);
        StringBuilder result = new StringBuilder();
        result.append("[");
        for (List<String> line : output) {
            String name = line.get(0).substring(2);
            if (map.containsKey(name)) {
                Object data1 = toObject(map.get(name).get(1));
                if (map.get(name).size() == 3) {
                    Object data2 = toObject(map.get(name).get(2));
                    result.append(oneDiffToStr(name, data1, data2, "changed"));
                } else if (map.get(name).get(0).charAt(0) == '-') {
                    result.append(oneDiffToStr(name, data1, "", "deleted"));
                } else if (map.get(name).get(0).charAt(0) == '+') {
                    result.append(oneDiffToStr(name, "", data1, "added"));
                }
                map.remove(name);
            } else if (map.containsKey(line.get(0))) {
                name = line.get(0);
                result.append(oneDiffToStr(name, toObject(map.get(name).get(1)), "", "unchanged"));
            }
        }
        return result.substring(0, result.length() - 1) + "\n]";
    }

    public static String oneDiffToStr(String name, Object oldData, Object newData, String type) throws Exception {
        StringBuilder result = new StringBuilder();
        result.append("\n  {\n    \"name\": \"").append(name);
        if (type.equals("unchanged")) {
            result.append("\",\n    \"oldValue\": ").append(oldData.toString());
        } else if (type.equals("added")) {
            result.append("\",\n    \"newValue\": ").append(newData.toString());
        } else if (type.equals("deleted")) {
            result.append("\",\n    \"oldValue\": ").append(oldData.toString());
        } else if (type.equals("changed")) {
            result.append("\",\n    \"newValue\": ").append(newData.toString());
            result.append(",\n    \"oldValue\": ").append(oldData.toString());
        } else {
            throw new Exception("wrong change type");
        }
        result.append(",\n    \"type\": \"").append(type).append("\"\n  },");

        return result.toString();
    }

    private static Object toObject(String str) {
        if (str.equals("true") || str.equals("false") || str.equals("null") || Plain.isNumeric(str)) {
            return str;
        } else if (str.startsWith("[")) {
            List<String> arr = List.of(str.substring(1, str.length() - 1).split(","));
            return arrayToString(arr);
        } else if (str.startsWith("{")) {
            List<String> arr = List.of(str.substring(1, str.length() - 1).split(","));
            return innerArrayToString(arr);
        } else {
            return "\"" + str + "\"";
        }
    }

    private static String arrayToString(List<String> arr) {
        StringBuilder result = new StringBuilder();
        result.append("[");
        for (String element: arr) {
            element = element.trim();
            result.append("\n      ").append(toObject(element)).append(",");
        }
        return result.substring(0, result.length() - 1) + "\n    ]";
    }

    private static String innerArrayToString(List<String> arr) {
        StringBuilder result = new StringBuilder();
        result.append("{");
        for (String element: arr) {
            element = element.trim();
            List<String> line = elemToArray(element);
            result.append("\n      ").append(toObject(line.get(0)))
                    .append(": ").append(toObject(line.get(1))).append(",");
        }
        return result.substring(0, result.length() - 1) + "\n    }";
    }

    private static List<String> elemToArray(String str) {
        List<String> result = new ArrayList<>();
        result.addAll(Arrays.asList(str.split("=")));
        return result;
    }
}
