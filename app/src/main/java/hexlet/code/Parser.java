package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.util.Map;

public class Parser {

    public static Map<String, Object> parse(String content, String fileExtension) throws Exception {
        ObjectMapper mapper;
        //can't use switch: "Inner assignments should be avoided"
        if (fileExtension.equals("json")) {
            mapper = new ObjectMapper();
        } else if (fileExtension.equals("yaml") || fileExtension.equals("yml")) {
            mapper = new YAMLMapper();
        } else {
            throw new Exception("this '" + fileExtension + "' file type is not supported by this program");
        }
        return mapper.readValue(content, new TypeReference<Map<String, Object>>() { });
    }

}
