package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Parser {

    private static final String JSON = "json";
    private static final String YAML = "yml";

    public static Map<String, Object> makeDiffMap(Path path1, Path path2) throws Exception {
        findFileExtension(path1);
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> contents1 = fileToMap(path1);
        Map<String, Object> contents2 = fileToMap(path2);
        contents1.forEach((key, value) -> {
            if (contents2.containsKey(key)) {
                if (contents1.get(key).equals(contents2.get(key))) {
                    //same keys, same values
                    result.put("  " + key, contents1.get(key));
                } else {
                    //same keys, different values
                    result.put("- " + key, contents1.get(key));
                    result.put("+ " + key, contents2.get(key));
                }
            } else {
                //key exist in first map, but doesn't exist in second
                result.put("- " + key, contents1.get(key));
            }
        });

        contents2.forEach((key, value) -> {
            if (!contents1.containsKey(key)) {
                //key exist in second map, but doesn't exist in first
                result.put("+ " + key, contents2.get(key));
            }
        });
        return result;
    }

    public static Map<String, Object> fileToMap(Path path) throws Exception {
        String fileExtension = findFileExtension(path);
        if (fileExtension.equals(JSON)) {
            return Utils.parse(path);
        }
        if (fileExtension.equals(YAML)) {
            return yamlFileToMap(path);
        }
        throw new Exception("this \"" + fileExtension + "\" file type is not supported by this program");
    }

    public static Map<String, Object> yamlFileToMap(Path path) throws Exception {
        ObjectMapper mapper = new YAMLMapper();
        String content = Files.readString(path);
        Map<String, Object> result = mapper.readValue(content, new TypeReference<Map<String, Object>>() {
        });
        return result;
    }

    public static String findFileExtension(Path path) {
        String str = path.toString();
        int index = str.lastIndexOf('.');
        return str.substring(index + 1);
    }

}
