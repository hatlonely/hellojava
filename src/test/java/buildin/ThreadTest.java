package buildin;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

public class ThreadTest {
    @Test
    public void testThread() {
        class MyThread extends Thread {
            private String name;

            public MyThread(String name) {
                this.name = name;
            }

            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(100L);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.printf("%s is running %d\n", name, i);
                }
            }
        }

        {
            Thread t1 = new MyThread("t1");
            Thread t2 = new MyThread("t2");

            t1.start();
            t2.start();
            try {
                t1.join();
                t2.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testRunnable() {
        class MyRunnable implements Runnable {
            private String name;

            public MyRunnable(String name) {
                this.name = name;
            }

            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(100L);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.printf("%s is running %d\n", name, i);
                }
            }
        }

        {
            Thread r1 = new Thread(new MyRunnable("r1"));
            Thread r2 = new Thread(new MyRunnable("r2"));

            r1.start();
            r2.start();
            try {
                r1.join();
                r2.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testCallable() {
        class MyCallable implements Callable<Integer> {
            private Random random;

            public MyCallable() {
                this.random = new Random();
            }

            @Override
            public Integer call() throws Exception {
                Thread.sleep(100L);
                return this.random.nextInt();
            }
        }

        {
            FutureTask<Integer> future1 = new FutureTask<>(new MyCallable());
            FutureTask<Integer> future2 = new FutureTask<>(new MyCallable());
            new Thread(future1).start();
            new Thread(future2).start();

            try {
                System.out.println(future1.get(50, TimeUnit.MILLISECONDS));
            } catch (TimeoutException e) {
                System.out.println("future1 timeout");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                System.out.println(future2.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testThreadPool() {
        class MyCallable implements Callable<Integer> {
            private Random random;

            public MyCallable() {
                this.random = new Random();
            }

            @Override
            public Integer call() throws Exception {
                Thread.sleep(100L);
                return this.random.nextInt();
            }
        }

        {
            ExecutorService es = Executors.newFixedThreadPool(5);
            Future<Integer> future1 = es.submit(new MyCallable());
            Future<Integer> future2 = es.submit(new MyCallable());

            try {
                System.out.println(future1.get(50, TimeUnit.MILLISECONDS));
            } catch (TimeoutException e) {
                System.out.println("future1 timeout");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                System.out.println(future2.get());
            } catch (Exception e) {
                e.printStackTrace();
            }

            es.shutdown();
        }

        {
            ExecutorService es = Executors.newCachedThreadPool();
            CompletionService<Integer> cs = new ExecutorCompletionService<>(es);
            cs.submit(new MyCallable());
            cs.submit(new MyCallable());
            cs.submit(new MyCallable());
            cs.submit(new MyCallable());

            try {
                System.out.println(cs.take().get());
                System.out.println(cs.take().get());
                System.out.println(cs.take().get());
                System.out.println(cs.take().get());
            } catch (Exception e) {
                e.printStackTrace();
            }

            es.shutdown();
        }
    }
}