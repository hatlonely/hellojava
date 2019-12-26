package javase;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class ReflectTest {
    @Test
    public void testReflect() throws Exception {
        Class c = Class.forName("java.lang.String");

        assertNotNull(c);
        assertEquals(c.getName(), "java.lang.String");
        assertEquals(c.getPackageName(), "java.lang");

        Method m = c.getMethod("startsWith", String.class);
        assertEquals(m.getName(), "startsWith");
        // 参数
        assertEquals(m.getParameters().length, 1);
        assertEquals(m.getParameters()[0].getName(), "arg0");
        assertEquals(m.getParameters()[0].getType().getName(), "java.lang.String");
        assertEquals(m.getReturnType().getName(), "boolean");
        // 返回值
        assertTrue(m.getReturnType().isPrimitive());
        // 调用
        assertTrue((boolean) m.invoke("hello world", "hello"));
    }
}
