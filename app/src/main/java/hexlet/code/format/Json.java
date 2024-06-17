package hexlet.code.format;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.Utils;

import java.util.List;
import java.util.Map;

public class Json {

    public static String getString(List<Map<String, Object>> output) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(fixJsonOutput(output));
    }

    private static List<Map<String, Object>> fixJsonOutput(List<Map<String, Object>> output) {
        output.forEach(map -> {
            String type = map.get("type").toString();
            String defaultValueName = Utils.VALUE_NAME;
            if (map.containsKey(defaultValueName) && type.equals("added")) {
                map.put("newValue", map.get(defaultValueName));
                map.remove(defaultValueName);
            } else if (map.containsKey(defaultValueName) && (type.equals("deleted") || type.equals("unchanged"))) {
                map.put("oldValue", map.get(defaultValueName));
                map.remove(defaultValueName);
            }

            String valueName1 = Utils.VALUE1_NAME;
            if (map.containsKey(valueName1)) {
                map.put("oldValue", map.get(valueName1));
                map.remove(valueName1);
            }
            String valueName2 = Utils.VALUE2_NAME;
            if (map.containsKey(valueName2)) {
                map.put("newValue", map.get(valueName2));
                map.remove(valueName2);
            }
        });
        return output;
    }

}
