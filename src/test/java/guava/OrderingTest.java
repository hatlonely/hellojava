package guava;

import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class OrderingTest {
    @Test
    public void testOrdering() {
        final List<String> list = Arrays.asList("banana", "orange", "apple", "pear", "watermelon");

        assertArrayEquals(Ordering.natural().sortedCopy(list).toArray(), new String[]{
                "apple", "banana", "orange", "pear", "watermelon"
        });
        assertArrayEquals(Ordering.usingToString().sortedCopy(list).toArray(), new String[]{
                "apple", "banana", "orange", "pear", "watermelon"
        });
        assertArrayEquals(Ordering.natural().reverse().sortedCopy(list).toArray(), new String[]{
                "watermelon", "pear", "orange", "banana", "apple"
        });
        assertArrayEquals(Ordering.natural().reverse().nullsFirst().sortedCopy(list).toArray(), new String[]{
                "watermelon", "pear", "orange", "banana", "apple"
        });
        // onResultOf 根据 Function 的结果排序
        assertArrayEquals(Ordering.natural().reverse().nullsFirst().onResultOf((x) -> ((String) x).length()).sortedCopy(list).toArray(), new String[]{
                "watermelon", "banana", "orange", "apple", "pear"
        });
        // compound 第二排序方法
        assertArrayEquals(Ordering.natural().reverse().nullsFirst()
                .compound((x, y) -> Ints.compare(((String) x).length(), ((String) y).length()))
                .sortedCopy(list).toArray(), new String[]{
                "watermelon", "pear", "orange", "banana", "apple"
        });

        assertEquals(Ordering.natural().max(list), "watermelon");
        assertEquals(Ordering.natural().min(list), "apple");
        assertEquals(Ordering.natural().greatestOf(list, 1), Arrays.asList("watermelon"));
        assertEquals(Ordering.natural().greatestOf(list, 2), Arrays.asList("watermelon", "pear"));
        assertEquals(Ordering.natural().greatestOf(list, 3), Arrays.asList("watermelon", "pear", "orange"));
        assertEquals(Ordering.natural().leastOf(list, 3), Arrays.asList("apple", "banana", "orange"));
        assertFalse(Ordering.natural().isOrdered(list));
        assertTrue(Ordering.natural().isOrdered(Ordering.natural().sortedCopy(list)));
        assertFalse(Ordering.natural().isOrdered(Ordering.natural().reverse().sortedCopy(list)));
    }
}
