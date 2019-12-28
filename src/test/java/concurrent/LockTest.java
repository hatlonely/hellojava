package concurrent;

import org.junit.Test;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

public class LockTest {
    @Test
    public void testReentrantLock() {
        // 独占锁
        ReentrantLock lock = new ReentrantLock();
        ExecutorService es = Executors.newCachedThreadPool();

        long endTime = System.currentTimeMillis() + 1000;
        Set<Integer> set = new TreeSet<>();
        for (int i = 0; i < 10; i++) {
            es.execute(() -> {
                while (System.currentTimeMillis() < endTime) {
                    lock.lock();
                    set.add(Math.abs(ThreadLocalRandom.current().nextInt() % 100));
                    lock.unlock();
                }
            });

            es.execute(() -> {
                while (System.currentTimeMillis() < endTime) {
                    lock.lock();
                    set.remove(Math.abs(ThreadLocalRandom.current().nextInt() % 100));
                    lock.unlock();
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
    public void testReentrantReadWriteLock() {
        // 读写锁
        ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();
        ExecutorService es = Executors.newCachedThreadPool();

        long endTime = System.currentTimeMillis() + 1000;
        Set<Integer> set = new TreeSet<>();
        for (int i = 0; i < 10; i++) {
            es.execute(() -> {
                while (System.currentTimeMillis() < endTime) {
                    rwlock.writeLock().lock();
                    set.add(Math.abs(ThreadLocalRandom.current().nextInt() % 100));
                    rwlock.writeLock().unlock();
                }
            });

            es.execute(() -> {
                while (System.currentTimeMillis() < endTime) {
                    rwlock.writeLock().lock();
                    set.remove(Math.abs(ThreadLocalRandom.current().nextInt() % 100));
                    rwlock.writeLock().unlock();
                }
            });

            es.execute(() -> {
                while (System.currentTimeMillis() < endTime) {
                    rwlock.readLock().lock();
                    set.contains(Math.abs(ThreadLocalRandom.current().nextInt() % 100));
                    rwlock.readLock().unlock();
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
    public void testStampedLock() {
        class MyClass {
            private int v = 0;
        }

        // 乐观锁
        StampedLock lock = new StampedLock();
        ExecutorService es = Executors.newCachedThreadPool();

        long endTime = System.currentTimeMillis() + 100;
        MyClass obj = new MyClass();
        for (int i = 0; i < 4; i++) {
            es.execute(() -> {
                while (System.currentTimeMillis() < endTime) {
                    long stamp = lock.writeLock();
                    obj.v = Math.abs(ThreadLocalRandom.current().nextInt() % 100);
                    lock.unlockWrite(stamp);
                }
            });

            es.execute(() -> {
                while (System.currentTimeMillis() < endTime) {
                    int v;
                    while (true) {
                        long stamp = lock.tryOptimisticRead();  // 乐观读，不锁写
                        v = obj.v;
                        if (lock.validate(stamp)) {             // 如果读期间，有写入，这个判断会失败
                            break;
                        }
                    }
                    System.out.println(v);
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
