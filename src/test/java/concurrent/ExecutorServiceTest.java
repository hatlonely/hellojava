package concurrent;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.*;

public class ExecutorServiceTest {
    @Test
    public void testNewExecuteService() {
        // 单线程池
        ExecutorService es1 = Executors.newSingleThreadExecutor();
        // 固定线程数量
        ExecutorService es2 = Executors.newFixedThreadPool(4);
        // 按照需要创建新的线程，用完的线程会放入线程池重复使用，空闲的线程会在 60s 之后释放
        ExecutorService es3 = Executors.newCachedThreadPool();
        // 工作窃取线程池，内部有固定数量（cpu 核数）的线程
        // 如果当前线程的任务完成，会窃取其他线程的任务
        ExecutorService es4 = Executors.newWorkStealingPool();
        //
        ScheduledExecutorService es5 = Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorService es6 = Executors.newScheduledThreadPool(4);
    }

    @Test
    public void testExecute() {
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
    }

    @Test
    public void testSubmit() {
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
    }

    @Test
    public void testSchedulerExecutor() {
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
    }
}
