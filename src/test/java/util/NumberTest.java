package util;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class NumberTest {
    @Test
    public void testNumber() {
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
    public void testSize() {
        System.out.println("Byte: " + Byte.SIZE);
        System.out.println("Char: " + Character.SIZE);
        System.out.println("Short: " + Short.SIZE);
        System.out.println("Integer: " + Integer.SIZE);
        System.out.println("Long: " + Long.SIZE);
        System.out.println("Float: " + Float.SIZE);
        System.out.println("Double: " + Double.SIZE);
    }

    @Test
    public void testConvert() {
        assertEquals(Integer.parseInt("10"), 10);
        assertEquals(Integer.valueOf(10).toString(), "10");
        assertEquals(Double.parseDouble("123.456"), 123.456, 0.00001);
        assertEquals(Double.valueOf(123.456).toString(), "123.456");
    }

    @Test
    public void testLimit() {
        System.out.println("Byte: [" + Byte.MIN_VALUE + ", " + Byte.MAX_VALUE + "]");
        System.out.println("Short: [" + Short.MIN_VALUE + ", " + Short.MAX_VALUE + "]");
        System.out.println("Integer: [" + Integer.MIN_VALUE + ", " + Integer.MAX_VALUE + "]");
        System.out.println("Long: [" + Long.MIN_VALUE + ", " + Long.MAX_VALUE + "]");
        System.out.println("Float: [" + Float.MIN_VALUE + ", " + Float.MAX_VALUE + "]");
        System.out.println("Double: [" + Double.MIN_VALUE + ", " + Double.MAX_VALUE + "]");
    }
}
