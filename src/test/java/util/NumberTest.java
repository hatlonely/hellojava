package util;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class NumberTest {
    @Test
    public void testInteger() {
        double delta = 0.000001;

        for (Number n : Arrays.asList(
                (short) 10, 10, (long) 10, (float) 10.0, 10.0
        )) {
            assertEquals(n.shortValue(), 10);
            assertEquals(n.intValue(), 10);
            assertEquals(n.longValue(), 10);
            assertEquals(n.byteValue(), (byte) 10);
            assertEquals(n.floatValue(), 10.0, delta);
            assertEquals(n.doubleValue(), 10.0, delta);
            System.out.println(n.toString());
        }
    }

    @Test
    public void testConvert() {
        System.out.println("Byte: " + Byte.SIZE);
        System.out.println("Char: " + Character.SIZE);
        System.out.println("Short: " + Short.SIZE);
        System.out.println("Integer: " + Integer.SIZE);
        System.out.println("Long: " + Long.SIZE);
        System.out.println("Float: " + Float.SIZE);
        System.out.println("Double: " + Double.SIZE);
    }
}
