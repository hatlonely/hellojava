# java stream

stream 是 java 8 的一大亮点，专注于了集合的处理，包括抽取，过滤，转化，聚合，化简等，借助新的 lambda 表达式，极大地简化了流式处理的流程，提高了代码的可读性，同时支持并发和串行两种模式，可以很容易地写出高性能的代码

## 基本概念

stream 相当于一个高级版本的 iterator，iterator 只有简单的遍历功能，而 stream 在这个基础上提供了抽取，过滤，转化，聚合，化简等丰富的流处理功能

常见的操作主要中间操作和终止操作，一个流处理可以会经过若干个中间操作和一个终止操作，中间操作返回的依然是一个流，终止操作获得一个特定的结果，中间操作是惰性的，在遇到终止操作之前，中间操作只是记录一个操作步骤，不会从流中读取任何数据

## 创建流

``` java
 // 从容器中创建
Stream<Integer> stream1 = List.of(1, 2, 3, 4, 5).stream();
Stream<Integer> stream2 = Stream.of(1, 2, 3, 4, 5);

// 创建随机数
Stream<Integer> stream3 = new Random().ints().limit(10).boxed();
Stream<Integer> stream4 = ThreadLocalRandom.current().ints().limit(10).boxed();

// 文件流
Stream<String> stream5 = new BufferedReader(new FileReader("/tmp/test.txt")).lines();

// IntStream
Stream<Integer> stream6 = IntStream.range(1, 10).boxed();

// generate
Stream<Integer> stream7 = Stream.generate(() -> (int) System.currentTimeMillis()).limit(10);

// iterate
Stream<Integer> stream8 = Stream.iterate(1, x -> x + 1).limit(10);
Stream<Integer> stream9 = Stream.iterate(1, x -> x < 10, x -> x + 1);
```

## 终止操作

终止操作代表一个计算的结果，常见的终止操作如下:

- `forEach`: 遍历元素
- `forEachOrdered`: 按顺序遍历元素，主要在并发场景下使用
- `toArray`: 将流输出到数组中
- `collect`: 收集流，可以输出到指定的 `Collector` 中，`Collectors` 中自带了 `toList`，`toSet` 等 `Collector`
- `anyMatch`: 任意元素满足谓词，返回 true
- `allMatch`: 所有元素满足谓词，返回 true
- `noneMatch`: 所有元素都不满足谓词，返回 true
- `count`: 元素数量
- `findFirst`: 返回第一个元素
- `findAny`: 返回任意一个元素
- `min`: 返回最小元素
- `max`: 返回最大元素
- `reduce`: 聚合，将流中所有的数据聚合成一个值

``` java
Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).forEach(System.out::print);
Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).forEachOrdered(System.out::print);
assertThat(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).toArray(), equalTo(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9}));
assertThat(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).collect(Collectors.toList()), equalTo(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9)));
assertFalse(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).anyMatch(x -> x > 10));
assertTrue(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).allMatch(x -> x < 10));
assertTrue(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).noneMatch(x -> x > 10));
assertEquals(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).count(), 9);
assertEquals(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).findFirst().orElse(0), Integer.valueOf(1));
assertEquals(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).findAny().orElse(0), Integer.valueOf(1));
assertEquals(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).iterator().next(), Integer.valueOf(1));
assertEquals(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).max(Comparator.comparingInt(x -> x)).orElse(0), Integer.valueOf(9));
assertEquals(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).min(Comparator.comparingInt(x -> x)).orElse(0), Integer.valueOf(1));
assertEquals(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).reduce((x, y) -> x + y).orElse(0), Integer.valueOf(45));
```

## 中间操作

- `map`: 转化，将流中元素按照 Founction 映射成另一种元素
- `filter`: 过滤，将不符合条件的元素从流中删除
- `distinct`: 去重，去掉重复元素
- `sorted`: 排序
- `limit`: 最多取元素的个数
- `skip`: 跳过前 n 个元素
- `peek`: 和 forEach 操作类似，但依然会返回当前流
- `takeWhile`: 获取元素直到不满足条件
- `dropWhile`: 丢弃元素直到满足条件

``` java
assertThat(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).map(x -> x * x).collect(Collectors.toList()), equalTo(List.of(
        1, 4, 9, 16, 25, 36, 49, 64, 81
)));
assertThat(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).filter(x -> x % 2 == 0).collect(Collectors.toList()), equalTo(List.of(
        2, 4, 6, 8
)));
assertThat(Stream.of(4, 1, 2, 1, 2, 4, 3, 3).distinct().collect(Collectors.toList()), equalTo(List.of(
        4, 1, 2, 3
)));
assertThat(Stream.of(6, 4, 7, 3, 2, 9, 1, 5, 8).sorted().collect(Collectors.toList()), equalTo(List.of(
        1, 2, 3, 4, 5, 6, 7, 8, 9
)));
assertThat(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).limit(3).collect(Collectors.toList()), equalTo(List.of(
        1, 2, 3
)));
assertThat(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).skip(6).collect(Collectors.toList()), equalTo(List.of(
        7, 8, 9
)));
assertThat(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).peek(System.out::println).collect(Collectors.toList()), equalTo(List.of(
        1, 2, 3, 4, 5, 6, 7, 8, 9
)));
assertEquals(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).mapToInt(x -> x * x).sum(), 285);
assertThat(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).takeWhile(x -> x < 5).collect(Collectors.toList()), equalTo(List.of(
        1, 2, 3, 4
)));
assertThat(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).dropWhile(x -> x < 5).collect(Collectors.toList()), equalTo(List.of(
        5, 6, 7, 8, 9
)));
```

## 链接

- 测试代码: <https://github.com/hatlonely/hellojava/blob/master/src/test/java/util/StreamTest.java>
