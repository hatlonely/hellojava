package concurrent;

import org.junit.Test;

import java.util.concurrent.*;

public class CyclicBarrierTest {
    @Test
    public void testCyclicBarrier() {
        ExecutorService es = Executors.newCachedThreadPool();
        CyclicBarrier cb = new CyclicBarrier(10);

        for (int i = 0; i < 10; i++) {
            es.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getId() + " wait others");
                    cb.await();
                    System.out.println(Thread.currentThread().getId() + " done");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
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
    public void testCyclicBarrierBroken() {
        ExecutorService es = Executors.newCachedThreadPool();
        CyclicBarrier cb = new CyclicBarrier(10);

        for (int i = 0; i < 8; i++) {
            es.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getId() + " wait others");
                    cb.await();
                    System.out.println(Thread.currentThread().getId() + " done");
                } catch (InterruptedException | BrokenBarrierException e) {
                    // 其中一个线程异常退出，await 抛出 BrokenBarrierException 异常
                    e.printStackTrace();
                }
            });
        }

        es.execute(() -> {
            try {
                cb.await(1, TimeUnit.SECONDS);
            } catch (InterruptedException | TimeoutException | BrokenBarrierException e) {
                // 等待最后一个线程超时，await 抛出 TimeoutException 异常
                e.printStackTrace();
            }
        });

        try {
            es.shutdown();
            while (!es.awaitTermination(1, TimeUnit.SECONDS)) {
                // nothing to do
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
