package jackson;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

public class JsonTest {
    @Test
    public void testJson() {
        {
            String jsonStr = "{\"key1\": \"value1\", \"key2\": [1, 2, 3], \"key3\": {\"subkey\": 666}}";
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode jsonNode = objectMapper.readTree(jsonStr);
                assertEquals(jsonNode.get("key1").asText(), "value1");
                assertEquals(jsonNode.path("key1").asText(), "value1");
                assertEquals(jsonNode.path("key2").path(0).asInt(), 1);
                assertEquals(jsonNode.path("key3").path("subkey").asInt(), 666);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        {
            Map<String, Object> obj = new HashMap<>();
            obj.put("key1", "value1");
            obj.put("key2", new int[] { 1, 2, 3 });
            Map<String, Object> subObj = new HashMap<>();
            subObj.put("subkey", 666);
            obj.put("key3", subObj);

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String str = objectMapper.writeValueAsString(obj);
                System.out.println(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}