package javase;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

import static org.junit.Assert.assertEquals;

public class QueueTest {
    @Test
    public void testQueue() {
        for (Queue<Integer> q : Arrays.asList(
                new LinkedList<Integer>(),
                new ArrayDeque<Integer>(),
                new ConcurrentLinkedDeque<Integer>(),
                new PriorityQueue<Integer>()
        )) {
            Assertions.assertThrows(NoSuchElementException.class, () -> q.remove());
            for (int i = 0; i < 10; i++) {
                q.add(i);
            }
            for (int i = 0; i < 10; i++) {
                assertEquals(q.element(), Integer.valueOf(i));
                assertEquals(q.remove(), Integer.valueOf(i));
            }
            q.clear();

            assertEquals(q.poll(), null);
            for (int i = 0; i < 10; i++) {
                q.offer(i);
            }
            for (int i = 0; i < 10; i++) {
                assertEquals(q.peek(), Integer.valueOf(i));
                assertEquals(q.poll(), Integer.valueOf(i));
            }
            q.clear();
        }
    }

}
