package javase;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QueueTest {
    @Test
    public void testQueue() {
        for (final Queue<Integer> q : Arrays.asList(
                new LinkedList<Integer>(),
                new ArrayDeque<Integer>(),
                new ConcurrentLinkedDeque<Integer>(),
                new PriorityQueue<Integer>(),
                new ArrayBlockingQueue<Integer>(10),
                new LinkedBlockingQueue<Integer>(),
                new PriorityBlockingQueue<Integer>()
        )) {
            // add / remove / element
            q.clear();
            assertThrows(NoSuchElementException.class, () -> q.remove());
            assertThrows(NoSuchElementException.class, () -> q.element());
            for (int i = 0; i < 10; i++) {
                q.add(i);
            }
            for (int i = 0; i < 10; i++) {
                assertEquals(q.element(), Integer.valueOf(i));
                assertEquals(q.remove(), Integer.valueOf(i));
            }

            // offer / poll / peek
            q.clear();
            assertEquals(q.poll(), null);
            assertEquals(q.peek(), null);
            for (int i = 0; i < 10; i++) {
                q.offer(i);
            }
            for (int i = 0; i < 10; i++) {
                assertEquals(q.peek(), Integer.valueOf(i));
                assertEquals(q.poll(), Integer.valueOf(i));
            }
        }
    }

    @Test
    public void testBlockingQueue() {
        final Queue<Integer> q = new ArrayBlockingQueue<>(10);
        // empty
        assertThrows(NoSuchElementException.class, () -> q.remove());
        assertEquals(q.poll(), null);
        assertThrows(NoSuchElementException.class, () -> q.element());
        assertEquals(q.peek(), null);

        for (int i = 0; i < 10; i++) {
            q.add(i);
        }
        // full
        assertThrows(IllegalStateException.class, () -> q.add(10));
        assertFalse(q.offer(10));
    }
}
