package util;

import org.junit.Test;

import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class SetTest {
    @Test
    public void testSet() {
        // Set 没有特有的方法，所有的方法继承自 Collection
    }

    @Test
    public void testSortedSet() {
        SortedSet<String> set = IntStream.range(0, 10).boxed().map(x -> "key" + x).collect(Collectors.toCollection(TreeSet::new));
        assertEquals(set.first(), "key0");
        assertEquals(set.last(), "key9");
        assertThat(set.headSet("key3"), equalTo(Set.of("key0", "key1", "key2")));
        assertThat(set.tailSet("key7"), equalTo(Set.of("key7", "key8", "key9")));
        assertThat(set.subSet("key3", "key7"), equalTo(Set.of("key3", "key4", "key5", "key6")));
    }

    @Test
    public void testNavigableSet() {
        {
            NavigableSet<String> set = IntStream.range(0, 10).boxed().map(x -> "key" + x).collect(Collectors.toCollection(TreeSet::new));
            assertEquals(set.lower("key6"), "key5");    // <
            assertEquals(set.higher("key6"), "key7");   // >
            assertEquals(set.floor("key6"), "key6");    // <=
            assertEquals(set.ceiling("key6"), "key6");  // >=
            set.remove("key6");
            assertEquals(set.floor("key6"), "key5");
            assertEquals(set.ceiling("key6"), "key7");
        }
        {
            NavigableSet<String> set = IntStream.range(0, 5).boxed().map(x -> "key" + x).collect(Collectors.toCollection(TreeSet::new));
            assertEquals(set.pollFirst(), "key0");
            assertThat(set, equalTo(Set.of("key1", "key2", "key3", "key4")));
            assertEquals(set.pollLast(), "key4");
            assertThat(set, equalTo(Set.of("key1", "key2", "key3")));
        }
        {
            NavigableSet<String> set = IntStream.range(0, 10).boxed().map(x -> "key" + x).collect(Collectors.toCollection(TreeSet::new));
            assertThat(set.descendingSet(), equalTo(Set.of("key9", "key8", "key7", "key6", "key5", "key4", "key3", "key2", "key1", "key0")));
            assertThat(set.headSet("key3", false), equalTo(Set.of("key0", "key1", "key2")));
            assertThat(set.tailSet("key7", true), equalTo(Set.of("key7", "key8", "key9")));
            assertThat(set.subSet("key3", true, "key7", false), equalTo(Set.of("key3", "key4", "key5", "key6")));
        }
    }
}
