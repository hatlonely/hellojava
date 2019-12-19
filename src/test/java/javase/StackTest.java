package javase;

import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.assertEquals;

public class StackTest {
    @Test
    public void testStack() {
        final Stack<Integer> s = new Stack<>();

        for (int i = 0; i < 10; i++) {
            s.push(i);
        }
        for (int i = 0; i < 10; i++) {
            assertEquals(s.peek(), Integer.valueOf(9 - i));
            assertEquals(s.pop(), Integer.valueOf(9 - i));
        }
    }
}
