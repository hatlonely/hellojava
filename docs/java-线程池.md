# java 线程池

Java 的 `concurrent` 包下提供了多种线程池的实现，使用起来非常方便

## ExecutorService

`ExecutorService` 是线程池的抽象接口，`concurrent` 包提供了如下如下几个线程池的实现

- `Executors.newSingleThreadExecutor`: 仅由一个线程组成的线程池
- `Executors.newFixedThreadPool(num)`: 固定线程数量的线程池
- `Executors.newCachedThreadPool`: 按需创建新的线程，用完的线程放入线程池重复使用，空闲的线程会在 60s 后释放
- `Executors.newWorkStealingPool()`: 工作窃取线程池，内部有固定数量（cpu 核数）的线程，如果当前线程的任务完成，会窃取其他线程的任务，其实就是 `ForkJoinPool`
- `Executors.newSingleThreadScheduledExecutor`: 支持延迟执行的单个线程池
- `Executors.newScheduledThreadPool(num)`: 支持延迟执行的固定数量的线程池

`ExecutorService` 主要提供如下几个接口

- `execute(runnable)`: 将一个 runnable 任务放到线程池中执行
- `submit(callable)`: 提交一个 callable 任务，返回一个 future 对象，可以获取 callable 的返回值
- `invokeAll`: 提交集合中所有的 callable
- `shutdown`: 关闭线程池，等待当前所有线程的完成正在执行的任务，执行完成后，线程退出，这个函数只是发出退出信号，并不会阻塞等待线程退出
- `awaitTermination`: 等待所有线程退出，需要提前掉用 `shutdown`

``` java
ExecutorService es1 = Executors.newSingleThreadExecutor();
ExecutorService es2 = Executors.newFixedThreadPool(4);
ExecutorService es3 = Executors.newCachedThreadPool();
ExecutorService es4 = Executors.newWorkStealingPool();
ScheduledExecutorService es5 = Executors.newSingleThreadScheduledExecutor();
ScheduledExecutorService es6 = Executors.newScheduledThreadPool(4);
```

## execute

执行一个没有返回值的任务

``` java
ExecutorService es = Executors.newFixedThreadPool(4);

for (int i = 0; i < 10; i++) {
    // execute 执行一个没有返回值的任务
    es.execute(() -> System.out.println("hello world"));
}

try {
    es.shutdown();
    while (!es.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
        // nothing to do
    }
} catch (Exception e) {
    e.printStackTrace();
}
```

## submit

提交一个有返回值的任务

``` java
class Power implements Callable<Integer> {
    private final int i;

    private Power(int i) {
        this.i = i;
    }

    @Override
    public Integer call() throws Exception {
        return i * i;
    }
}

ExecutorService es = Executors.newCachedThreadPool();
List<Future<Integer>> res = Lists.newArrayListWithCapacity(10);
for (int i = 0; i < 10; i++) {
    // submit 提交一个有返回值的任务，通过 Future 对象获取返回值
    res.add(es.submit(new Power(i)));
}

for (int i = 0; i < 10; i++) {
    try {
        Future<Integer> future = res.get(i);    // 阻塞等待任务完成
        System.out.println(future.get());
    } catch (Exception e) {
        e.printStackTrace();
    }
}

try {
    es.shutdown();
    while (!es.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
        // nothing to do
    }
} catch (Exception e) {
    e.printStackTrace();
}
```

## ScheduledExecutor

延迟执行的线程池，提供一个新的接口

- `schedule`: 延迟执行任务

``` java
ScheduledExecutorService es = Executors.newScheduledThreadPool(4);
for (int i = 0; i < 10; i++) {
    es.schedule(() -> System.out.println("hello world"), i, TimeUnit.SECONDS);
}
for (int i = 0; i < 10; i++) {
    es.schedule(() -> {
        int sum = 0;
        for (int j = 0; j < 10; j++) {
            sum += j;
        }
        return sum;
    }, i, TimeUnit.SECONDS);
}


try {
    es.shutdown();
    while (!es.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
        // nothing to do
    }
} catch (Exception e) {
    e.printStackTrace();
}
```

## 链接

- 测试代码: <https://github.com/hatlonely/hellojava/blob/master/src/test/java/concurrent/ExecutorServiceTest.java>
