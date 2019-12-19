package javase;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class MapTest {
    @Test
    public void testMap2() {
        for (final Map<String, String> m : Arrays.asList(
                new TreeMap<String, String>(),
                new HashMap<String, String>(),
                new Hashtable<String, String>()
        )) {

        }
    }

    @Test
    public void testMap() {
        final Map<String, String> hmss = new HashMap<>();
        hmss.put("key1", "val1");
        hmss.put("key2", "val2");
        hmss.put("key3", "val3");

        assertFalse(hmss.isEmpty());
        assertTrue(hmss.containsKey("key1"));
        assertEquals(hmss.size(), 3);
        assertEquals(hmss.get("key2"), "val2");

        System.out.println(hmss.keySet());
        System.out.println(hmss.values());

        for (final Map.Entry<String, String> entry : hmss.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }

        hmss.forEach((k, v) -> System.out.println(k + " => " + v));
        hmss.remove("key2");
        assertEquals(hmss.get("key2"), null);

        final Map<String, String> tmss = new TreeMap<>();
        assertEquals(tmss.getOrDefault("key6", "val6"), "val6");
    }
}
