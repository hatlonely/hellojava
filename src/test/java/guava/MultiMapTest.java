package guava;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.junit.Test;

public class MultiMapTest {
    @Test
    public void testMultiMap() {
        final Multimap<String, String> mm = HashMultimap.create();
    }
}
