# java 线程同步

java 提供了多种线程同步的方式

- `Semaphore`: 信号量，实现简单，使用灵活
- `CyclicBarrier`: 屏障，用于协同多个线程同时开始执行
- `CountDownLatch`: 同步计数器，用于等待多个线程完成

## Semaphore

信号量主要提供了两个方法:

- `acquire`: 获取信号量，相当于原子变量 -1，当信号量为 0 时，就会阻塞
- `release`: 释放信号量，相当于原子变量 +1

下面代码使用信号量实现了生产者消费者问题

``` java
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
```

下面代码用信号量实现了临界区的功能，和 `synchronized` 关键词的效果一样

``` java
ExecutorService es = Executors.newCachedThreadPool();
Semaphore semaphore = new Semaphore(1);
long endTime = System.currentTimeMillis() + 100;
for (int i = 0; i < 10; i++) {
    es.execute(() -> {
        try {
            while (System.currentTimeMillis() < endTime) {
                semaphore.acquire();
                System.out.println("critical section");
                semaphore.release();
            }
        } catch (InterruptedException e) {
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
```

## CountDownLatch

`CountDownLatch` 的工作模式是构造时创建固定数量的计数器，每次调用 `countDown` 计数器减一，当减到 0 时，阻塞在 `await` 上的线程将被唤醒

`CountDownLatch` 提供了如下接口:

- `countDown`: 计数器减一
- `await`: 阻塞等待，直到计数器清零

``` java
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
```

## CyclicBarrier

`CyclicBarrier` 的工作模式是多个线程分别调用 `await`，直到满足构造时的数量后，唤醒所有线程继续往下执行

`CyclicBarrier` 提供如下方法:

- `await`: 阻塞等待，当调用次数达到构造时的数量时，唤醒所有线程

``` java
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
```

## 链接

- Samaphore 测试代码: <https://github.com/hatlonely/hellojava/blob/master/src/test/java/concurrent/SemaphoreTest.java>
- CountDownLatch 测试代码: <https://github.com/hatlonely/hellojava/blob/master/src/test/java/concurrent/CountDownLatchTest.java>
- CyclicBarrier 测试代码: <https://github.com/hatlonely/hellojava/blob/master/src/test/java/concurrent/CyclicBarrierTest.java>
