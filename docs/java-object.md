# java object

## 简介

`Object` 是所有类的默认基类，提供了几个基础的方法

- `hashCode`: 将对象映射成一个整型，JVM 将每个 new 出来的对象丢到一个 hash 表中，不相等的对象可能拥有相同的 hash 值，但相等的对象一定拥有相同的 hash 值
- `toString`: 将对象转成字符串，默认输出对象的引用地址
- `equals`: 判断是否和另一个对象相等，默认两对象为同一对象才返回真

``` java
Point point = new Point(123, 456);
assertEquals(point.hashCode(), 16151955);
assertEquals(point.toString(), "[123, 456]");
assertTrue(point.equals(new Point(123, 456)));

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
```

## 深拷贝与浅拷贝

Java 的赋值操作都只传递引用，如果需要拷贝整个对象，就需要调用 `clone` 方法，`clone` 方法被声明成 `protected`，要想公开使用，可以自己重载

`Cloneable` 接口，给 `Object` 提供了快速复制的能力，只需要调用 `super.clone()` 即可，和每个字段都按照等号赋值得到的结果是一样的，因此如果对象内部还有其他对象，只能获取到对象的引用，也就是**浅拷贝**；但有时这并不符合我们的期望，我们希望内部对象调用内部的 clone 方法同样生成一个新的对象，这个时候我们就需要自己实现 `clone` 方法

``` java
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
```

## 对象同步

Java 给每个对象都内置一个对象锁(monitor lock)，这个对象锁用于 `synchronized` 修饰的代码段中

- `wait`: 释放当前持有的对象锁，并进入阻塞状态，等待 notify 信号重新获取对象锁
- `notify`: 唤醒阻塞在这个对象上的一个线程
- `notifyAll`: 唤醒阻塞在这个对象上的所有线程

指的注意的是，用于同步的几个方法 `wait/notify/notifyAll` 只能在 `synchronized` 修饰的代码中调用

``` java
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
```

## 链接

- 测试代码: <https://github.com/hatlonely/hellojava/blob/master/src/test/java/util/ObjectTest.java>
