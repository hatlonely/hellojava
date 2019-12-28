package util;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegexTest {
    @Test
    public void testRegex() {
        Pattern pattern = Pattern.compile("^([a-z0-9._%+-]+)@([a-z0-9.-]+)\\.[a-z]{2,4}$");
        Matcher matcher = pattern.matcher("hatlonely@foxmail.com");
        assertTrue(matcher.matches());
        assertEquals(matcher.group(), "hatlonely@foxmail.com");
        assertEquals(matcher.group(1), "hatlonely");
        assertEquals(matcher.group(2), "foxmail");
        assertEquals(matcher.groupCount(), 2);
    }
}
