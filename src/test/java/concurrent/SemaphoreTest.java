package concurrent;

import org.junit.Test;

import java.util.concurrent.*;

public class SemaphoreTest {
    @Test
    public void testConsumerProducer() {
        ExecutorService es = Executors.newCachedThreadPool();

        Semaphore producer = new Semaphore(0);
        Semaphore consumer = new Semaphore(1);

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

    @Test
    public void testCriticalSection() {
        ExecutorService es = Executors.newCachedThreadPool();
        Semaphore semaphore = new Semaphore(1);
        long endTime = System.currentTimeMillis() + 100;
        for (int i = 0; i < 10; i++) {
            es.execute(() -> {
                try {
                    while (System.currentTimeMillis() < endTime) {
                        semaphore.acquire();
                        System.out.println("hello world");
                        semaphore.release();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
