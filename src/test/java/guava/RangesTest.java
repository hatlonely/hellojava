package guava;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RangesTest {
    @Test
    public void testRanges() {
        {
            Range<Double> r1 = Range.closed(1.5, 2.5);     // [a, b]
            Range<Double> r2 = Range.open(1.5, 2.5);       // (a, b)
            Range<Double> r3 = Range.closedOpen(1.0, 2.0); // [a, b)
            Range<Double> r4 = Range.openClosed(1.0, 2.0); // (a, b]
            Range<Double> r5 = Range.greaterThan(4.0);                    // (a, +∞)
            Range<Double> r6 = Range.atLeast(5.0);                        // [a, +∞)
            Range<Double> r7 = Range.lessThan(5.0);                       // (-∞, b)
            Range<Double> r8 = Range.atMost(5.0);                         // (-∞, b]
            Range<Double> r9 = Range.all();                               // (-∞, +∞)
            Range<Double> r10 = Range.upTo(10.0, BoundType.CLOSED);   // (-∞, b)
            Range<Double> r11 = Range.downTo(6.0, BoundType.OPEN);    // (a, +∞)
            Range<Double> r12 = Range.range(6.0, BoundType.CLOSED, 10.0, BoundType.OPEN);  // [a, b)
        }
        {
            assertTrue(Range.closed(1, 3).contains(2));
            assertTrue(Range.closedOpen(4, 4).isEmpty());

            // 是否有交集
            assertTrue(Range.closed(1, 10).isConnected(Range.open(5, 15)));

            // 交集
            assertEquals(
                    Range.closed(1, 10).intersection(Range.open(5, 15)),
                    Range.openClosed(5, 10)
            );

            // 最小覆盖集合
            assertEquals(
                    Range.closed(1, 5).span(Range.closed(6, 10)),
                    Range.closed(1, 10)
            );
        }
    }
}
