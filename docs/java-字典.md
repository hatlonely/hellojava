# java 字典

## 数据结构总览

![数据结构](datastruct.png)

## Map

`Map` 描述的是一种映射关系，一个 key 对应一个 value，可以添加，删除，修改和获取 key/value

`Map` 的主要接口如下:

- `isEmpty`: 判断是否没有元素
- `size`: 获取元素个数
- `get`: 获取指定 key 的 value
- `getOrDefault`: 获取指定 key 的 value，如果没有 key，返回默认值
- `containsKey`: 判断字典是否包含 key
- `keySet`: key 的集合
- `values`: value 的集合
- `entrySet`: 包含 key/value 的集合，主要用于遍历
- `put`: 添加一个 key/value
- `putIfAbsent`: key 不存在才添加，如果 key 存在，返回 value，如果 key 不存在，返回 null
- `putAll`: 合并 map，不存在的 key 添加，已存在的 key 覆盖
- `remove(key)`: 删除，返回老 value
- `remove(key, val)`: 存在 `map[key] = val` 才删除，返回是否有元素删除
- `replace(key, newVal)`: 替换，返回老 value
- `replace(key, val, newVal`: 存在 `map[key] = val` 才替换，返回是否有元素替换
- `repalceAll`: 对所有的 key/value 执行 `BiFounction` 替换原来的 value
- `compute`: 所选的 key/oldValue 执行 `BiFounction` 替换原来的 value；如果 key 不存在，则 oldValue 为 null
- `computeIfPresent`: key 存在才执行 `BiFounction` 替换原来的 value
- `computeIfAbsent`: key 不存在才对 key 执行 `Founction` 作为 value 插入
- `merge`: 用 oldValue 和 newValue 执行 `BiFounction` 替换原来的 value；如果 key 不存在，则 oldValue 为 null

``` java
{
    Map<String, String> map = new HashMap<>(Map.of(
            "key0", "val0", "key1", "val1", "key2", "val2", "key3", "val3"
    ));
    assertEquals(map.size(), 4);
    assertFalse(map.isEmpty());
    assertTrue(map.containsKey("key3"));

    assertEquals(map.get("key3"), "val3");
    assertEquals(map.get("key6"), null);
    assertEquals(map.getOrDefault("key3", "defaultValue"), "val3");
    assertEquals(map.getOrDefault("key6", "defaultValue"), "defaultValue");

    assertThat(map.keySet(), equalTo(Set.of("key0", "key1", "key2", "key3")));
    assertThat(map.values(), hasItems("val0", "val1", "val2", "val3"));

    for (Map.Entry<String, String> entry : map.entrySet()) {
        System.out.println(entry.getKey() + " => " + entry.getValue());
    }
    map.forEach((k, v) -> System.out.println(k + " => " + v));

    map.clear();
    assertTrue(map.isEmpty());
}
{
    Map<String, String> map = new HashMap<>();
    map.put("key0", "val0");
    map.putAll(Map.of("key1", "val1", "key2", "val2"));
    assertEquals(map.putIfAbsent("key3", "val3"), null);
    assertEquals(map.putIfAbsent("key3", "val33"), "val3");
    assertThat(map, equalTo(Map.of(
            "key0", "val0", "key1", "val1", "key2", "val2", "key3", "val3"
    )));
}
{
    Map<String, String> map = new HashMap<>(Map.of(
            "key0", "val0", "key1", "val1", "key2", "val2", "key3", "val3"
    ));
    assertEquals(map.remove("errorKey"), null);
    assertEquals(map.remove("key0"), "val0");
    assertFalse(map.remove("key1", "errorValue"));
    assertTrue(map.remove("key1", "val1"));
    assertThat(map, equalTo(Map.of(
            "key2", "val2", "key3", "val3"
    )));
}
{
    Map<String, String> map = new HashMap<>(Map.of(
            "key0", "val0", "key1", "val1", "key2", "val2", "key3", "val3"
    ));
    assertEquals(map.replace("errorKey", "replaceValue"), null);
    assertEquals(map.replace("key0", "replaceValue"), "val0");
    assertFalse(map.replace("key1", "errorValue", "replaceValue"));
    assertTrue(map.replace("key1", "val1", "replaceValue"));
    assertThat(map, equalTo(Map.of(
            "key0", "replaceValue", "key1", "replaceValue", "key2", "val2", "key3", "val3"
    )));
}
{
    Map<String, String> map = new HashMap<>(Map.of(
            "key0", "val0", "key1", "val1", "key2", "val2", "key3", "val3"
    ));
    map.replaceAll((k, v) -> k + v);
    assertThat(map, equalTo(Map.of(
            "key0", "key0val0", "key1", "key1val1", "key2", "key2val2", "key3", "key3val3"
    )));
}
{
    Map<String, String> map = new HashMap<>(Map.of(
            "key0", "val0", "key1", "val1", "key2", "val2"
    ));
    assertEquals(map.compute("key0", (k, v) -> k + v), "key0val0");
    assertEquals(map.computeIfPresent("key1", (k, v) -> k + v), "key1val1");
    assertEquals(map.computeIfPresent("key6", (k, v) -> k + v), null);
    assertEquals(map.computeIfAbsent("key2", k -> k + k.replace("key", "val")), "val2");
    assertEquals(map.computeIfAbsent("key3", k -> k + k.replace("key", "val")), "key3val3");
    assertThat(map, equalTo(Map.of(
            "key0", "key0val0", "key1", "key1val1", "key2", "val2", "key3", "key3val3"
    )));
}
{
    Map<String, String> map = new HashMap<>(Map.of(
            "key0", "val0", "key1", "val1", "key2", "val2"
    ));
    assertEquals(map.merge("key0", "newVal", (oldValue, newValue) -> (oldValue + "->" + newValue)), "val0->newVal");
    assertEquals(map.merge("key3", "newVal", (oldValue, newValue) -> (oldValue + "->" + newValue)), "newVal");
    assertThat(map, equalTo(Map.of(
            "key0", "val0->newVal", "key1", "val1", "key2", "val2", "key3", "newVal"
    )));
}
```

## SortedMap
