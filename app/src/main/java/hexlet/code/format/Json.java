package hexlet.code.format;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;


public class Json {

    public static String getString(List<Map<String, Object>> output) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(output);
    }

}
