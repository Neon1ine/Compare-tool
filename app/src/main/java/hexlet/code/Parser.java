package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.util.Map;

public class Parser {

    private static final String JSON = "json";
    private static final String YAML = "yml";

    public static Map<String, Object> parse(String content, String fileExtension) throws Exception {
        ObjectMapper mapper;
        if (fileExtension.equals(JSON)) {
            mapper = new ObjectMapper();
        } else if (fileExtension.equals(YAML)) {
            mapper = new YAMLMapper();
        } else {
            throw new Exception("this '" + fileExtension + "' file type is not supported by this program");
        }
        return mapper.readValue(content, new TypeReference<Map<String, Object>>() { });
    }

}
