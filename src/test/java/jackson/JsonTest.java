package jackson;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

class MyObj1 {
    private String name;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

class MyObj2 {
    private String key1;
    private List<Integer> key2;
    private Map<String, Integer> key3;

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public List<Integer> getKey2() {
        return key2;
    }

    public void setKey2(List<Integer> key2) {
        this.key2 = key2;
    }

    public Map<String, Integer> getKey3() {
        return key3;
    }

    public void setKey3(Map<String, Integer> key3) {
        this.key3 = key3;
    }
}

class MyObj3 {
    MyObj1 obj1;
    MyObj2 obj2;

    public MyObj1 getObj1() {
        return obj1;
    }

    public void setObj1(MyObj1 obj1) {
        this.obj1 = obj1;
    }

    public MyObj2 getObj2() {
        return obj2;
    }

    public void setObj2(MyObj2 obj2) {
        this.obj2 = obj2;
    }
}

public class JsonTest {
    @Test
    public void testJsonNode() {
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

    @Test
    public void testUnmarshall() throws Exception {
        {
            String jsonStr = "{\"name\": \"hatlonely\", \"id\": 123}";
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            MyObj1 obj = objectMapper.readValue(jsonStr, MyObj1.class);
            System.out.println(obj.getName());
            System.out.println(obj.getId());

            System.out.println(objectMapper.writeValueAsString(obj));
        }
        {
            String jsonStr = "{\"key1\": \"value1\", \"key2\": [1, 2, 3], \"key3\": {\"subkey\": 666}}";
            ObjectMapper objectMapper = new ObjectMapper();

            MyObj2 obj = objectMapper.readValue(jsonStr, MyObj2.class);
            System.out.println(obj.getKey1());
            System.out.println(obj.getKey2());
            System.out.println(obj.getKey3());

            System.out.println(objectMapper.writeValueAsString(obj));
        }
        {
            String jsonStr = "{\"obj1\": {\"name\": \"hatlonely\", \"id\": 123}, \"obj2\": {\"key1\": \"value1\", \"key2\": [1, 2, 3], \"key3\": {\"subkey\": 666}}}";
            ObjectMapper objectMapper = new ObjectMapper();

            MyObj3 obj = objectMapper.readValue(jsonStr, MyObj3.class);
            System.out.println(obj.getObj1().getName());
            System.out.println(obj.getObj1().getId());
            System.out.println(obj.getObj2().getKey1());
            System.out.println(obj.getObj2().getKey2());
            System.out.println(obj.getObj2().getKey3());

            System.out.println(objectMapper.writeValueAsString(obj));
        }
    }
}
