package hexlet.code;

import java.nio.file.Files;
import java.nio.file.Path;

import java.nio.file.Paths;
import java.util.Map;
import java.util.List;

public class Differ {

    public static String generate(String path1, String path2) throws Exception {
        return generate(path1, path2, "stylish");
    }

    public static String generate(String path1, String path2, String format) throws Exception {
        Path firstPath = fixPath(path1);
        Path secondPath = fixPath(path2);
        Map<String, Object> contents1 = Parser.parse(Files.readString(firstPath), findFileExtension(firstPath));
        Map<String, Object> contents2 = Parser.parse(Files.readString(secondPath), findFileExtension(secondPath));
        List<Map<String, Object>> output = Utils.makeDiffList(contents1, contents2);
        return Formatter.convert(output, format);
    }

    private static String findFileExtension(Path path) {
        String str = path.toString();
        int index = str.lastIndexOf('.');
        return str.substring(index + 1);
    }

    private static Path fixPath(String path) {
        //path = "./src/test/resources/" + path;
        return Paths.get(path).toAbsolutePath().normalize();
    }
}
