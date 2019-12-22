package guava;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RangeSetTest {
    @Test
    public void testRangeSet() {
        RangeSet<Integer> rs = TreeRangeSet.create();
        rs.add(Range.closed(1, 10)); // [1, 10]
        assertTrue(rs.contains(8));
        assertTrue(rs.contains(10));
        assertFalse(rs.contains(11));

        rs.add(Range.closedOpen(11, 15)); // [11, 15)
        assertTrue(rs.contains(11));
        assertFalse(rs.contains(15));

        rs.remove(Range.openClosed(4, 6));
        System.out.println(rs); // [[1..4], (6..10], [11..15)]
    }
}
