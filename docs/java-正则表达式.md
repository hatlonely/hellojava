# java 正则表达式

正则表达式是一个非常强大的字符串处理工具，通过一种特殊的语法来描述一种模式，再通过模式可以完成字符串的匹配，萃取，替换等操作

## 简例

要判断一个字符串是否是一个邮箱，可能需要很多的判断逻辑，使用则表达式，只需要下面代码即可

``` java
Pattern pattern = Pattern.compile("^([a-z0-9._%+-]+)@([a-z0-9.-]+)\\.[a-z]{2,4}$");
Matcher matcher = pattern.matcher("hatlonely@foxmail.com");
assertTrue(matcher.matches());
```

主要有 `Pattern` 对象和 `Matcher` 对象

- `Pattern`: 模式对象
- `Matcher`: 匹配的结果

## 部分匹配与完全匹配

`Matcher` 提供 `matches` 方法用于完全匹配，`find` 方法用于部分匹配，还提供 `asPredicate` 方法返回一个部分匹配的谓词

`Pattern` 提供一个静态方法 `matches` 用于完全匹配，这个方法会临时构造 `Pattern` 对象，如果 `Pattern` 会被多次重复使用，尽量别直接使用这种方法

``` java
assertTrue(Pattern.compile("hello").matcher("hello").matches());         // 完全匹配
assertTrue(Pattern.compile("hello").matcher("hello world").find());      // 部分匹配
assertTrue(Pattern.compile("hello").asPredicate().test("hello world"));  // 部分匹配
assertTrue(Pattern.matches("hello", "hello"));                           // 完全匹配
```

## 通配符

|      符号     |                              含义                              |
|---------------|----------------------------------------------------------------|
| `c`           | 匹配单个字符 c                                                 |
| `.`           | 匹配所有单个字符，换行符 `\n` 除外                             |
| `^`           | 匹配字符串开始                                                 |
| `$`           | 匹配字符串结束                                                 |
| `\b`          | 匹配字符界，字符和空白之间                                     |
| `\B`          | 匹配非字符界，字符和空白之间                                   |
| `\|`          | 匹配前面表达式或者后面表达式                                   |
| `[charset]`   | 匹配任意括号内的字符，可用 `-` 表示范围，[a-z] 表示所有小写字母|
| `[^charset]`  | 匹配任意括号外的字符                                           |
| `\d`          | 匹配数字，相当于 [0-9]                                         |
| `\D`          | 匹配非数字，相当于 [^0-9]                                      |
| `\s`          | 匹配空白符，相当于 [ \f\n\r\t\v]                               |
| `\S`          | 匹配非空白符，相当于 [^ \f\n\r\t\v]                            |
| `\f`          | 匹配换页符                                                     |
| `\n`          | 匹配换行符                                                     |
| `\r`          | 匹配回车                                                       |
| `\t`          | 匹配字表符                                                     |
| `\v`          | 匹配垂直制表符                                                 |
| `\w`          | 匹配字符类字符，包括下划线，相当于 [A-Za-z0-9_]                |
| `\W`          | 匹配非字符类字符，[^A-Za-z0-9_]                                |
| `\u`          | 匹配四位十六进制数表示的 Unicode 字符，`\u00A9` 例如匹配 ©     |
| `\x`          | 匹配两位十六进制数表示的 ascii 码                              |

|     限定符    |                              含义                              |
|---------------|----------------------------------------------------------------|
| `*`           | 匹配前面字符或表达式 0 次或多次                                |
| `+`           | 匹配前面字符或表达式 1 次或多次                                |
| `?`           | 匹配前面字符或表达式 0 次或1次，跟着其他限定词后表示非贪婪匹配 |
| `{n}`         | 匹配前面字符或表达式 n 次                                      |
| `{n,}`        | 匹配前面字符或表达式至少 n 次                                  |
| `{n,m}`       | 匹配前面字符或表达式至少 n 次，至多 m 次                       |

