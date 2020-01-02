package util;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ObjectTest {
    @Test
    public void testWaitNotify() throws InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();
        Object object = new Object();

        Runnable run = () -> {
            try {
                for (int i = 0; i < 100; i++) {
                    synchronized (object) {
                        System.out.println(Thread.currentThread().getName());
                        object.notify();
                        object.wait();
                    }
                }
                synchronized (object) {
                    object.notifyAll();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        es.execute(run);
        es.execute(run);

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
    public void testObject() throws CloneNotSupportedException {
        {
            Point point = new Point(123, 456);
            assertEquals(point.hashCode(), 16151955);
            assertEquals(point.toString(), "[123, 456]");
            assertTrue(point.equals(new Point(123, 456)));
        }
        {
            Point point = new Point(123, 456);
            Point copyPoint = (Point) point.clone();
            copyPoint.x = 234;
            assertEquals(point.x, 123);
            assertEquals(copyPoint.x, 234);
        }
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        {
            ShallowCopyLine line = new ShallowCopyLine(1, 2, 3, 4);
            ShallowCopyLine copyLine = (ShallowCopyLine) line.clone();

            assertEquals(copyLine.p1.x, 1);
            assertEquals(copyLine.p1.y, 2);
            assertEquals(copyLine.p2.x, 3);
            assertEquals(copyLine.p2.y, 4);

            line.p1.x = 5;
            line.p1.y = 6;
            line.p2.x = 7;
            line.p2.y = 8;
            assertEquals(copyLine.p1.x, 5);
            assertEquals(copyLine.p1.y, 6);
            assertEquals(copyLine.p2.x, 7);
            assertEquals(copyLine.p2.y, 8);
        }
        {
            DeepCopyLine line = new DeepCopyLine(1, 2, 3, 4);
            DeepCopyLine copyLine = (DeepCopyLine) line.clone();

            assertEquals(copyLine.p1.x, 1);
            assertEquals(copyLine.p1.y, 2);
            assertEquals(copyLine.p2.x, 3);
            assertEquals(copyLine.p2.y, 4);

            line.p1.x = 5;
            line.p1.y = 6;
            line.p2.x = 7;
            line.p2.y = 8;
            assertEquals(copyLine.p1.x, 1);
            assertEquals(copyLine.p1.y, 2);
            assertEquals(copyLine.p2.x, 3);
            assertEquals(copyLine.p2.y, 4);
        }
    }

    class DeepCopyLine {
        Point p1;
        Point p2;

        DeepCopyLine() {
            p1 = new Point(0, 0);
            p2 = new Point(0, 0);
        }

        DeepCopyLine(int x1, int y1, int x2, int y2) {
            p1 = new Point(x1, y1);
            p2 = new Point(x2, y2);
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            DeepCopyLine line = new DeepCopyLine();
            line.p1 = (Point) p1.clone();
            line.p2 = (Point) p2.clone();
            return line;
        }
    }

    class ShallowCopyLine implements Cloneable {
        Point p1;
        Point p2;

        ShallowCopyLine(int x1, int y1, int x2, int y2) {
            p1 = new Point(x1, y1);
            p2 = new Point(x2, y2);
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    class Point implements Cloneable {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return x * 131313 + y;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Point)) {
                return false;
            }
            Point other = (Point) obj;
            return x == other.x && y == other.y;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            sb.append(x);
            sb.append(", ");
            sb.append(y);
            sb.append(']');
            return sb.toString();
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
}
