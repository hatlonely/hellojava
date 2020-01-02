# java forkjoin

`ForkJoin` 是 java 1.7 提供了一套新的并行计算框架，它的思想类似于大数据处理中的 MapReduce，把大任务拆分了多个小任务，最后再将小任务的结果汇总

它的模型大概是这样，申请一个固定数量的线程池(一般和 cpu 的核数相当)，每个线程都有一个任务队列，当自己的任务队列为空时，会从其他线程的队列中“窃取”任务，`Executors.newWorkStealingPool` 实际上就是将 `ForkJoin` 封装成了一个线程池

`ForkJoin` 可以处理两种任务，`RecursiveAction` 没有返回值，不需要汇总结果的任务，以及 `RecursiveTask` 有返回值，最后可以汇总结果的任务

## RecursiveAction

`RecursiveAction` 提供了 `fork` 方法，这个方法会将新的任务放到线程池中，通过这个方法可以完成对任务的拆分

下面代码完成了并发地对数组每个元素进行平方的操作，这里的关键就是实现 `RecursiveAction` 的 `compute` 的方法: 用二分法将任务拆分，左边的任务放到线程池中，右边的任务继续在当前的线程中完成计算，当一个任务中的元素小于10个时不再拆分

``` java
class PowerList extends RecursiveAction {
    private static final long serialVersionUID = 3291059130246730585L;
    private final ArrayList<Integer> list;
    private final int start;
    private final int end;

    private PowerList(ArrayList<Integer> list, int start, int end) {
        this.list = list;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        System.out.println(Thread.currentThread().getId() + " [" + start + ", " + end + ")");
        if (end - start < 10) {
            for (int i = start; i < end; i++) {
                int k = list.get(i);
                list.set(i, k * k);
            }
        } else {
            int middle = (end + start) / 2;
            PowerList l = new PowerList(list, start, middle);
            l.fork();       // 左边的任务在新的线程中完成
            PowerList r = new PowerList(list, middle, end);
            r.compute();    // 右边的任务在当前线程中完成
        }
    }
}

ForkJoinPool pool = new ForkJoinPool(4);
ArrayList<Integer> list = new ArrayList<>();

for (int i = 0; i < 100; i++) {
    list.add(i);
}

PowerList action = new PowerList(list, 0, list.size());
pool.submit(action);
action.join();

for (int i = 0; i < 100; i++) {
    System.out.println("list[" + i + "] = " + list.get(i));
}
```

## RecursiveTask

和 `RecursiveAction` 一样，`RecursiveTask` 也继承自 `ForkJoinTask` 提供了 `fork` 方法，另外还提供了 `join` 方法，等待子任务完成计算，这里的 `join` 会阻塞当前任务，当并不会阻塞线程，线程会从任务队列中拿一个新的任务去执行，因此执行效率是非常高的

下面代码完成了对数组并发求和的操作，和上面一样，用二分法将任务拆分，右边的任务放到线程池中，左边的任务在当前线程中完成计算，并等待右边的计算结果

``` java
class SumList extends RecursiveTask<Integer> {
    private static final long serialVersionUID = -3250022364259675865L;
    private final ArrayList<Integer> list;
    private final int start;
    private final int end;

    private SumList(ArrayList<Integer> list, int start, int end) {
        this.list = list;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        System.out.println(Thread.currentThread().getId() + " [" + start + ", " + end + ")");
        if (end - start < 10) {
            int sum = 0;
            for (int i = start; i < end; i++) {
                sum += list.get(i);
            }
            return sum;
        }

        int middle = (end + start) / 2;
        SumList l = new SumList(list, start, middle);
        SumList r = new SumList(list, middle, end);
        r.fork();
        return l.compute() + r.join();
    }
}

ArrayList<Integer> list = new ArrayList<>();
for (int i = 0; i < 10000; i++) {
    list.add(i);
}

ForkJoinPool pool = new ForkJoinPool(4);
SumList task = new SumList(list, 0, list.size());
pool.submit(task);

System.out.println("sum: " + task.join());
```

## 链接

- 测试代码: <https://github.com/hatlonely/hellojava/blob/master/src/test/java/concurrent/ForkJoinTest.java>

