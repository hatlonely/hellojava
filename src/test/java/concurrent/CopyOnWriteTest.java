package concurrent;

import org.junit.Test;

import java.util.concurrent.*;

// CopyOnWrite 写时复制
// 写操作时，复制容器，完成后，再将指针指向新的容器
// 适合较少写入，较多读取的场景
// 在复制写入的这个过程中，可能无法读取到最新的数据，保证的是最终一致性
public class CopyOnWriteTest {
    @Test
    public void testCopyOnWriteList() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        ExecutorService es = Executors.newCachedThreadPool();

        for (int i = 0; i < 100; i++) {
            list.add(i);
        }

        long endTime = System.currentTimeMillis() + 100;
        for (int i = 0; i < 10; i++) {
            es.execute(() -> {
                while (System.currentTimeMillis() < endTime) {
                    list.add(Math.abs(ThreadLocalRandom.current().nextInt() % 100));
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        for (int i = 0; i < 10; i++) {
            es.execute(() -> {
                while (System.currentTimeMillis() < endTime) {
                    System.out.println(list.size());
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
    public void testCopyOnWriteArray() {
        CopyOnWriteArraySet<Integer> set = new CopyOnWriteArraySet<>();
        ExecutorService es = Executors.newCachedThreadPool();

        for (int i = 0; i < 100; i++) {
            set.add(i);
        }

        long endTime = System.currentTimeMillis() + 100;
        for (int i = 0; i < 10; i++) {
            es.execute(() -> {
                while (System.currentTimeMillis() < endTime) {
                    set.add(Math.abs(ThreadLocalRandom.current().nextInt() % 100));
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        for (int i = 0; i < 10; i++) {
            es.execute(() -> {
                while (System.currentTimeMillis() < endTime) {
                    System.out.println(set.size());
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
}
