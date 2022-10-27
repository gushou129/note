# innerclass

定义在一个类中的一个类，与普通类一样，没有什么区别，除了被定义在一个类之中。

## 静态内部类

使用`static`修饰的内部类。

- 如果一个类内部需要使用一个完整的类，可以使用内部类，如：发动机类之于汽车、cpu之于电脑等；
- 使用方法与普通类一样，只有位置的区别；
- 内外部的静态资源都能互相访问，但是静态资源无法访问实例成员。

## 成员内部类（非静态内部类）

不用`static`修饰的内部类。

- 可以直接访问外部类的静态资源，内部类的实例方法可以使用外部类的实例成员，因为在因果上是先有外部类，再有内部类，先有外部类对象，再有内部类对象；
- 在内部类访问同名不同类的实例成员时使用：`外部类名.this.实例成员`进行操作。

## 局部内部类

可以出现在代码块、方法中的一种类。

## 匿名内部类

```java
Animer a = new Animer(){
    public void run(){
        // something
    }
}
```

- 方便创建子类对象，简化代码；
- 匿名类的类型就是匿名；
- 匿名对象的父类是所匿名的那个类。
### Lambda

- 只有函数式接口才可使用匿名内部类表达式；
- 通常的使用`@FunctionalInterface`作为函数式接口的注解。

简化后的代码：

```java
run(() -> {
    // do something
})
```

比如：
```java
Arrays.sort(ages, (Integer o1, Integer o2) -> {
    return o1 - o2;
});
```

```java
Arrays.sort(ages, (o1, o2) -> {
    return o1 - o2;
});
```

```java
Arrays.sort(ages, (o1, o2) -> o1 - o2);
```


```java
btn.addActionListener(e -> {
    System.out.println();
});
```

```java
btn.addActionListener(e -> System.out.println());
```


