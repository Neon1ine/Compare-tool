package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Parser {

    private static final String JSON = "json";
    private static final String YAML = "yml";

    public static Map<String, Object> parse(Path path) throws Exception {
        if (!Files.exists(path)) {
            throw new Exception("file '" + path + "' does not exist");
        }
        String fileExtension = findFileExtension(path);
        ObjectMapper mapper;
        if (fileExtension.equals(JSON)) {
            mapper = new ObjectMapper();
        } else if (fileExtension.equals(YAML)) {
            mapper = new YAMLMapper();
        } else {
            throw new Exception("this '" + fileExtension + "' file type is not supported by this program");
        }
        String content = Files.readString(path);
        return mapper.readValue(content, new TypeReference<Map<String, Object>>() { });
    }

    public static String findFileExtension(Path path) {
        String str = path.toString();
        int index = str.lastIndexOf('.');
        return str.substring(index + 1);
    }

}
