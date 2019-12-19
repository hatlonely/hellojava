package javase;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class DequeTest {
    @Test
    public void testDeque() {
        for (final Deque<Integer> q : Arrays.asList(
                new LinkedList<Integer>(),
                new ArrayDeque<Integer>(),
                new ConcurrentLinkedDeque<Integer>()
        )) {
            // add / remove / get
            q.clear();
            assertThrows(NoSuchElementException.class, () -> q.removeLast());
            assertThrows(NoSuchElementException.class, () -> q.removeFirst());
            for (int i = 0; i < 5; i++) {
                q.addFirst(i);
            }
            for (int i = 5; i < 10; i++) {
                q.addLast(i);
            }
            assertArrayEquals(q.toArray(), new Integer[]{4, 3, 2, 1, 0, 5, 6, 7, 8, 9});
            for (int i = 9; i >= 5; i--) {
                assertEquals(q.getLast(), Integer.valueOf(i));
                assertEquals(q.removeLast(), Integer.valueOf(i));
            }
            for (int i = 4; i >= 0; i--) {
                assertEquals(q.getFirst(), Integer.valueOf(i));
                assertEquals(q.removeFirst(), Integer.valueOf(i));
            }

            // offer / poll / peek
            q.clear();
            assertEquals(q.pollFirst(), null);
            assertEquals(q.pollLast(), null);
            for (int i = 0; i < 5; i++) {
                q.offerFirst(i);
            }
            for (int i = 5; i < 10; i++) {
                q.offerLast(i);
            }
            assertArrayEquals(q.toArray(), new Integer[]{4, 3, 2, 1, 0, 5, 6, 7, 8, 9});
            for (int i = 9; i >= 5; i--) {
                assertEquals(q.peekLast(), Integer.valueOf(i));
                assertEquals(q.pollLast(), Integer.valueOf(i));
            }
            for (int i = 4; i >= 0; i--) {
                assertEquals(q.peekFirst(), Integer.valueOf(i));
                assertEquals(q.pollFirst(), Integer.valueOf(i));
            }

            // push / pop
            q.clear();
            for (int i = 0; i < 10; i++) {
                q.push(i);
            }
            for (int i = 0; i < 10; i++) {
                assertEquals(q.pop(), Integer.valueOf(9 - i));
            }

            // removeFirstOccurrence / removeLastOccurrence
            q.clear();
            for (int i = 0; i < 10; i++) {
                q.add(i);
            }
            assertTrue(q.removeFirstOccurrence(2));
            assertFalse(q.removeLastOccurrence(2));
        }
    }
}
