package concurrent;

import org.junit.Test;

import java.util.concurrent.*;

public class SemaphoreTest {
    @Test
    public void testSemaphore() {
        ExecutorService es = Executors.newCachedThreadPool();

        Semaphore producer = new Semaphore(1);
        Semaphore consumer = new Semaphore(0);

        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        es.execute(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    producer.acquire();
                    queue.put(Thread.currentThread().getId() + " " + i);
                    consumer.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        es.execute(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    consumer.acquire();
                    queue.put(Thread.currentThread().getId() + " " + i);
                    producer.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        try {
            es.shutdown();
            while (!es.awaitTermination(1, TimeUnit.SECONDS)) {
                // nothing to do
            }

            while (!queue.isEmpty()) {
                System.out.println(queue.take());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
