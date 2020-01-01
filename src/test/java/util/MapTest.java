package util;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
            assertTrue(map.containsValue("val3"));

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
    public void testSortedMap() {
        SortedMap<String, String> map = new TreeMap<>(Map.of(
                "key0", "val0", "key1", "val1", "key2", "val2",
                "key3", "val3", "key4", "val4"
        ));

        assertEquals(map.firstKey(), "key0");
        assertEquals(map.lastKey(), "key4");
        assertThat(map.headMap("key2").keySet(), equalTo(Set.of("key0", "key1")));
        assertThat(map.tailMap("key3").keySet(), equalTo(Set.of("key3", "key4")));
        assertThat(map.subMap("key2", "key3").keySet(), equalTo(Set.of("key2")));
    }

    @Test
    public void testNavigableMap() {
        {
            NavigableMap<String, String> map = new TreeMap<>(Map.of(
                    "key0", "val0", "key1", "val1", "key2", "val2",
                    "key3", "val3", "key4", "val4"
            ));

            assertEquals(map.lowerKey("key3"), "key2");
            assertEquals(map.higherKey("key3"), "key4");
            assertEquals(map.floorKey("key3"), "key3");
            assertEquals(map.ceilingKey("key3"), "key3");
            assertEquals(map.lowerEntry("key3").getKey(), "key2");
            assertEquals(map.higherEntry("key3").getKey(), "key4");
            assertEquals(map.floorEntry("key3").getKey(), "key3");
            assertEquals(map.ceilingEntry("key3").getKey(), "key3");
            map.remove("key3");
            assertEquals(map.floorKey("key3"), "key2");
            assertEquals(map.ceilingKey("key3"), "key4");
            assertEquals(map.floorEntry("key3").getKey(), "key2");
            assertEquals(map.ceilingEntry("key3").getKey(), "key4");
        }
        {
            NavigableMap<String, String> map = new TreeMap<>(Map.of(
                    "key0", "val0", "key1", "val1", "key2", "val2",
                    "key3", "val3", "key4", "val4"
            ));

            assertEquals(map.pollFirstEntry().getKey(), "key0");
            assertArrayEquals(map.keySet().toArray(), new String[]{"key1", "key2", "key3", "key4"});
            assertEquals(map.pollLastEntry().getKey(), "key4");
            assertArrayEquals(map.keySet().toArray(), new String[]{"key1", "key2", "key3"});
        }
        {
            NavigableMap<String, String> map = new TreeMap<>(Map.of(
                    "key0", "val0", "key1", "val1", "key2", "val2",
                    "key3", "val3", "key4", "val4"
            ));

            assertArrayEquals(map.headMap("key2", true).keySet().toArray(), new String[]{"key0", "key1", "key2"});
            assertArrayEquals(map.tailMap("key3", false).keySet().toArray(), new String[]{"key4"});
            assertArrayEquals(map.subMap("key2", false, "key3", true).keySet().toArray(), new String[]{"key3"});
        }
    }

    @Test
    public void testHashtable() {
        {
            Map<Integer, Integer> map = new HashMap<>();
            assertDoesNotThrow(() -> map.put(null, 1));
            assertDoesNotThrow(() -> map.put(1, null));
        }
        {
            Map<Integer, Integer> map = new Hashtable();
            assertThrows(NullPointerException.class, () -> map.put(null, 1));
            assertThrows(NullPointerException.class, () -> map.put(1, null));
        }
    }

    @Test
    public void testIdentityHashMap() {
        Map<String, String> map = new IdentityHashMap<>();
        String key1 = new String("key1");
        map.put(key1, "val1");
        assertFalse(key1 == "key1");
        assertTrue(key1.equals("key1"));
        assertEquals(map.get(key1), "val1");
        assertEquals(map.get("key1"), null);
    }

    @Test
    public void testWeakHashMap() {
        {
            Map<String, String> map = new WeakHashMap<>();
            String key1 = new String("key1");
            map.put(key1, "val1");
            assertEquals(map.get("key1"), "val1");
            key1 = null;
            System.gc();
            assertEquals(map.get("key1"), null);
        }
        {
            Map<String, String> map = new WeakHashMap<>();
            String val1 = new String("val1");
            map.put("key1", val1);
            assertEquals(map.get("key1"), "val1");
            val1 = null;
            System.gc();
            assertEquals(map.get("key1"), "val1");
        }
    }
}
