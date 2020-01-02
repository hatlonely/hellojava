# java copy on write

copy on write 的语义是当写入或者修改操作发生时，复制一份当前的数据，然后在新的数据上修改，修改完成后将数据引用指向新的数据，整个过程无需加锁，可以有效减少竞争带来的开销，但因为复制也带来新的性能开销，比较适合多读少写的场景，除此之外，在复制写入的这个过程中，由于无法读取到最新的数据，可能造成短暂的不一致，但最终是一致的

concurrent 包提供主要提供了两种 copy on write 的数据结构

- `CopyOnWriteArrayList`: copy on write list
- `CopyOnWriteArraySet`: copy on write set

## CopyOnWriteArrayList

``` java
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
```

## CopyOnWriteArraySet

``` java
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
```

## 链接

- 测试代码: <https://github.com/hatlonely/hellojava/blob/master/src/test/java/concurrent/CopyOnWriteTest.java>
