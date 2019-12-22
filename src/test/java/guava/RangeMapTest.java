package guava;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RangeMapTest {
    @Test
    public void testRangeMap() {
        RangeMap<Integer, String> rm = TreeRangeMap.create();

        rm.put(Range.closed(1, 10), "hello");
        rm.put(Range.closedOpen(15, 20), "world");

        assertEquals(rm.get(12), null);
        assertEquals(rm.get(10), "hello");
        assertEquals(rm.get(15), "world");
        assertEquals(rm.get(20), null);
    }
}
