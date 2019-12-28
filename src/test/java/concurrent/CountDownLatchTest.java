package concurrent;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    @Test
    public void testCountDownLatch() {
        long endTime = System.currentTimeMillis() + 3000;

        CountDownLatch latch = new CountDownLatch(2);
        new Thread(() -> {
            while (System.currentTimeMillis() < endTime) {
                System.out.println("hello world 1");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            latch.countDown();
        }).start();

        new Thread(() -> {
            while (System.currentTimeMillis() < endTime) {
                System.out.println("hello world 2");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            latch.countDown();
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
