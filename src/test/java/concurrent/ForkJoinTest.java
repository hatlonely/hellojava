package concurrent;

import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest {
    @Test
    public void testRecursiveAction() {
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
                    PowerList r = new PowerList(list, middle, end);
                    l.compute();    // 左边的任务在当前线程中完成
                    r.fork();       // 右边的任务在新的线程中完成
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
    }

    @Test
    public void testRecursiveTask() {
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
    }
}
