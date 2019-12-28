package util;

import org.junit.Test;

import java.util.Arrays;
import java.util.NavigableSet;
import java.util.TreeSet;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class NavigableSetTest {
    @Test
    public void testNavigableSet() {
        for (NavigableSet<String> s : Arrays.asList(
                new TreeSet<String>()
        )) {
            for (int i = 0; i < 10; i++) {
                s.add("test" + i);
            }
            assertEquals(s.lower("test6"), "test5"); // <
            assertEquals(s.higher("test6"), "test7"); // >
            assertEquals(s.floor("test6"), "test6"); // <=
            assertEquals(s.ceiling("test6"), "test6"); // >=

            s.add("test65");
            s.add("test67");
            assertEquals(s.floor("test66"), "test65"); // <=
            assertEquals(s.ceiling("test66"), "test67"); // >=
            s.remove("test65");
            s.remove("test67");

            assertEquals(s.pollFirst(), "test0");
            assertEquals(s.pollLast(), "test9");

            NavigableSet ds = s.descendingSet();
            assertArrayEquals(ds.toArray(), new String[]{
                    "test8", "test7", "test6", "test5", "test4", "test3", "test2", "test1"
            });

            NavigableSet hs1 = s.headSet("test3", true);
            NavigableSet hs2 = s.headSet("test3", false);
            NavigableSet ts1 = s.tailSet("test7", true);
            NavigableSet ts2 = s.tailSet("test7", false);
            NavigableSet ss = s.subSet("test3", true, "test7", false);
            assertArrayEquals(hs1.toArray(), new String[]{"test1", "test2", "test3"});
            assertArrayEquals(hs2.toArray(), new String[]{"test1", "test2"});
            assertArrayEquals(ts1.toArray(), new String[]{"test7", "test8"});
            assertArrayEquals(ts2.toArray(), new String[]{"test8"});
            assertArrayEquals(ss.toArray(), new String[]{"test3", "test4", "test5", "test6"});
        }
    }
}