``` java
assertTrue(Pattern.matches("[0-9]*", ""));
assertTrue(Pattern.matches("[0-9]?", ""));
assertTrue(Pattern.matches("[0-9]?", "1"));
assertTrue(Pattern.matches("[0-9]+", "123"));
assertTrue(Pattern.matches("[0-9]{3}", "123"));
assertTrue(Pattern.matches("[0-9]{3,4}", "123"));
assertTrue(Pattern.matches("[0-9]{3,4}", "1234"));
assertTrue(Pattern.matches("[0-9]{3,}", "1234"));
assertTrue(Pattern.matches("\\d+", "123"));
assertTrue(Pattern.matches("\\s+", " \t"));
assertTrue(Pattern.matches("\\w+", "abc"));
assertTrue(Pattern.matches("(f|z)oo", "foo"));
assertTrue(Pattern.matches("(f|z)oo", "zoo"));
assertTrue(Pattern.matches(".*", "any string"));
```

## 捕获分组

支持用小括号 `()` 将模式分组，`Matcher` 提供 `group` 方法获取分组的内容

|      符号     |                              含义                              |
|---------------|----------------------------------------------------------------|
| `(pattern)`   | 匹配分组并且**捕获**子表达式                                   |
| `(?:pattern)` | 匹配分组但是**不捕获**子表达式                                 |
| `(?=pattern)` | 正向预测，**非捕获**匹配                                       |
| `(?!pattern)` | 反向预测，**非捕获**匹配                                       |

``` java
Pattern pattern = Pattern.compile("^[a-z0-9]+@[a-z0-9.]+[.][a-z]{2,4}$");
Matcher matcher = pattern.matcher("hatlonely@foxmail.com");
assertTrue(matcher.matches());
assertEquals(matcher.groupCount(), 0);
```

使用 `groupCount` 获取捕获分组的数量，由于模式串中没有小括号，所以没有捕获的分组

``` java
Pattern pattern = Pattern.compile("^([a-z0-9]+)@(([a-z0-9.]+)[.]([a-z]{2,4}))$");
Matcher matcher = pattern.matcher("hatlonely@foxmail.com");
assertTrue(matcher.matches());
assertEquals(matcher.groupCount(), 4);
assertEquals(matcher.group(), "hatlonely@foxmail.com");
assertEquals(matcher.group(1), "hatlonely");
assertEquals(matcher.group(2), "foxmail.com");
assertEquals(matcher.group(3), "foxmail");
assertEquals(matcher.group(4), "com");
```

使用 `group` 方法获取分组的内容，分组的编号以左括号为准，`gruop(i)` 返回第 `i` 个分组，`group(0)` 代表整个匹配串

``` java
Pattern pattern = Pattern.compile("^([a-z0-9]+)@(?:([a-z0-9.]+)[.]([a-z]{2,4}))$");
Matcher matcher = pattern.matcher("hatlonely@foxmail.com");
assertTrue(matcher.matches());
assertEquals(matcher.groupCount(), 3);
assertEquals(matcher.group(), "hatlonely@foxmail.com");
assertEquals(matcher.group(0), "hatlonely@foxmail.com");
assertEquals(matcher.group(1), "hatlonely");
assertEquals(matcher.group(2), "foxmail");
assertEquals(matcher.group(3), "com");
```

可以用 `?:` 阻止捕获，如上面代码所示，`foxmail.com` 就没有被捕获

``` java
Pattern pattern = Pattern.compile("Windows (?=95|98|NT|2000)");
Matcher matcher = pattern.matcher("Windows 2000");
assertTrue(matcher.find());
assertEquals(matcher.group(), "Windows ");
assertEquals(matcher.groupCount(), 0);
```

`?=` 正向预测，匹配结束后的字符串需要匹配分组，分组内的模式仅用于预测，不会出现在最终的匹配串中，只能用于部分匹配

``` java
Pattern pattern = Pattern.compile("Windows (?!95|98|NT|2000)");
Matcher matcher = pattern.matcher("Windows vista");
assertTrue(matcher.find());
assertEquals(matcher.group(), "Windows ");
assertEquals(matcher.groupCount(), 0);
```

`?=` 反向预测，匹配结束后的字符串不能匹配分组，分组内的模式仅用于预测，不会出现在最终的匹配串中，只能用于部分匹配

## 反向引用

|      符号     |                              含义                              |
|---------------|----------------------------------------------------------------|
| `\1`          | 匹配捕获匹配到的反向引用，`\2` 表示第二个反向引用              |

``` java
assertTrue(Pattern.matches("(\\w+) \\1", "ab ab"));
assertTrue(Pattern.matches("(\\w+) \\1", "abc abc"));
assertFalse(Pattern.matches("(\\w+) \\1", "abc def"));
```

`(\w+)\1` 表示两个重复的单词
