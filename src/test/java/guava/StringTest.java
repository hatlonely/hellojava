package guava;

import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringTest {
    @Test
    public void testJoiner() {
        assertEquals(Joiner.on("; ").skipNulls().join(
                "Harry", null, "Ron", "Hermione"
        ), "Harry; Ron; Hermione");

        assertEquals(Joiner.on("; ").useForNull("null").join(
                "Harry", null, "Ron", "Hermione"
        ), "Harry; null; Ron; Hermione");
    }

    @Test
    public void testSplitter() {
        Splitter.on(",")
                .trimResults()          // 去掉前后空白
                .omitEmptyStrings()     // 忽略空字符串
                .split("one, two, ,three")
                .forEach((x) -> System.out.println(x));
    }

    @Test
    public void testCaseFormat() {
        assertEquals(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "CONSTANT_NAME"), "constantName");
        assertEquals(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, "CONSTANT_NAME"), "constant-name");
        assertEquals(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, "CONSTANT_NAME"), "ConstantName");
    }
}
