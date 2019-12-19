package javase;

import org.junit.Test;

import java.util.Arrays;
import java.util.NavigableMap;
import java.util.TreeMap;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class NavigableMapTest {
    @Test
    public void testNavigableMap() {
        for (final NavigableMap<String, String> m : Arrays.asList(
                new TreeMap<String, String>()
        )) {
            for (int i = 0; i < 10; i++) {
                m.put("key" + i, "val" + i);
            }
            assertEquals(m.lowerKey("key6"), "key5");   // <
            assertEquals(m.higherKey("key6"), "key7");  // >
            assertEquals(m.floorKey("key6"), "key6");   // <=
            assertEquals(m.ceilingKey("key6"), "key6"); // >=
            assertEquals(m.lowerEntry("key6").getKey(), "key5");
            assertEquals(m.higherEntry("key6").getKey(), "key7");
            assertEquals(m.floorEntry("key6").getKey(), "key6");
            assertEquals(m.ceilingEntry("key6").getKey(), "key6");

            m.put("key65", "val65");
            m.put("key67", "val67");
            assertEquals(m.floorKey("key66"), "key65");
            assertEquals(m.ceilingKey("key66"), "key67");
            assertEquals(m.floorEntry("key66").getKey(), "key65");
            assertEquals(m.ceilingEntry("key66").getKey(), "key67");
            m.remove("key65");
            m.remove("key67");

            assertEquals(m.pollFirstEntry().getKey(), "key0");
            assertEquals(m.pollLastEntry().getKey(), "key9");

            final NavigableMap dm = m.descendingMap();
            assertArrayEquals(dm.keySet().toArray(), new String[]{
                    "key8", "key7", "key6", "key5", "key4", "key3", "key2", "key1"
            });

            final NavigableMap hm1 = m.headMap("key3", true);
            final NavigableMap hm2 = m.headMap("key3", false);
            final NavigableMap tm1 = m.tailMap("key7", true);
            final NavigableMap tm2 = m.tailMap("key7", false);
            final NavigableMap sm = m.subMap("key3", true, "key7", false);
            assertArrayEquals(hm1.keySet().toArray(), new String[]{"key1", "key2", "key3"});
            assertArrayEquals(hm2.keySet().toArray(), new String[]{"key1", "key2"});
            assertArrayEquals(tm1.keySet().toArray(), new String[]{"key7", "key8"});
            assertArrayEquals(tm2.keySet().toArray(), new String[]{"key8"});
            assertArrayEquals(sm.keySet().toArray(), new String[]{"key3", "key4", "key5", "key6"});
        }
    }
}
