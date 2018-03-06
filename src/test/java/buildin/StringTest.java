package buildin;

import org.junit.Test;
import static org.junit.Assert.*;

public class StringTest {
    @Test
    public void testStringAssert() {
        assertTrue("stay hungry, stay foolish".startsWith("stay"));
        assertTrue("stay hungry, stay foolish".endsWith("foolish"));
        assertTrue("stay hungry, stay foolish".contains("hungry"));
        assertTrue("hello world".equalsIgnoreCase("Hello world"));
    }

    @Test
    public void testStringOperation() {
        assertEquals("hello" + " " + "java", "hello java");
        assertEquals("HELLO".toLowerCase(), "hello");
        assertEquals("world".toUpperCase(), "WORLD");
        assertEquals(" hello world ".trim(), "hello world");
        assertEquals("0123456789".substring(4), "456789");
        assertEquals("0123456789".substring(3, 6), "345");
        assertEquals("stay hungry, stay foolish".replace("stay", "keep"), "keep hungry, keep foolish");
        assertArrayEquals("java golang swift".split(" "), new String[]{"java", "golang", "swift"});
        assertEquals(String.join("|", new String[]{"java", "golang", "swift"}), "java|golang|swift");
    }

    @Test
    public void testStringFind() {
        assertEquals("01234567890123456789".indexOf('6'), 6);
        assertEquals("01234567890123456789".lastIndexOf('6'), 16);
        assertEquals("01234567890123456789".indexOf("678"), 6);
        assertEquals("01234567890123456789".lastIndexOf("678"), 16);
        assertEquals("01234567890123456789".indexOf("abcd"), -1);
        assertEquals("01234567890123456789".charAt(6), '6');
    }

    @Test
    public void testStringConvert() {
        assertEquals(Integer.parseInt("123456"), 123456);
        assertEquals(Double.parseDouble("123.456"), 123.456, 0.00001);
        assertEquals(Integer.toString(123456), "123456");
        assertEquals(Integer.toHexString(123456), "1e240");
        assertEquals(Double.toString(123.456), "123.456");
    }
}
