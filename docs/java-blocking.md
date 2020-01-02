# java blocking

blocking 语义是阻塞，blocking 的数据结构除了线程安全外，还提供额外的阻塞接口，concurrent 包主要提供了 6 种阻塞的数据结构

- `ArrayBlockingQueue`: 数组实现的阻塞队列
- `LinkedBlockingQueue`: 链表实现的阻塞队列
- `LinkedTransferQueue` 链表实现的阻塞队列，如果队列为空，会在队列中插入空节点，后续生成者直接将元素直接交给消费者，不入队列
- `SynchronousQueue`: 没有缓冲区的队列
- `PriorityBlockingQueue`: 阻塞的优先队列，元素会按从小到大排序
- `LinkedBlockingDeque`: 链表实现的 Deque

## BlockingQueue

`BlockingQueue` 在 Queue 的基础上新增了几个阻塞接口

- `put`: 放入一个元素到队列中，如果队列满了，阻塞直到有空间
- `take`: 从队列中拿出一个元素，如果队列为空，阻塞直到有元素
- `offer`: 放入一个元素到队列中，如果队列满了，阻塞等待，或者超时返回 false
- `poll`: 从队列中拿出一个元素，如果队列为空，阻塞等待，或者超时返回 null

``` java
BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(100);
ExecutorService es = Executors.newCachedThreadPool();

// 生产者
for (int i = 0; i < 3; i++) {
    es.execute(() -> {
        try {
            for (int j = 0; j < 10; j++) {
                queue.put(Math.abs(ThreadLocalRandom.current().nextInt() % 100));
            }
            for (int j = 0; j < 10; j++) {
                queue.offer(Math.abs(ThreadLocalRandom.current().nextInt() % 100), 1000, TimeUnit.MILLISECONDS);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    });
}

// 消费者
for (int i = 0; i < 3; i++) {
    es.execute(() -> {
        try {
            for (int j = 0; j < 10; j++) {
                System.out.println("take " + queue.take());
            }
            for (int j = 0; j < 10; j++) {
                System.out.println("poll " + queue.poll(1000, TimeUnit.MILLISECONDS));
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

## LinkedTransferQueue

`LinkedTransferQueue` 消费者线程取元素时，如果队列不为空，则直接取走数据，若队列为空，那就生成一个节点（节点元素为null）入队，然后消费者线程等待在这个节点上，后面生产者线程入队时发现有一个元素为 null 的节点，生产者线程就不入队了，直接就将元素填充到该节点，并唤醒该节点等待的线程，被唤醒的消费者线程取走元素，从调用的方法返回

``` java
TransferQueue<Integer> queue = new LinkedTransferQueue<>();
ExecutorService es = Executors.newCachedThreadPool();

for (int i = 0; i < 3; i++) {
    es.execute(() -> {
        try {
            for (int j = 0; j < 10; j++) {
                queue.transfer(Math.abs(ThreadLocalRandom.current().nextInt() % 100));
            }
            for (int j = 0; j < 10; j++) {
                queue.tryTransfer(Math.abs(ThreadLocalRandom.current().nextInt() % 100), 1000, TimeUnit.MILLISECONDS);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    });
}

for (int i = 0; i < 3; i++) {
    es.execute(() -> {
        try {
            for (int j = 0; j < 20; j++) {
                System.out.println("poll " + queue.take());
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

## LinkedBlockingDeque

`BlockingDeque` 在 `BlockingQueue` 的基础上新增了如下接口

- `putFirst`: 从头部插入，如果队列已满，阻塞直到有空间
- `putLast`: 从尾部插入，如果队列已满，阻塞直到有空间
- `takeFirst`: 从头部获取，如果队列已空，阻塞直到有元素
- `takeLast`: 从尾部获取，如果队列已空，阻塞直到有元素
- `offerFirst`: 从头部插入，如果队列已满，阻塞直到有空间，或者超时返回 false
- `offerLast`: 从尾部插入，如果队列已满，阻塞直到有空间，或者超时返回 false
- `pollFirst`: 从头部获取，如果队列已空，阻塞直到有元素，或者超时返回 null
- `pollLast`: 从尾部获取，如果队列为空，阻塞直到有元素，或者超时返回 null

``` java
BlockingDeque<Integer> queue = new LinkedBlockingDeque<>();
ExecutorService es = Executors.newCachedThreadPool();

// 生产者
for (int i = 0; i < 3; i++) {
    es.execute(() -> {
        try {
            for (int j = 0; j < 10; j++) {
                queue.putFirst(Math.abs(ThreadLocalRandom.current().nextInt() % 100));
            }
            for (int j = 0; j < 10; j++) {
                queue.putLast(Math.abs(ThreadLocalRandom.current().nextInt() % 100));
            }
            for (int j = 0; j < 10; j++) {
                queue.offerFirst(Math.abs(ThreadLocalRandom.current().nextInt() % 100), 200, TimeUnit.MILLISECONDS);
            }
            for (int j = 0; j < 10; j++) {
                queue.offerLast(Math.abs(ThreadLocalRandom.current().nextInt() % 100), 200, TimeUnit.MILLISECONDS);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    });
}

// 消费者
for (int i = 0; i < 3; i++) {
    es.execute(() -> {
        try {
            for (int j = 0; j < 10; j++) {
                System.out.println("take " + queue.takeFirst());
            }
            for (int j = 0; j < 10; j++) {
                System.out.println("take " + queue.takeLast());
            }
            for (int j = 0; j < 10; j++) {
                System.out.println("take " + queue.pollFirst(200, TimeUnit.MILLISECONDS));
            }
            for (int j = 0; j < 10; j++) {
                System.out.println("take " + queue.pollLast(200, TimeUnit.MILLISECONDS));
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

## 链接

- 测试代码: <https://github.com/hatlonely/hellojava/blob/master/src/test/java/concurrent/BlockingTest.java>
