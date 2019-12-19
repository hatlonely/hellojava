package javase;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class CollectionTest {
    @Test
    public void testCollection() {
        for (Collection<Integer> c : Arrays.asList(
                new ArrayList<Integer>(),
                new LinkedList<Integer>(),
                new Vector<Integer>(),
                new HashSet<Integer>(),
                new TreeSet<Integer>()
        )) {
            for (int i = 0; i < 10; i++) {
                c.add(i);
            }
            assertArrayEquals(c.toArray(), new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
            assertEquals(c.size(), 10);
            assertFalse(c.isEmpty());
            for (int i = 0; i < 10; i++) {
                assertTrue(c.contains(i));
            }

            assertTrue(c.remove(4));
            assertFalse(c.remove(10));
            assertArrayEquals(c.toArray(), new Integer[]{0, 1, 2, 3, 5, 6, 7, 8, 9});

            c.addAll(Arrays.asList(10, 11, 12));
            assertArrayEquals(c.toArray(), new Integer[]{0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 11, 12});

            assertTrue(c.containsAll(Arrays.asList(1, 3, 5, 7, 9)));
            assertFalse(c.containsAll(Arrays.asList(13, 14)));

            assertTrue(c.removeAll(Arrays.asList(0, 10, 12)));
            assertArrayEquals(c.toArray(), new Integer[]{1, 2, 3, 5, 6, 7, 8, 9, 11});

            assertTrue(c.removeIf((i) -> i % 2 == 0));
            assertArrayEquals(c.toArray(), new Integer[]{1, 3, 5, 7, 9, 11});
            assertFalse(c.removeIf((i) -> i > 20));

            assertTrue(c.retainAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)));
            assertArrayEquals(c.toArray(), new Integer[]{1, 3, 5, 7, 9});

            c.clear();
            assertTrue(c.isEmpty());
        }
    }
}
