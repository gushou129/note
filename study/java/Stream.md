---
categories: 
- 变成自己的话
tags: 
- java
---

# Stream

- 简化集合与数据操作的API
- 结合Lambda表达式

在传送带上处理数据。

1. 创建传送带，放置数据；
2. 处理数据；
3. 关闭传送带。

## 创建传送带

### Collection

```java
Collection<String> list = new ArrayList<>();
Stream<String> s = list.stream();
```

### Map

```java
Map<String, Integer> maps = new Map<>();

// key stream, 键流
Stream<String> keyStream = maps.keySet().stream();
// value stream, 值流
Stream<Integer> valueStream = maps.values().stream();

// entryset stream, 键值对流
Stream<Map.Entry<String, Integer>> s = m.entrySet().stream();
```

### Array

```java
String[] names = {};
// 使用Arrays.Stream
Stream<String> nameStream = Arrays.stream(names);
// 使用Stream.of
Stream<String> nameStream2 = Stream.of(names);
```

## 常用API

### 非终结方法

```java
// 按照要求过滤数据
filter();
// 取前几个
limit();
// 跳过前几个
skip();
// 加工方法，第一个String表示原料类型
// 第二个String表示加工后的类型
map(new Function<String, String>){});
// 合并流方法 建议合并的两个流数据都使用同种类型
Stram<T> concat(Stram a, Stram b);
```

### 终结方法

```java
void forEach();
long count()
```
