# java concurrent

concurrent 的语义是线程安全，可以再多线程的场景下使用，数据结构本身已经封装好了同步机制，不需要额外同步机制

concurrent 包下提供了5种数据结构，包括

- `ConcurrentHashMap`: hash 实现的线程安全 Map，内部采用多段锁减少竞争，提高性能
- `ConcurrentSkipListMap`: skip list 实现的线程安全的 Map
- `ConcurrentSkipListSet`: skip list 实现的线程安全的 Set
- `ConcurrentLinkedQueue`: 链表实现的线程安全的 Queue
- `ConcurrentLinkedDeque`: 链表实现的线程安全的 Deque

## ConcurrentHashMap

``` java
ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
ExecutorService es = Executors.newCachedThreadPool();

long endTime = System.currentTimeMillis() + 100;
for (int i = 0; i < 5; i++) {
    es.execute(() -> {
        while (System.currentTimeMillis() < endTime) {
            for (int j = 0; j < 100; j++) {
                map.put("key" + j, "val" + j);
            }
        }
    });
}

for (int i = 0; i < 5; i++) {
    es.execute(() -> {
        while (System.currentTimeMillis() < endTime) {
            for (int j = 0; j < 100; j++) {
                System.out.println(map.get("key" + j));
            }
        }
    });
}

for (int i = 0; i < 2; i++) {
    es.execute(() -> {
        while (System.currentTimeMillis() < endTime) {
            for (int j = 0; j < 10; j++) {
                map.remove("key" + Math.abs(ThreadLocalRandom.current().nextInt() % 100));
            }
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

## ConcurrentSkipListMap

``` java
ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
ExecutorService es = Executors.newCachedThreadPool();

long endTime = System.currentTimeMillis() + 100;
for (int i = 0; i < 5; i++) {
    es.execute(() -> {
        while (System.currentTimeMillis() < endTime) {
            for (int j = 0; j < 100; j++) {
                map.put("key" + j, "val" + j);
            }
        }
    });
}

for (int i = 0; i < 5; i++) {
    es.execute(() -> {
        while (System.currentTimeMillis() < endTime) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                System.out.println(entry.getKey() + " => " + entry.getValue());
            }
        }
    });
}

for (int i = 0; i < 2; i++) {
    es.execute(() -> {
        while (System.currentTimeMillis() < endTime) {
            for (int j = 0; j < 10; j++) {
                map.remove("key" + Math.abs(ThreadLocalRandom.current().nextInt() % 100));
            }
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

## ConcurrentSkipListSet

``` java
ConcurrentSkipListSet<String> set = new ConcurrentSkipListSet<>();
ExecutorService es = Executors.newCachedThreadPool();

long endTime = System.currentTimeMillis() + 100;
for (int i = 0; i < 5; i++) {
    es.execute(() -> {
        while (System.currentTimeMillis() < endTime) {
            for (int j = 0; j < 100; j++) {
                set.add("key" + j);
            }
        }
    });
}

for (int i = 0; i < 5; i++) {
    es.execute(() -> {
        while (System.currentTimeMillis() < endTime) {
            for (int j = 0; j < 100; j++) {
                set.contains("key" + j);
            }
        }
    });
}

for (int i = 0; i < 2; i++) {
    es.execute(() -> {
        while (System.currentTimeMillis() < endTime) {
            set.remove("key" + Math.abs(ThreadLocalRandom.current().nextInt() % 100));
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

## ConcurrentLinkedQueue

``` java
ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
ExecutorService es = Executors.newCachedThreadPool();

for (int i = 0; i < 3; i++) {
    es.execute(() -> {
        for (int j = 0; j < 20; j++) {
            queue.offer(Math.abs(ThreadLocalRandom.current().nextInt() % 100));
        }
    });

    es.execute(() -> {
        for (int j = 0; j < 20; j++) {
            System.out.println("poll " + queue.poll());
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

## ConcurrentLinkedDeque

``` java
ConcurrentLinkedDeque<Integer> queue = new ConcurrentLinkedDeque<>();
ExecutorService es = Executors.newCachedThreadPool();

for (int i = 0; i < 3; i++) {
    es.execute(() -> {
        for (int j = 0; j < 10; j++) {
            queue.offerFirst(Math.abs(ThreadLocalRandom.current().nextInt() % 100));
        }
        for (int j = 0; j < 10; j++) {
            queue.offerLast(Math.abs(ThreadLocalRandom.current().nextInt() % 100));
        }
        for (int j = 0; j < 10; j++) {
            System.out.println("poll " + queue.pollFirst());
        }
        for (int j = 0; j < 10; j++) {
            System.out.println("poll " + queue.pollLast());
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

- 测试代码: <https://github.com/hatlonely/hellojava/blob/master/src/test/java/concurrent/ConcurrentTest.java>
