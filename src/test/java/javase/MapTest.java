package javase;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

public class MapTest {
    @Test
    public void testMap() {
        for (final Map<String, String> m : Arrays.asList(
                new TreeMap<String, String>(),
                new HashMap<String, String>(),
                new Hashtable<String, String>(),
                new ConcurrentHashMap<String, String>()
        )) {
            assertTrue(m.isEmpty());
            for (int i = 0; i < 5; i++) {
                m.put("key" + i, "val" + i);
            }
            System.out.println(m);

            for (int i = 0; i < 5; i++) {
                assertTrue(m.containsKey("key" + i));
                assertEquals(m.get("key" + i), "val" + i);
            }
            assertEquals(m.size(), 5);
            assertFalse(m.isEmpty());
            assertFalse(m.containsKey("key6"));

            assertEquals(m.remove("key3"), "val3");
            assertEquals(m.remove("key3"), null);

            m.putAll(Map.of(
                    "key2", "val2",
                    "key3", "val3",
                    "key4", "val4"
            ));
            System.out.println(m);
            final Set keys = m.keySet();
            for (int i = 0; i < 5; i++) {
                assertTrue(keys.contains("key" + i));
            }
            final Collection vals = m.values();
            for (int i = 0; i < 5; i++) {
                assertTrue(vals.contains("val" + i));
            }

            final Set<Map.Entry<String, String>> entries = m.entrySet();
            for (final Map.Entry it : entries) {
                System.out.println(it.getKey() + "=>" + it.getValue());
            }
            m.forEach((k, v) -> System.out.println(k + " => " + v));

            assertEquals(m.getOrDefault("key3", "val33"), "val3");
            assertEquals(m.getOrDefault("key9", "val99"), "val99");
            assertEquals(m.putIfAbsent("key3", "val33"), "val3");   // 无值才插入
            assertEquals(m.replace("key3", "val33"), "val3");       // 有值才替换
            assertEquals(m.replace("key9", "val99"), null);
            assertEquals(m.get("key9"), null);
            assertEquals(m.get("key3"), "val33");
            assertFalse(m.replace("key3", "val3", "val33"));    // 老值相等才修改
            assertTrue(m.replace("key3", "val33", "val3"));

            assertEquals(m.compute("key3", (k, v) -> (k + v)), "key3val3");
            assertEquals(m.get("key3"), "key3val3");
            assertEquals(m.computeIfPresent("key5", (k, v) -> (k + v)), null);
            assertEquals(m.computeIfAbsent("key5", (k) -> (k.replace("key", "val"))), "val5");

            assertEquals(m.merge("key5", "nv", (ov, nv) -> (ov + nv)), "val5nv");
        }
    }
}
