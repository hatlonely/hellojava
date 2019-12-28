package util;

import org.junit.Test;

import java.util.Arrays;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class SortedMapTest {
    @Test
    public void testSortedMap() {
        for (SortedMap<String, String> m : Arrays.asList(
                new TreeMap<String, String>()
        )) {
            for (int i = 0; i < 5; i++) {
                m.put("key" + i, "val" + i);
            }
            System.out.println(m);
            assertEquals(m.firstKey(), "key0");
            assertEquals(m.lastKey(), "key4");

            SortedMap hm = m.headMap("key2");
            SortedMap tm = m.tailMap("key3");
            SortedMap sm = m.subMap("key2", "key3");
            System.out.println(hm);
            System.out.println(tm);
            System.out.println(sm);
            assertArrayEquals(hm.keySet().toArray(), new String[]{"key0", "key1"});
            assertArrayEquals(tm.keySet().toArray(), new String[]{"key3", "key4"});
            assertArrayEquals(sm.keySet().toArray(), new String[]{"key2"});
        }
    }
}
