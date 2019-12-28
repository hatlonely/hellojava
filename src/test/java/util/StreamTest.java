package util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StreamTest {
    @Test
    public void testStream() {
        ArrayList<Integer> li = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            li.add(i);
        }

        System.out.println(li.stream().filter((x) -> x % 2 == 0).collect(Collectors.toList()));
        System.out.println(li.stream().dropWhile(x -> x % 2 == 0).collect(Collectors.toList()));
        System.out.println(li.stream().map((x) -> x * x).collect(Collectors.toList()));
        System.out.println(li.stream().distinct().sorted((x, y) -> y - x).collect(Collectors.toList()));
        System.out.println(li.stream().skip(3).limit(4).collect(Collectors.toList()));
        assertEquals(li.stream().reduce((x, y) -> x + y).orElse(0), Integer.valueOf(45));
        assertEquals(li.stream().min(Comparator.comparingInt(x -> x)).orElse(0), Integer.valueOf(0));
        assertEquals(li.stream().max(Comparator.comparingInt(x -> x)).orElse(0), Integer.valueOf(9));
        assertTrue(li.stream().allMatch(x -> x < 10));
        assertTrue(li.stream().anyMatch(x -> x == 6));
        assertTrue(li.stream().noneMatch(x -> x == 11));
        assertEquals(li.stream().count(), 10);
    }

    @Test
    public void testRandomStream() {
        Random random = new Random();
        List<Integer> li = random.ints().limit(10).boxed().map((x) -> Math.abs(x % 100)).collect(Collectors.toList());
        System.out.println(li);
    }
}
