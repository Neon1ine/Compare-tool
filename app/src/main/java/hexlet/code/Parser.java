package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.util.Map;

public class Parser {

    public static Map<String, Object> parse(String content, String fileExtension) throws Exception {
        ObjectMapper mapper = switch (fileExtension) {
            case "json" -> new ObjectMapper();
            case "yaml", "yml" -> new YAMLMapper();
            default -> throw new Exception("this '" + fileExtension + "' file type is not supported by this program");
        };
        return mapper.readValue(content, new TypeReference<Map<String, Object>>() { });
    }

}
