package guava;

import com.google.common.collect.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MultiMapTest {
    @Test
    public void testMultimap() {
        final Multimap<String, String> hm = HashMultimap.create();
        final Multimap<String, String> tm = TreeMultimap.create();
        final Multimap<String, String> lhm = LinkedHashMultimap.create();
        final Multimap<String, String> mta = MultimapBuilder.treeKeys().arrayListValues().build();
        final Multimap<String, String> mtl = MultimapBuilder.hashKeys().linkedListValues().build();

        for (final Multimap<String, String> m : ImmutableList.of(
                hm, tm, lhm, mta, mtl
        )) {
            assertTrue(m.isEmpty());
            for (int i = 0; i < 10; i++) {
                m.put("key" + i, "val" + i);
            }
            m.put("key6", "val66");
            assertTrue(m.containsKey("key6"));
            assertTrue(m.containsEntry("key6", "val6"));
            assertTrue(m.containsValue("val66"));
            assertEquals(m.size(), 11);
            assertTrue(m.get("key6").contains("val6"));
            assertTrue(m.get("key6").contains("val66"));
            assertEquals(m.get("key6").size(), 2);
        }
    }
}
