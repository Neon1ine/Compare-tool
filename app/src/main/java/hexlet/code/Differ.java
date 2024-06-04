package hexlet.code;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Map;

public class Differ {
    public static String findDiff() throws Exception {
        return generate(App.getFilePath1(), App.getFilePath2());
    }

    public static String generate(Path path1, Path path2) throws Exception {
        Map<String, Object> outputMap = Parser.makeDiffMap(path1, path2);
        return mapToString(outputMap);
    }

    private static String mapToString(Map<String, Object> outputMap) {
        StringBuilder result = new StringBuilder();
        result.append("{");
        outputMap.entrySet().stream()
                .sorted(Comparator.comparing(entry -> entry.getKey().substring(2)))
                .forEach(entry -> result.append("\n  ").append(entry.getKey()).append(": ").append(entry.getValue()));
        result.append("\n}");
        return result.toString();
    }
}
