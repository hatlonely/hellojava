# java atomic

原子变量提供各种原子操作，多线程场景下操作不需要加锁，性能非常好

## 简例

``` java
AtomicInteger ai = new AtomicInteger(10);
ExecutorService es = Executors.newCachedThreadPool();

long endTime = System.currentTimeMillis() + 100;
for (int i = 0; i < 10; i++) {
    es.execute(() -> {
        while (System.currentTimeMillis() < endTime) {
            System.out.println(ai.incrementAndGet());
            System.out.println(ai.addAndGet(1));
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

## AtomicInteger

- `get`: 返回当前 int 值
- `incrementAndGet`: 自增，返回自增后的值
- `getAndIncrement`: 自增，发挥自增前的值
- `addAndGet`: 加上一个 int，并且获取加上之后的值
- `getAndAdd`: 加上一个 int，并且返回加上之前的值
- `compareAndSet`: 当前值与参数相等时，才设置当前值，返回是否设置成功
- `compareAndExchange`: 当前值与参数相等时，才设置当前值，返回老的值

``` java
AtomicInteger i = new AtomicInteger(10);
assertEquals(i.get(), 10);
assertEquals(i.incrementAndGet(), 11);
assertEquals(i.getAndIncrement(), 11);
assertEquals(i.get(), 12);
assertEquals(i.addAndGet(10), 22);
assertEquals(i.getAndAdd(10), 22);
assertEquals(i.get(), 32);
assertTrue(i.compareAndSet(32, 10));
assertFalse(i.compareAndSet(32, 10));
assertEquals(i.compareAndExchange(10, 22), 10);
assertEquals(i.compareAndExchange(10, 22), 22);
```

## AtomicIntegerArray

和 `AtomicInteger` 的接口类似，只是每个接口都增加一个 `index` 参数

``` java
AtomicIntegerArray ia = new AtomicIntegerArray(10);

for (int i = 0; i < 10; i++) {
    ia.set(i, i);
}

assertEquals(ia.get(5), 5);
assertEquals(ia.incrementAndGet(5), 6);
assertEquals(ia.incrementAndGet(5), 7);
assertEquals(ia.addAndGet(5, 10), 17);
assertTrue(ia.compareAndSet(5, 17, 15));
assertEquals(ia.compareAndExchange(5, 15, 10), 15); // 返回老值
```

## AtomicReference

- `get`: 获取当前引用对象
- `getAndSet`: 设置新的引用，并返回老的引用
- `compareAndSet`: 当前值与参数相等时，才设置当前值，返回是否设置成功
- `compareAndExchange`: 当前值与参数相等时，才设置当前值，返回老的值
- `accumulateAndGet`: 用当前值和参数一起执行 BinaryOpterator 的结果设置成当前值，返回新的值
- `getAndAccumulate`: 用当前值和参数一起执行 BinaryOpterator 的结果设置成当前值，返回老的值

``` java
 AtomicReference<Integer> i = new AtomicReference<>();
assertEquals(i.get(), null);
assertEquals(i.getAndSet(10), null);
assertEquals(i.get(), Integer.valueOf(10));
assertEquals(i.compareAndExchange(10, 11), Integer.valueOf(10));
assertTrue(i.compareAndSet(11, 12));
assertEquals(i.accumulateAndGet(3, (x, y) -> x + y), Integer.valueOf(15));
assertEquals(i.getAndAccumulate(3, (x, y) -> x + y), Integer.valueOf(15));
```

## AtomicStampedReference

在 atomic 的基础上增加一个 stamp 概念，stamp 可理解为一个版本号，即使两个相等值相等，还可以通过版本号来区分

``` java
AtomicStampedReference<Integer> i = new AtomicStampedReference<>(null, 0);
assertEquals(i.getReference(), null);
assertEquals(i.getStamp(), 0);
assertTrue(i.compareAndSet(null, 10, 0, 1));
assertEquals(i.getReference(), Integer.valueOf(10));
assertEquals(i.getStamp(), 1);
```

## LongAccumulator

`LongAccumulator` 累加器，内部采用多个 `atomic` 变量实现，减少由于多线程竞争带来的性能开销，`LongAccumulator` 通过一个 `BinaryOperator` 和一个初始值构造而成，而 `LongAddr` 相当于 `new LongAccumulator((x, y) -> x + y, 0)`

``` java
 {
    LongAccumulator i = new LongAccumulator((x, y) -> x + y, 0);
    i.accumulate(10);
    i.accumulate(20);
    assertEquals(i.get(), 30);
}
{
    LongAdder j = new LongAdder();
    j.add(10);
    j.add(20);
    assertEquals(j.sum(), 30);
}
```
