package buildin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

public class ListTest {
    @Test
    public void testList() {
        List<Integer> li1 = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9); // ArrayList

        assertFalse(li1.isEmpty());
        assertTrue(li1.contains(3));
        assertEquals(li1.size(), 10);
        assertEquals(li1.get(6).intValue(), 6);
        assertEquals(li1.indexOf(8), 8);

        li1.sort((a, b) -> (b - a));

        for (Integer i : li1) {
            System.out.println(i);
        }

        li1.stream().map(i -> i * i).forEach(System.out::println);

        List<Integer> li2 = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            li2.add(i);
        }
        li2.removeIf(i -> (i % 2 == 0)); // ArrayList can not remove
        System.out.println(li2);

        assertEquals(li2.stream().max((a, b) -> (a - b)).get().intValue(), 9);
        assertEquals(li2.stream().min((a, b) -> (b - a)).get().intValue(), 9);

        System.out.println(li2.subList(1, 3));
    }
}