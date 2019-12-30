# java 集合

## 集合接口

Collection 接口主要关注集合的添加，删除，包含

- `isEmpty`: 判断是否没有元素
- `size`: 获取元素个数
- `add`: 添加元素
- `addAll`: 添加给定集合中的所有元素，相当于并集
- `remove`: 删除元素
- `removeAll`: 删除给定集合中的所有元素，相当于差集
- `removeIf`: 删除满足谓词的元素
- `retainAll`: 保留给定集合中的元素，相当于交集
- `contains`: 判断某个元素是否在集合内
- `containsAll`: 判断给定集合中的所有元素是否都在集合内
- `clear`: 清空所有元素
- `stream`: 支持流处理

``` java
{
    Collection<Integer> c = new ArrayList<>(List.of(1, 2, 3, 4, 5));
    assertEquals(c.size(), 5);
    assertFalse(c.isEmpty());
    assertTrue(c.contains(3));
    assertTrue(c.containsAll(List.of(2, 4)));
    c.clear();
    assertEquals(c.size(), 0);
    assertTrue(c.isEmpty());
}
{
    Collection<Integer> c = new ArrayList<>(List.of(1, 2, 3, 4, 5));
    c.add(6);
    assertThat(c, equalTo(List.of(1, 2, 3, 4, 5, 6)));
    c.addAll(List.of(7, 8, 9));
    assertThat(c, equalTo(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9)));
}
{
    Collection<Integer> c = new ArrayList<>(List.of(1, 2, 3, 4, 5));
    c.remove(3);
    assertThat(c, equalTo(List.of(1, 2, 4, 5)));
    c.removeAll(List.of(2, 3));
    assertThat(c, equalTo(List.of(1, 4, 5)));
    c.retainAll(List.of(1, 2, 3, 4));
    assertThat(c, equalTo(List.of(1, 4)));
    c.removeIf(x -> x % 2 == 0);
    assertThat(c, equalTo(List.of(1)));
}
{
    Collection<Integer> c = new ArrayList<>(List.of(1, 2, 3, 4, 5));
    c.forEach(System.out::print);
    assertEquals(c.stream().map(x -> x * x).mapToInt(x -> x).sum(), 55);

    for (Integer i : c) {
        System.out.print(i);
    }
}
```

## List

List 接口为顺序表，关注集合的定位，查找，修改和排序，底层有两种实现，链表和数组，链表有较好的头部插入性能，数组在随机访问的时候有很大优势，util 里主要提供了三种顺序表:

- LinkedList: 双链表实现，定位元素需要遍历，get 性能是 O(n)；插入性能 O(1)，但指定下标插入需要先定位；查找也需要遍历，性能 O(n)
- ArrayList: 数组实现，插入时需要移动数组中的元素，插入性能是 O(n)，向后插入是 O(1)，插入时如果数组空间不够，需要重新申请新的空间，并将原来的元素添加到新的数组中；可以根据下标定位元素，支持随机访问，get 性能是 O(1)；查找需要遍历，性能 O(n)
- Vector: 和 ArrayList 底层一样，但是是线程安全的

List 在 Collection 的基础上，提供了下面接口

- `get`: 按下标定位元素
- `indexOf`: 查找元素，返回下标
- `lastIndexOf`: 从后向前查找元素
- `subList`: 子链表
- `set`: 指定下标修改
- `sort`: 排序
- `replaceAll`: 对所有元素用 UnaryOperator 的返回值替换

``` java
{
    List<Integer> l = new ArrayList<>(List.of(1, 2, 3, 4, 5, 4, 3, 2, 1));
    assertEquals(l.get(2), Integer.valueOf(3));
    assertEquals(l.indexOf(3), 2);
    assertEquals(l.indexOf(6), -1);
    assertEquals(l.lastIndexOf(3), 6);
    assertEquals(l.subList(2, 6), List.of(3, 4, 5, 4));
}
{
    List<Integer> l = new ArrayList<>(List.of(1, 2, 3, 4, 5, 4, 3, 2, 1));
    l.set(5, 6);
    assertThat(l, equalTo(List.of(1, 2, 3, 4, 5, 6, 3, 2, 1)));
}
{
    List<Integer> l = new ArrayList<>(List.of(1, 2, 3, 4, 5, 4, 3, 2, 1));
    l.sort(Integer::compareTo);
    assertThat(l, equalTo(List.of(1, 1, 2, 2, 3, 3, 4, 4, 5)));
}
{
    List<Integer> l = new ArrayList<>(List.of(1, 2, 3, 4, 5, 4, 3, 2, 1));
    l.replaceAll(x -> x * x);
    assertThat(l, equalTo(List.of(1, 4, 9, 16, 25, 16, 9, 4, 1)));
}
```

## Set

## Stack

## Queue

## Deque

## 链接

- Collection 测试代码: <https://github.com/hatlonely/hellojava/blob/master/src/test/java/util/CollectionTest.java>
- List 测试代码: <https://github.com/hatlonely/hellojava/blob/master/src/test/java/util/ListTest.java>
