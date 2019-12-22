package guava;

import com.google.common.collect.*;
import com.google.common.primitives.Ints;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class UtilityTest {
    @Test
    public void testIterable() {
        Iterable<Integer> nums = Iterables.concat(
                Ints.asList(1, 2, 3),
                Ints.asList(4, 5, 6)
        );
    }

    @Test
    public void testLists() {
        {
            List<Integer> l1 = Lists.newArrayList();
            List<Integer> l2 = Lists.newLinkedList();
            List<Integer> l3 = Lists.newArrayListWithExpectedSize(20);
            List<Integer> l4 = Lists.newCopyOnWriteArrayList();
        }
        {
            // 笛卡尔乘积
            List<List<Integer>> ll = Lists.cartesianProduct(Lists.newArrayList(1, 2), Lists.newArrayList(3, 4, 5));
            System.out.println(ll);
        }
        {
            List<Integer> l = Ints.asList(1, 2, 3, 4, 5);
            System.out.println(Lists.reverse(l));
            System.out.println(Lists.partition(l, 3));
        }
    }

    @Test
    public void testSet() {
        {
            Set<Integer> s1 = Sets.newTreeSet();
            Set<Integer> s2 = Sets.newHashSet();
            Set<Integer> s3 = Sets.newLinkedHashSet();
            Set<Integer> s4 = Sets.newConcurrentHashSet();
            Set<Integer> s5 = Sets.newCopyOnWriteArraySet();

            for (Set<Integer> s : ImmutableList.of(
                    s1, s2, s3, s4, s5
            )) {
                for (int i = 0; i < 10; i++) {
                    s.add(i);
                }
                System.out.println(Sets.filter(s, (i) -> i % 2 == 0));  // 过滤
                for (Set<Integer> set : Sets.combinations(s, 2)) {  // 从集合 s 中挑选 size 个元素
                    System.out.println(set);
                }
            }
        }

        {
            Set<Integer> s1 = Sets.newTreeSet();
            Set<Integer> s2 = Sets.newTreeSet();
            for (int i = 0; i < 10; i++) {
                s1.add(i);
                s2.add(i + 5);
            }
            System.out.println(Sets.intersection(s1, s2));  // 交集, [5, 6, 7, 8, 9]
            System.out.println(Sets.difference(s1, s2));    // 差集, [0, 1, 2, 3, 4]
            System.out.println(Sets.union(s1, s2));         // 并集, [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14]

            for (Set<Integer> set : Sets.powerSet(s1)) {    // 所有子集
                System.out.println(set);
            }
        }
    }

    @Test
    public void testMap() {
        {
            Map<String, String> m1 = Maps.newHashMap();
            Map<String, String> m2 = Maps.newTreeMap();
            Map<String, String> m3 = Maps.newLinkedHashMap();
            Map<String, String> m4 = Maps.newConcurrentMap();
            Map<String, String> m5 = Maps.newIdentityHashMap();

            Map<String, String> m6 = Maps.asMap(ImmutableSet.of(
                    "key1", "key2", "key3"
            ), (i) -> i.replace("key", "val"));
        }

        {
            Map<String, String> m = Maps.newHashMap();
            for (int i = 0; i < 10; i++) {
                m.put("key" + i, "val" + i);
            }
            System.out.println(Maps.filterKeys(m, (k) -> k.endsWith("6") || k.endsWith("7")));
            System.out.println(Maps.filterEntries(m, (e) -> e.getKey().endsWith("8")));
        }

        {
            class MyClass {
                private final String id;
                private final int val;

                private MyClass(String id, int val) {
                    this.id = id;
                    this.val = val;
                }

                private String getID() {
                    return id;
                }

                @Override
                public String toString() {
                    return id + " => " + val;
                }
            }
            ImmutableList<MyClass> l = ImmutableList.of(
                    new MyClass("one", 1),
                    new MyClass("two", 2)
            );
            Map<String, MyClass> m = Maps.uniqueIndex(l, (v) -> v.getID());
            for (Map.Entry e : m.entrySet()) {
                System.out.println(e.getKey() + ": " + e.getValue().toString());
            }
        }
    }

    @Test
    public void testQueue() {
        Queue<Integer> q1 = Queues.newArrayBlockingQueue(20);
        Queue<Integer> q2 = Queues.newArrayDeque();
        Queue<Integer> q3 = Queues.newLinkedBlockingDeque();
        Queue<Integer> q4 = Queues.newConcurrentLinkedQueue();
        Queue<Integer> q5 = Queues.newPriorityQueue();
        Queue<Integer> q6 = Queues.newPriorityBlockingQueue();
        Queue<Integer> q7 = Queues.newSynchronousQueue();
    }
}
