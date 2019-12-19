package javase;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ListTest {
    @Test
    public void testList() {
        for (final List<Integer> l : Arrays.asList(
                new LinkedList<Integer>(),
                new ArrayList<Integer>(),
                new Vector<Integer>()
        )) {
            for (int i = 0; i < 10; i++) {
                l.add(i);
            }
            assertArrayEquals(l.toArray(), new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});

            for (final ListIterator it = l.listIterator(); it.hasNext(); ) {
                System.out.println(it.next());
            }

            for (int i = 0; i < 10; i++) {
                assertEquals(l.get(i), Integer.valueOf(i));
            }
            for (int i = 0; i < 10; i++) {
                l.set(i, 10 - i - 1);
            }
            assertArrayEquals(l.toArray(), new Integer[]{9, 8, 7, 6, 5, 4, 3, 2, 1, 0});

            l.add(2, 10);
            assertArrayEquals(l.toArray(), new Integer[]{9, 8, 10, 7, 6, 5, 4, 3, 2, 1, 0});

            l.remove(2);
            assertArrayEquals(l.toArray(), new Integer[]{9, 8, 7, 6, 5, 4, 3, 2, 1, 0});

            l.remove(Integer.valueOf(2));
            assertArrayEquals(l.toArray(), new Integer[]{9, 8, 7, 6, 5, 4, 3, 1, 0});

            l.add(2);
            l.sort(Integer::compareTo);
            assertArrayEquals(l.toArray(), new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});

            l.add(8, 2);
            assertArrayEquals(l.toArray(), new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 2, 8, 9});
            assertEquals(l.indexOf(2), 2);
            assertEquals(l.lastIndexOf(2), 8);

            final List<Integer> sub = l.subList(3, 8);
            assertArrayEquals(sub.toArray(), new Integer[]{3, 4, 5, 6, 7});
        }
    }
}
