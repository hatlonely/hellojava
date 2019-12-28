package util;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class CollectionTest {
    @Test
    public void testCollection() {
        for (Collection<Integer> c : Arrays.asList(
                new ArrayList<Integer>(),
                new LinkedList<Integer>(),
                new Vector<Integer>(),
                new HashSet<Integer>(),
                new TreeSet<Integer>()
        )) {
            // 新增
            for (int i = 0; i < 10; i++) {
                c.add(i);
            }

            // 遍历
            for (Iterator it = c.iterator(); it.hasNext(); ) {
                System.out.println(it.next());
            }
            for (Integer i : c) {
                System.out.println(i);
            }

            // 状态检查
            assertArrayEquals(c.toArray(), new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
            assertEquals(c.size(), 10);
            assertFalse(c.isEmpty());
            for (int i = 0; i < 10; i++) {
                assertTrue(c.contains(i));
            }

            // 删除
            assertTrue(c.remove(4));
            assertFalse(c.remove(10));
            assertArrayEquals(c.toArray(), new Integer[]{0, 1, 2, 3, 5, 6, 7, 8, 9});

            // 集合新增
            c.addAll(Arrays.asList(10, 11, 12));
            assertArrayEquals(c.toArray(), new Integer[]{0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 11, 12});

            assertTrue(c.containsAll(Arrays.asList(1, 3, 5, 7, 9)));
            assertFalse(c.containsAll(Arrays.asList(13, 14)));

            // 集合删除
            assertTrue(c.removeAll(Arrays.asList(0, 10, 12)));
            assertArrayEquals(c.toArray(), new Integer[]{1, 2, 3, 5, 6, 7, 8, 9, 11});

            // 条件删除
            assertTrue(c.removeIf((i) -> i % 2 == 0));
            assertArrayEquals(c.toArray(), new Integer[]{1, 3, 5, 7, 9, 11});
            assertFalse(c.removeIf((i) -> i > 20));

            // 交集
            assertTrue(c.retainAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)));
            assertArrayEquals(c.toArray(), new Integer[]{1, 3, 5, 7, 9});

            // stream
            assertEquals(c.stream().max((a, b) -> (a - b)).get().intValue(), 9);
            c.stream().map(i -> i * i).forEach(System.out::println);

            // 清空
            c.clear();
            assertTrue(c.isEmpty());
        }
    }
}
