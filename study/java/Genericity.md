# Genericity

用于约束输入数据的类型。
也用于接受一切数据。

## 泛型类
```java
class MyArrayList<E> {
    public void add(E e) { }

    public void remove(E e) { }
}
```

## 泛型方法

```java
public <T> void show(T t) { }
```

## 泛型接口

```java
public interface Data <T>{ }
```

## 泛型通配符

- `?`可以在“使用泛型”的时候代表一切类型；
- `? extends class`: 必须是class或者是其子类（上限）；
- `? super class`: 必须是class或者是其父类（下限）。

```java
public static void go(ArrayList<? extends Car> cars){ }
```

