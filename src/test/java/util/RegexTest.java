package util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class RegexTest {
    @Test
    public void testMatch() {
        assertTrue(Pattern.compile("hello").matcher("hello").matches());         // 完全匹配
        assertTrue(Pattern.compile("hello").matcher("hello world").find());      // 部分匹配
        assertTrue(Pattern.compile("hello").asPredicate().test("hello world"));     // 部分匹配
        assertTrue(Pattern.matches("hello", "hello"));                    // 完全匹配
    }

    @Test
    public void testWildcard() {
        assertTrue(Pattern.matches("[0-9]*", ""));
        assertTrue(Pattern.matches("[0-9]?", ""));
        assertTrue(Pattern.matches("[0-9]?", "1"));
        assertTrue(Pattern.matches("[0-9]+", "123"));
        assertTrue(Pattern.matches("[0-9]{3}", "123"));
        assertTrue(Pattern.matches("[0-9]{3,4}", "123"));
        assertTrue(Pattern.matches("[0-9]{3,4}", "1234"));
        assertTrue(Pattern.matches("[0-9]{3,}", "1234"));
        assertTrue(Pattern.matches("\\d+", "123"));
        assertTrue(Pattern.matches("\\s+", " \t"));
        assertTrue(Pattern.matches("\\w+", "abc"));
        assertTrue(Pattern.matches("(f|z)oo", "foo"));
        assertTrue(Pattern.matches("(f|z)oo", "zoo"));
        assertTrue(Pattern.matches(".*", "any string"));
    }

    @Test
    public void testCapture() {
        {
            Pattern pattern = Pattern.compile("^[a-z0-9]+@[a-z0-9.]+[.][a-z]{2,4}$");
            Matcher matcher = pattern.matcher("hatlonely@foxmail.com");
            assertTrue(matcher.matches());
            assertEquals(matcher.groupCount(), 0);
        }
        {
            Pattern pattern = Pattern.compile("^([a-z0-9]+)@(([a-z0-9.]+)[.]([a-z]{2,4}))$");
            Matcher matcher = pattern.matcher("hatlonely@foxmail.com");
            assertTrue(matcher.matches());
            assertEquals(matcher.groupCount(), 4);
            assertEquals(matcher.group(), "hatlonely@foxmail.com");
            assertEquals(matcher.group(1), "hatlonely");
            assertEquals(matcher.group(2), "foxmail.com");
            assertEquals(matcher.group(3), "foxmail");
            assertEquals(matcher.group(4), "com");
        }
        {
            Pattern pattern = Pattern.compile("^([a-z0-9]+)@(?:([a-z0-9.]+)[.]([a-z]{2,4}))$");
            Matcher matcher = pattern.matcher("hatlonely@foxmail.com");
            assertTrue(matcher.matches());
            assertEquals(matcher.groupCount(), 3);
            assertEquals(matcher.group(), "hatlonely@foxmail.com");
            assertEquals(matcher.group(0), "hatlonely@foxmail.com");
            assertEquals(matcher.group(1), "hatlonely");
            assertEquals(matcher.group(2), "foxmail");
            assertEquals(matcher.group(3), "com");
        }
        {
            Pattern pattern = Pattern.compile("Windows (?=95|98|NT|2000)");
            Matcher matcher = pattern.matcher("Windows 2000");
            assertTrue(matcher.find());
            assertEquals(matcher.group(), "Windows ");
            assertEquals(matcher.groupCount(), 0);
        }
        {
            Pattern pattern = Pattern.compile("Windows (?!95|98|NT|2000)");
            Matcher matcher = pattern.matcher("Windows vista");
            assertTrue(matcher.find());
            assertEquals(matcher.group(), "Windows ");
            assertEquals(matcher.groupCount(), 0);
        }
        {
            Pattern pattern = Pattern.compile("(?<=95|98|NT|2000) Windows");
            Matcher matcher = pattern.matcher("2000 Windows");
            assertTrue(matcher.find());
            assertEquals(matcher.group(), " Windows");
            assertEquals(matcher.groupCount(), 0);
        }
        {
            Pattern pattern = Pattern.compile("(?<!95|98|NT|2000) Windows");
            Matcher matcher = pattern.matcher("vista Windows");
            assertTrue(matcher.find());
            assertEquals(matcher.group(), " Windows");
            assertEquals(matcher.groupCount(), 0);
        }
    }

    @Test
    public void testBackReferences() {
        assertTrue(Pattern.matches("(\\w+) \\1", "ab ab"));
        assertTrue(Pattern.matches("(\\w+) \\1", "abc abc"));
        assertFalse(Pattern.matches("(\\w+) \\1", "abc def"));
    }

    @Test
    public void testReplace() {
        Pattern pattern = Pattern.compile("^([a-z0-9]+)@(?:([a-z0-9.]+)[.]([a-z]{2,4}))$");
        assertEquals(pattern.matcher("hatlonely@foxmail.com").replaceAll(
                "$0 $1 $2 $3"
        ), "hatlonely@foxmail.com hatlonely foxmail com");

        assertEquals("hatlonely@foxmail.com".replaceAll(
                "^([a-z0-9]+)@(?:([a-z0-9.]+)[.]([a-z]{2,4}))$", "$0 $1 $2 $3"
        ), "hatlonely@foxmail.com hatlonely foxmail com");
    }

    @Test
    public void testFildAll() {
        String str = "abab x acac y aeae";
        Pattern pattern = Pattern.compile("(\\w+)\\1");
        Matcher matcher = pattern.matcher(str);

        List<String> li = new ArrayList<>();
        while (matcher.find()) {
            li.add(matcher.group());
            assertThat(str.substring(matcher.start(), matcher.end()), equalTo(matcher.group()));
        }
        assertThat(li, equalTo(List.of("abab", "acac", "aeae")));
    }
}
