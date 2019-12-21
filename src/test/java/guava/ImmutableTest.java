package guava;

import com.google.common.collect.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ImmutableTest {
    @Test
    public void testImmutable() {
        {
            final ImmutableSet<String> colors = ImmutableSet.of(
                    "red", "orange", "yellow", "green", "blue", "purple"
            );
            assertTrue(colors.contains("red"));
        }
        {
            // construct a immutable from mutable
            final List<String> list = Lists.newLinkedList();
            list.addAll(Arrays.asList("red", "orange", "yellow", "green", "blue", "purple"));
            final ImmutableSet<String> colors = ImmutableSet.copyOf(list);
            assertTrue(colors.contains("red"));
        }

        {
            final ImmutableList<String> colors = ImmutableList.of(
                    "red", "orange", "yellow", "green", "blue", "purple"
            );
            assertEquals(colors.get(0), "red");
            assertEquals(colors.indexOf("yellow"), 2);
        }

        {
            final ImmutableSortedSet<String> colors = ImmutableSortedSet.of(
                    "red", "orange", "yellow", "green", "blue", "purple"
            );
            assertEquals(colors.first(), "blue");
            assertEquals(colors.last(), "yellow");
            assertEquals(colors.lower("x"), "red");
            assertEquals(colors.higher("x"), "yellow");
            assertEquals(colors.ceiling("x"), "yellow");
            assertEquals(colors.floor("x"), "red");
        }

        {
            final ImmutableMap<String, String> kv = ImmutableMap.of(
                    "key1", "val1",
                    "key2", "val2",
                    "key3", "val3"
            );
            assertTrue(kv.containsKey("key1"));
            assertTrue(kv.containsValue("val2"));
            assertEquals(kv.get("key1"), "val1");
            assertFalse(kv.isEmpty());
        }

        {
            final ImmutableSortedMap<String, String> kv = ImmutableSortedMap.of(
                    "key1", "val1",
                    "key2", "val2",
                    "key3", "val3"
            );
            assertTrue(kv.containsKey("key1"));
            assertTrue(kv.containsValue("val2"));
            assertEquals(kv.get("key1"), "val1");
            assertFalse(kv.isEmpty());
            assertEquals(kv.firstKey(), "key1");
            assertEquals(kv.lastKey(), "key3");
            assertEquals(kv.firstEntry().getKey(), "key1");
            assertEquals(kv.firstEntry().getValue(), "val1");
            assertEquals(kv.lastEntry().getKey(), "key3");
            assertEquals(kv.lowerKey("key2"), "key1");
            assertEquals(kv.higherKey("key2"), "key3");
            assertEquals(kv.ceilingKey("key2"), "key2");
            assertEquals(kv.floorKey("key2"), "key2");
        }
    }
}
