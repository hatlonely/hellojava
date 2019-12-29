# java math 库

`java.lang.Math` 库提供了常用的数学计算工具

## 常量

``` java
final double E = 2.7182818284590452354;                 // 自然对数底数
final double PI = 3.14159265358979323846;               // 圆周率
final double DEGREES_TO_RADIANS = 0.017453292519943295; // 角度转弧度
final double RADIANS_TO_DEGREES = 57.29577951308232;    // 弧度转角度
```

## 取整

- `abs(x)`: 绝对值
- `floor(x)`: 向下取整
- `ceil(x)`: 向上取整
- `round(x)`: 四舍五入，如果有两个(`x.5`)，返回较大的那个数
- `rint(x)`: 最接近的整数，如果有两个(`x.5`)，返回偶数
- `floorDiv(x, y)`: 向下取整除法
- `floorMod(x, y)`: java 默认的取摸 `%` 得到的结果和 `x` 的符号相同，`floorMod` 和 `y` 的符号相同

``` java
double delta = 0.0000001;
assertEquals(Math.abs(-6), 6);
assertEquals(Math.floor(-6.2), -7, delta);  // 向下取整
assertEquals(Math.floor(6.2), 6, delta);
assertEquals(Math.floor(6.8), 6, delta);
assertEquals(Math.ceil(-6.2), -6, delta);   // 向上取整
assertEquals(Math.ceil(6.2), 7, delta);
assertEquals(Math.ceil(6.8), 7, delta);
assertEquals(Math.round(-6.2), -6, delta);  // 四舍五入
assertEquals(Math.round(6.2), 6, delta);
assertEquals(Math.round(6.8), 7, delta);
assertEquals(Math.round(-6.5), -6, delta);
assertEquals(Math.round(6.5), 7, delta);
assertEquals(Math.rint(-6.2), -6, delta);  // 最接近整数，如果存在两个，返回偶数
assertEquals(Math.rint(6.2), 6, delta);
assertEquals(Math.rint(6.8), 7, delta);
assertEquals(Math.rint(-6.5), -6, delta);
assertEquals(Math.rint(6.5), 6, delta);

assertEquals(Math.floorDiv(7, 3), 2);
assertEquals(Math.floorDiv(-7, 3), -3);
assertEquals(Math.floorMod(7, 3), 1);
 assertEquals(Math.floorMod(-7, -3), -1);
assertEquals(Math.floorMod(-7, 3), 2);
assertEquals(-7 % -3, -1);
assertEquals(-7 % 3, -1);
```

## 三角函数

``` java
assertEquals(Math.sin(Math.PI / 2), 1.0, delta);
assertEquals(Math.cos(Math.PI), -1, delta);
assertEquals(Math.tan(Math.PI / 4), 1.0, delta);
assertEquals(Math.asin(1), Math.PI / 2, delta);
assertEquals(Math.acos(-1), Math.PI, delta);
assertEquals(Math.atan(1), Math.PI / 4, delta);
```

## 指数对数

- `pow(x, y)`: `x^y`，`x` 的 `y` 次方
- `sqrt(x)`: `√x`，`x` 的平方根
- `cbrt(x)`: 三次方根
- `hypot(x, y)`: `√(x² + y²)`
- `exp(x)`: `e ^ x`
- `expm1(x)`: `e ^ x - 1`
- `log(x)`: `ln(x)`
- `log10`: `lg(x)`
- `log1p(x)`: `ln(1+x)`

``` java
assertEquals(Math.pow(3, 2), 9, delta);
assertEquals(Math.pow(2, 3), 8, delta);
assertEquals(Math.sqrt(4), 2, delta);
assertEquals(Math.cbrt(27), 3, delta);
assertEquals(Math.hypot(3, 4), 5, delta);   // √(x² + y²)

assertEquals(Math.exp(2.5), Math.pow(Math.E, 2.5), delta);  // e ^ x
assertEquals(Math.expm1(2), Math.exp(2) - 1, delta);    // e ^ x - 1
assertEquals(Math.log(Math.exp(1.5)), 1.5, delta);  // ln(x)
assertEquals(Math.log10(1000), 3, delta);           // lg(x)
assertEquals(Math.log1p(Math.E - 1), 1, delta);     // ln(1 + x)
```

