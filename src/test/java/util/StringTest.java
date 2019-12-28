package util;

import org.junit.Test;

import java.util.StringJoiner;
import java.util.StringTokenizer;

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

    @Test
    public void testStringJoiner() {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        sj.add("one");
        sj.add("two");
        sj.add("three");
        assertEquals(sj.toString(), "[one, two, three]");
    }

    @Test
    public void testStringBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.append("hello");
        sb.append(" ");
        sb.append("world");
        sb.append(" ");
        sb.append(123);
        assertEquals(sb.toString(), "hello world 123");
    }

    @Test
    public void testStringBuffer() {
        // 和 StringBuilder 接口一样，但是是线程安全的
        StringBuffer sb = new StringBuffer();
        sb.append("hello");
        sb.append(" ");
        sb.append("world");
        sb.append(" ");
        sb.append(123);
        assertEquals(sb.toString(), "hello world 123");
    }

    @Test
    public void testStringTokenizer() {
        // StringTokenizer是由于兼容性原因而保留的遗留类，不建议使用，使用 String.split 代替
        StringTokenizer tokens = new StringTokenizer("one, two, three", ", ");
        while (tokens.hasMoreTokens()) {
            System.out.println(tokens.nextToken());
        }
    }
}
