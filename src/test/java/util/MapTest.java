package util;

import org.junit.Test;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.*;

public class MapTest {
    @Test
    public void testMap() {
        {
            Map<String, String> map = new HashMap<>(Map.of(
                    "key0", "val0", "key1", "val1", "key2", "val2", "key3", "val3"
            ));
            assertEquals(map.size(), 4);
            assertFalse(map.isEmpty());
            assertTrue(map.containsKey("key3"));

            assertEquals(map.get("key3"), "val3");
            assertEquals(map.get("key6"), null);
            assertEquals(map.getOrDefault("key3", "defaultValue"), "val3");
            assertEquals(map.getOrDefault("key6", "defaultValue"), "defaultValue");

            assertThat(map.keySet(), equalTo(Set.of("key0", "key1", "key2", "key3")));
            assertThat(map.values(), hasItems("val0", "val1", "val2", "val3"));

            for (Map.Entry<String, String> entry : map.entrySet()) {
                System.out.println(entry.getKey() + " => " + entry.getValue());
            }
            map.forEach((k, v) -> System.out.println(k + " => " + v));

            map.clear();
            assertTrue(map.isEmpty());
        }
        {
            Map<String, String> map = new HashMap<>();
            map.put("key0", "val0");
            map.putAll(Map.of("key1", "val1", "key2", "val2"));
            assertEquals(map.putIfAbsent("key3", "val3"), null);
            assertEquals(map.putIfAbsent("key3", "val33"), "val3");
            assertThat(map, equalTo(Map.of(
                    "key0", "val0", "key1", "val1", "key2", "val2", "key3", "val3"
            )));
        }
        {
            Map<String, String> map = new HashMap<>(Map.of(
                    "key0", "val0", "key1", "val1", "key2", "val2", "key3", "val3"
            ));
            assertEquals(map.remove("errorKey"), null);
            assertEquals(map.remove("key0"), "val0");
            assertFalse(map.remove("key1", "errorValue"));
            assertTrue(map.remove("key1", "val1"));
            assertThat(map, equalTo(Map.of(
                    "key2", "val2", "key3", "val3"
            )));
        }
        {
            Map<String, String> map = new HashMap<>(Map.of(
                    "key0", "val0", "key1", "val1", "key2", "val2", "key3", "val3"
            ));
            assertEquals(map.replace("errorKey", "replaceValue"), null);
            assertEquals(map.replace("key0", "replaceValue"), "val0");
            assertFalse(map.replace("key1", "errorValue", "replaceValue"));
            assertTrue(map.replace("key1", "val1", "replaceValue"));
            assertThat(map, equalTo(Map.of(
                    "key0", "replaceValue", "key1", "replaceValue", "key2", "val2", "key3", "val3"
            )));
        }
        {
            Map<String, String> map = new HashMap<>(Map.of(
                    "key0", "val0", "key1", "val1", "key2", "val2", "key3", "val3"
            ));
            map.replaceAll((k, v) -> k + v);
            assertThat(map, equalTo(Map.of(
                    "key0", "key0val0", "key1", "key1val1", "key2", "key2val2", "key3", "key3val3"
            )));
        }
        {
            Map<String, String> map = new HashMap<>(Map.of(
                    "key0", "val0", "key1", "val1", "key2", "val2"
            ));
            assertEquals(map.compute("key0", (k, v) -> k + v), "key0val0");
            assertEquals(map.computeIfPresent("key1", (k, v) -> k + v), "key1val1");
            assertEquals(map.computeIfPresent("key6", (k, v) -> k + v), null);
            assertEquals(map.computeIfAbsent("key2", k -> k + k.replace("key", "val")), "val2");
            assertEquals(map.computeIfAbsent("key3", k -> k + k.replace("key", "val")), "key3val3");
            assertThat(map, equalTo(Map.of(
                    "key0", "key0val0", "key1", "key1val1", "key2", "val2", "key3", "key3val3"
            )));
        }
        {
            Map<String, String> map = new HashMap<>(Map.of(
                    "key0", "val0", "key1", "val1", "key2", "val2"
            ));
            assertEquals(map.merge("key0", "newVal", (oldValue, newValue) -> (oldValue + "->" + newValue)), "val0->newVal");
            assertEquals(map.merge("key3", "newVal", (oldValue, newValue) -> (oldValue + "->" + newValue)), "newVal");
            assertThat(map, equalTo(Map.of(
                    "key0", "val0->newVal", "key1", "val1", "key2", "val2", "key3", "newVal"
            )));
        }
    }

    @Test
    public void testIdentityHashMapMap() {
        // 判断同一个 key 的条件是 key == e.key，必须是同一个对象才认为相等
        IdentityHashMap<String, String> m = new IdentityHashMap<>();
        for (int i = 0; i < 5; i++) {
            m.put("key" + i, "val" + i);
        }
        for (int i = 0; i < 5; i++) {
            m.put("key" + i, "val" + i + i);
        }
        System.out.println(m);
        System.out.println(m.get("key0"));

        String key = "key0";
        m.put(key, "val000");
        assertEquals(m.get(key), "val000");
    }
}
