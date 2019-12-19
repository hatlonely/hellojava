package javase;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedDeque;

public class DequeTest {
    @Test
    public void testDQueue() {
        for (Deque<Integer> q : Arrays.asList(
                new LinkedList<Integer>(),
                new ArrayDeque<Integer>(),
                new ConcurrentLinkedDeque<Integer>()
        )) {

        }
    }
}
