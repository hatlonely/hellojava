package concurrent;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.*;

import static org.junit.Assert.*;

public class AtomicTest {
    @Test
    public void testAtomic() {
        AtomicInteger ai = new AtomicInteger(10);
        ExecutorService es = Executors.newCachedThreadPool();

        long endTime = System.currentTimeMillis() + 100;
        for (int i = 0; i < 10; i++) {
            es.execute(() -> {
                while (System.currentTimeMillis() < endTime) {
                    System.out.println(ai.incrementAndGet());
                    System.out.println(ai.addAndGet(1));
                }
            });
        }

        try {
            es.shutdown();
            while (!es.awaitTermination(1, TimeUnit.SECONDS)) {
                // nothing to do
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAtomicInteger() {
        AtomicInteger i = new AtomicInteger(10);
        assertEquals(i.get(), 10);
        assertEquals(i.incrementAndGet(), 11);
        assertEquals(i.getAndIncrement(), 11);
        assertEquals(i.get(), 12);
        assertEquals(i.addAndGet(10), 22);
        assertEquals(i.getAndAdd(10), 22);
        assertEquals(i.get(), 32);
        assertTrue(i.compareAndSet(32, 10));
        assertFalse(i.compareAndSet(32, 10));
        assertEquals(i.compareAndExchange(10, 22), 10);
        assertEquals(i.compareAndExchange(10, 22), 22);
    }

    @Test
    public void testAtomicArray() {
        AtomicIntegerArray ia = new AtomicIntegerArray(10);

        for (int i = 0; i < 10; i++) {
            ia.set(i, i);
        }

        assertEquals(ia.get(5), 5);
        assertEquals(ia.incrementAndGet(5), 6);
        assertEquals(ia.incrementAndGet(5), 7);
        assertEquals(ia.addAndGet(5, 10), 17);
        assertTrue(ia.compareAndSet(5, 17, 15));
        assertEquals(ia.compareAndExchange(5, 15, 10), 15); // 返回老值
    }

    @Test
    public void testAtomicReference() {
        AtomicReference<Integer> i = new AtomicReference<>();
        assertEquals(i.get(), null);
        assertEquals(i.getAndSet(10), null);
        assertEquals(i.get().intValue(), 10);
    }

    @Test
    public void testStampedReference() {
        AtomicStampedReference<Integer> i = new AtomicStampedReference<>(null, 0);
        assertEquals(i.getReference(), null);
        assertEquals(i.getStamp(), 0);
        i.compareAndSet(null, 10, 0, 1);
        assertEquals(i.getReference().intValue(), 10);
        assertEquals(i.getStamp(), 1);
    }

    @Test
    public void testLongAccumulator() {
        {
            LongAccumulator i = new LongAccumulator((x, y) -> x + y, 0);
            i.accumulate(10);
            i.accumulate(20);
            assertEquals(i.get(), 30);
        }

        {
            LongAdder j = new LongAdder();
            j.add(10);
            j.add(20);
            assertEquals(j.sum(), 30);
        }
    }
}
