package javase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

public class SetTest {
    @Test
    public void testSet() {
        Set<Integer> hsi = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 5, 6, 6, 4, 3));
        System.out.println(hsi);

        assertFalse(hsi.isEmpty());
        assertTrue(hsi.contains(5));
        assertEquals(hsi.size(), 6);

        hsi.removeIf(i -> (i % 2 == 1));
        hsi.stream().forEach(System.out::println);

        hsi.add(8);
        hsi.add(0);

        hsi.stream().map(i -> (i + 100)).forEach(System.out::println);

        for (int i : hsi) {
            System.out.println(i);
        }

        Set<Integer> tsi = new TreeSet<>(Arrays.asList(1, 2, 3, 4, 5));
        tsi.forEach((i) -> System.out.println(i));
        tsi.stream().filter((i) -> (i % 2 == 1)).map((i) -> (i + 10)).forEach(System.out::println);
    }
}
