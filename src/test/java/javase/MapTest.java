package javase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

public class MapTest {
    @Test
    public void testMap() {
        Map<String, String> hmss = new HashMap<>();
        hmss.put("key1", "val1");
        hmss.put("key2", "val2");
        hmss.put("key3", "val3");

        assertFalse(hmss.isEmpty());
        assertTrue(hmss.containsKey("key1"));
        assertEquals(hmss.size(), 3);
        assertEquals(hmss.get("key2"), "val2");

        System.out.println(hmss.keySet());
        System.out.println(hmss.values());

        for (Map.Entry<String, String> entry : hmss.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }

        hmss.forEach((k, v) -> System.out.println(k + " => " + v));
        hmss.remove("key2");
        assertEquals(hmss.get("key2"), null);

        Map<String, String> tmss = new TreeMap<>();
        assertEquals(tmss.getOrDefault("key6", "val6"), "val6");
    }
}