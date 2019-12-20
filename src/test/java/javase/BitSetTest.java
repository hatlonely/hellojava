package javase;

import org.junit.Test;

import java.util.BitSet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BitSetTest {
    @Test
    public void testBitSet() {
        final BitSet bs = new BitSet();

        bs.set(1);
        bs.set(12);
        bs.set(123);
        assertFalse(bs.isEmpty());
        assertTrue(bs.get(1));
        assertTrue(bs.get(12));
        assertTrue(bs.get(123));
        assertFalse(bs.get(2));
        assertFalse(bs.get(23));
        assertFalse(bs.get(234));

        bs.clear(1);
        assertFalse(bs.get(1));

        bs.flip(1);
        assertTrue(bs.get(1));

        bs.set(10, 20);
        assertTrue(bs.get(13));

        bs.clear(10, 20);
        assertFalse(bs.get(13));

        bs.flip(10, 20);
        assertTrue(bs.get(13));

        System.out.println(bs);

        final BitSet nb = bs.get(10, 20);   // bit set 0-9
        System.out.println(nb);
    }

    @Test
    public void testBitOp() {
        final BitSet bs1 = new BitSet();
        final BitSet bs2 = new BitSet();
        bs1.set(1);
        bs1.set(2);
        bs1.set(4);
        bs2.set(2);
        bs2.set(3);

        bs1.and(bs2);       // 求交集
        bs1.or(bs2);        // 求并集
        bs1.xor(bs2);
        bs1.andNot(bs2);
    }
}
