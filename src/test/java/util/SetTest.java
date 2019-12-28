package util;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SetTest {
    @Test
    public void testSet() {
        // Set 没有特有的方法，所有的方法继承自 Collection
        for (Set<String> s : Arrays.asList(
                new HashSet<String>(),
                new TreeSet<String>(),
                new LinkedHashSet<String>()
        )) {
            for (int i = 0; i < 10; i++) {
                assertFalse(s.contains("test" + i));
            }
            for (int i = 0; i < 10; i++) {
                s.add("test" + i);
            }
            for (int i = 0; i < 10; i++) {
                assertTrue(s.contains("test" + i));
            }
            System.out.println(s);
            for (int i = 0; i < 10; i++) {
                s.remove("test" + i);
            }
            for (int i = 0; i < 10; i++) {
                assertFalse(s.contains("test" + i));
            }
        }
    }
}
