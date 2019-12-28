package util;

import org.junit.Test;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class SortedSetTest {
    @Test
    public void testSortedSet() {
        for (SortedSet<String> s : Arrays.asList(
                new TreeSet<String>()
        )) {
            for (int i = 0; i < 10; i++) {
                s.add("test" + i);
            }
            System.out.println(s);
            assertEquals(s.first(), "test0");
            assertEquals(s.last(), "test9");

            SortedSet hs = s.headSet("test3");
            SortedSet ts = s.tailSet("test7");
            SortedSet ss = s.subSet("test3", "test7");
            System.out.println(hs);
            System.out.println(ts);
            System.out.println(ss);
            assertArrayEquals(hs.toArray(), new String[]{"test0", "test1", "test2"});
            assertArrayEquals(ts.toArray(), new String[]{"test7", "test8", "test9"});
            assertArrayEquals(ss.toArray(), new String[]{"test3", "test4", "test5", "test6"});
        }
    }
}
