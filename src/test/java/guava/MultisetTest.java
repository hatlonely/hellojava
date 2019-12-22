package guava;

import com.google.common.collect.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class MultisetTest {
    @Test
    public void testMultiset() {
        final Multiset<String> hs = HashMultiset.create();
        final Multiset<String> ts = TreeMultiset.create();
        final Multiset<String> lhm = LinkedHashMultiset.create();
        final Multiset<String> chm = ConcurrentHashMultiset.create();

        for (final Multiset<String> set : ImmutableList.of(hs, ts, lhm, chm)) {
            for (int i = 0; i < 10; i++) {
                set.add("key" + i);
            }
            assertEquals(set.size(), 10);
            assertTrue(set.contains("key6"));
            assertFalse(set.contains("key20"));
            assertEquals(set.count("key1"), 1);
            assertEquals(set.add("key1"), true);
            assertEquals(set.count("key1"), 2);

            assertEquals(set.add("key66", 6), 0);
            assertEquals(set.count("key66"), 6);
            assertEquals(set.add("key66", 1), 6);
            assertEquals(set.count("key66"), 7);

            assertEquals(set.setCount("key77", 6), 0);
            assertEquals(set.count("key77"), 6);
            assertEquals(set.setCount("key77", 7), 6);
            assertEquals(set.count("key77"), 7);

            assertEquals(set.remove("key66", 2), 7);
            assertEquals(set.count("key66"), 5);
            assertEquals(set.remove("key66", 100), 5);
            assertEquals(set.count("key66"), 0);
            assertFalse(set.contains("key66"));
        }
    }

    @Test
    public void testSortedMultiset() {
        final SortedMultiset<String> set = TreeMultiset.create();

        for (int i = 0; i < 10; i++) {
            set.add("key" + i);
        }

        assertEquals(set.firstEntry().getElement(), "key0");
        assertEquals(set.firstEntry().getCount(), 1);
        assertEquals(set.lastEntry().getElement(), "key9");
        assertEquals(set.lastEntry().getCount(), 1);

        final SortedMultiset<String> hm = set.headMultiset("key3", BoundType.OPEN);
        final SortedMultiset<String> sm = set.subMultiset("key3", BoundType.CLOSED, "key7", BoundType.OPEN);
        final SortedMultiset<String> tm = set.tailMultiset("key7", BoundType.CLOSED);
        assertArrayEquals(hm.toArray(), new String[]{"key0", "key1", "key2"});
        assertArrayEquals(sm.toArray(), new String[]{"key3", "key4", "key5", "key6"});
        assertArrayEquals(tm.toArray(), new String[]{"key7", "key8", "key9"});

        assertEquals(set.pollFirstEntry().getElement(), "key0");
        assertEquals(set.pollLastEntry().getElement(), "key9");
        assertEquals(set.size(), 8);
    }
}
