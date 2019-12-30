package util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ListTest {
    @Test
    public void testList() {
        {
            List<Integer> l = new ArrayList<>(List.of(1, 2, 3, 4, 5, 4, 3, 2, 1));
            assertEquals(l.get(2), Integer.valueOf(3));
            assertEquals(l.indexOf(3), 2);
            assertEquals(l.indexOf(6), -1);
            assertEquals(l.lastIndexOf(3), 6);
            assertEquals(l.subList(2, 6), List.of(3, 4, 5, 4));
        }
        {
            List<Integer> l = new ArrayList<>(List.of(1, 2, 3, 4, 5, 4, 3, 2, 1));
            l.set(5, 6);
            assertThat(l, equalTo(List.of(1, 2, 3, 4, 5, 6, 3, 2, 1)));
        }
        {
            List<Integer> l = new ArrayList<>(List.of(1, 2, 3, 4, 5, 4, 3, 2, 1));
            l.sort(Integer::compareTo);
            assertThat(l, equalTo(List.of(1, 1, 2, 2, 3, 3, 4, 4, 5)));
        }
        {
            List<Integer> l = new ArrayList<>(List.of(1, 2, 3, 4, 5, 4, 3, 2, 1));
            l.replaceAll(x -> x * x);
            assertThat(l, equalTo(List.of(1, 4, 9, 16, 25, 16, 9, 4, 1)));
        }
    }
}
