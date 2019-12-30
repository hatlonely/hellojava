package util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class CollectionTest {
    @Test
    public void testCollection() {
        {
            Collection<Integer> c = new ArrayList<>(List.of(1, 2, 3, 4, 5));
            assertEquals(c.size(), 5);
            assertFalse(c.isEmpty());
            assertTrue(c.contains(3));
            assertTrue(c.containsAll(List.of(2, 4)));
            c.clear();
            assertEquals(c.size(), 0);
            assertTrue(c.isEmpty());
        }
        {
            Collection<Integer> c = new ArrayList<>(List.of(1, 2, 3, 4, 5));
            c.add(6);
            assertThat(c, equalTo(List.of(1, 2, 3, 4, 5, 6)));
            c.addAll(List.of(7, 8, 9));
            assertThat(c, equalTo(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9)));
        }
        {
            Collection<Integer> c = new ArrayList<>(List.of(1, 2, 3, 4, 5));
            c.remove(3);
            assertThat(c, equalTo(List.of(1, 2, 4, 5)));
            c.removeAll(List.of(2, 3));
            assertThat(c, equalTo(List.of(1, 4, 5)));
            c.retainAll(List.of(1, 2, 3, 4));
            assertThat(c, equalTo(List.of(1, 4)));
            c.removeIf(x -> x % 2 == 0);
            assertThat(c, equalTo(List.of(1)));
        }
        {
            Collection<Integer> c = new ArrayList<>(List.of(1, 2, 3, 4, 5));
            c.forEach(System.out::print);
            assertEquals(c.stream().map(x -> x * x).mapToInt(x -> x).sum(), 55);

            for (Integer i : c) {
                System.out.print(i);
            }
        }
    }
}
