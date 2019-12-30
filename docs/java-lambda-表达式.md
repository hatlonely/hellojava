# java lambda 表达式

lambda 是 java 8 引入的新特性，lambda 能替换简单的函数和类，简化了代码，提高了代码可读性

## 语法

```
(Type1 param1, Type2 param2, ...) -> {
    statement1;
    statement2;
    ...
    return statementX;
}
```

1. 如果参数类型可以自动推导，可以省略
2. 如果参数只有一个，小括号可以省略
3. 如果主体只有一条语句，大括号可以省略
4. 如果主体只有一条语句，return 语句可以省略


## 函数式接口

java 里面没有函数对象，所以提供了很多只有一个方法的接口，这类接口被称为函数式接口，这些接口可以用 lambda 表达式来构造

``` java
Predicate<Integer> greater10 = x -> x > 10;
Consumer<Integer> print = x -> System.out.println(x);
Function<Integer, String> intToString = x -> Integer.valueOf(x).toString();
Supplier<Integer> randInt = () -> ThreadLocalRandom.current().nextInt();
Comparator<Integer> less = (x, y) -> y - x;
UnaryOperator<Integer> power = x -> x * x;
BinaryOperator<Integer> add = (x, y) -> x + y;
BiConsumer<Integer, String> printis = (i, s) -> System.out.println(i + s);
Runnable run = () -> System.out.println("hello world");
```

## 方法引用与构造器引用

lambda 表达式可以直接引用类或者对象的方法，语法: `<object|class>::method`

也可以用 `<class>::new` 引用无参构造函数，带参构造函数可以用上面的 lambda 语法构造

``` java
Consumer<Integer> print = System.out::println;
Predicate<String> empty = String::isEmpty;
Supplier<Date> date = Date::new;
```
