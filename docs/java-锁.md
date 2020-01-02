# java 锁

锁是另外一种经典的线程同步方式，Java 提供了三种锁

- `ReentrantLock`: 独占锁
- `ReentrantReadWriteLock`: 读写锁
- `StampedLock`: 乐观锁

## 独占锁

顾名思义，独占锁同一时间只能被一个线程持有，线程通过调用 `lock` 获取锁，用完之后通过 `unlock` 释放锁

- `lock`: 获取锁
- `unlock`: 释放锁

``` java
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
```

## 读写锁

读写锁，将读写操作分离，写时独占，读时共享

- `readLock`: 获取读锁
- `writeLock`: 获取写锁

``` java
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
```

## 乐观锁

大部分场景下读写锁表现很好，但是在大量读的场景下，写线程可能很难拿到写锁，因为读锁会被不停地占用，从而造成饥饿，而乐观锁可以很好的解决这个问题，乐观读时不加锁，读完之后再去检查当前的数据版本是否有效(读取的过程中是否有新的更新)，这样理想情况下可以实现无锁的读取，同时又不会造成饥饿现象，极大的提升了性能

乐观锁的工作模式是，通过 `tryOptimisticRead` 获取一个版本号，然后读取数据，数据读取完之后再去判断这个版本是否已经过时，如果已经过时，可以重复这个过程，直到读到一个有效的数据

- `writeLock`: 获取写锁
- `unlockWrite`: 释放写锁
- `readLock`: 获取读锁
- `unlockRead`: 释放写锁
- `tryOptimisticRead`: 尝试乐观读，不锁写

``` java
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
```

## 链接

- 测试代码: <https://github.com/hatlonely/hellojava/blob/master/src/test/java/concurrent/LockTest.java>
