# java 基本类型

java 基本的基本类型包括 boolean, byte, char, short, int, long, float, double

每个类型都有一个封装类，封装类继承自 `Object` 对象，可以用作存放在容器中，另外还提供了一些辅助函数

| 基本类型 | 封装类    | 内存空间 | 取值范围                                    | 描述       |
| boolean  | Boolean   | 1        | [0, 1]                                      | 布尔类型   |
| byte     | Byte      | 8        | [-128, 127]                                 | 字节类型   |
| short    | Short     | 16       | [-32768, 32767]                             | 短整型     |
| int      | Integer   | 32       | [-2147483648, 2147483647]                   | 整型       |
| long     | Long      | 64       | [-9223372036854775808, 9223372036854775807] | 长整型     |
| float    | Float     | 32       | [1.4E-45, 3.4028235E38]                     | 单精度浮点 |
| double   | Double    | 64       | [4.9E-324, 1.7976931348623157E308]          | 双精度浮点 |
| char     | Character | 16       | -                                           | 字符类型   |
| void     | Void      | -        | -                                           | 空类型     |

普通类型能隐式转换成对应的封装类型

``` java
Integer i = 10;
Double d = 10.0;
```

封装类还定义了类型的表示范围

``` java
System.out.println("Byte: [" + Byte.MIN_VALUE + ", " + Byte.MAX_VALUE + "]");
System.out.println("Short: [" + Short.MIN_VALUE + ", " + Short.MAX_VALUE + "]");
System.out.println("Integer: [" + Integer.MIN_VALUE + ", " + Integer.MAX_VALUE + "]");
System.out.println("Long: [" + Long.MIN_VALUE + ", " + Long.MAX_VALUE + "]");
System.out.println("Float: [" + Float.MIN_VALUE + ", " + Float.MAX_VALUE + "]");
System.out.println("Double: [" + Double.MIN_VALUE + ", " + Double.MAX_VALUE + "]");
```

封装类提供了与字符串相互转化的功能

``` java
assertEquals(Integer.parseInt("10"), 10);
assertEquals(Integer.valueOf(10).toString(), "10");
assertEquals(Double.parseDouble("123.456"), 123.456, 0.00001);
assertEquals(Double.valueOf(123.456).toString(), "123.456");
```

## 链接

- 测试代码: <https://github.com/hatlonely/hellojava/blob/master/src/test/java/util/NumberTest.java>