## 双曲函数

- `sinh(x)`: `(e ^ x - e ^ -x) / 2`
- `cosh(x)`: `(e ^ x + e ^ -x) / 2`
- `tanh(x)`: `sinh(x) / cosh(x)`

``` java
assertEquals(Math.sinh(2), (Math.exp(2) - Math.exp(-2)) / 2, delta);    // sinh(x) = (e ^ x - e ^ -x) / 2
assertEquals(Math.cosh(2), (Math.exp(2) + Math.exp(-2)) / 2, delta);    // cosh(x) = (e ^ x + e ^ -x) / 2
assertEquals(Math.tanh(2), Math.sinh(2) / Math.cosh(2), delta);         // tanh(x) = sinh(x) / cosh(x)
```

## 精确计算

普通的数值计算在溢出时是没有感知的，比如 `Long.MAX_VALUE + 1` 将得到结果 `Long.MIN_VALUE`，为了解决这种不合理，`Math` 提供了一些辅助函数，在结果溢出时将抛出异常

- `addExact(x, y)`: 加法 
- `multiplyExact(x, y)`: 乘法 
- `decrementExact(x, y)`: 递减 
- `incrementExact(x, y)`: 递增 
- `negateExact(x, y)`: 相反数
- `multiplyFull(x, y)`: 接受两个 `int` 返回一个 `long`，防止溢出
- `multiplyHigh(x, y)`: 返回两个 `long` 乘积的高 `64` 位

``` java
assertEquals(Long.MAX_VALUE + 1, Long.MIN_VALUE);                                       // 溢出
assertThrows(ArithmeticException.class, () -> Math.addExact(Long.MAX_VALUE, 1));        // 加法溢出抛异常
assertThrows(ArithmeticException.class, () -> Math.multiplyExact(Long.MAX_VALUE, 2));   // 乘法
assertThrows(ArithmeticException.class, () -> Math.decrementExact(Long.MIN_VALUE));     // 递减
assertThrows(ArithmeticException.class, () -> Math.incrementExact(Long.MAX_VALUE));     // 递增
assertThrows(ArithmeticException.class, () -> Math.negateExact(Long.MIN_VALUE));        // 相反数
assertEquals(Math.addExact(1, 2), 3);
assertEquals(Math.multiplyExact(2, 3), 6);
assertEquals(Math.incrementExact(6), 7);
assertEquals(Math.decrementExact(6), 5);
assertEquals(Math.negateExact(-6), 6);

assertEquals(Math.multiplyFull(1, 2), 2);   // 接受两个 int 返回一个 long，防止溢出
assertEquals(Math.multiplyHigh(1, 2), 0);   // 返回两个 long 乘积的高 64 位
```

## 浮点数

任意两个浮点数之间都有无数个浮点数，因此大部分浮点数是无法表示的，只能选取一个最接近的，java 提供了一些接口来获取能表示的浮点数

``` java
System.out.println(Math.nextUp(1.1));   // 下一个浮点数
System.out.println(Math.nextDown(1.1)); // 上一个浮点数
System.out.println(Math.nextAfter(1.1, Double.POSITIVE_INFINITY));    // 下一个浮点数
System.out.println(Math.nextAfter(1.1, Double.NEGATIVE_INFINITY));    // 上一个浮点数
```

## 随机数

``` java
System.out.println(Math.random());          // 0 ~ 1 之间的随机数
```

## 链接

- 测试代码: <https://github.com/hatlonely/hellojava/blob/master/src/test/java/util/MathTest.java>
