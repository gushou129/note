# interface

接口即规范，规范即公开。

## 被实现

接口是被类**实现**(**implements**)的，实现接口的类是实现类。接口也可以是多对一的，一个实现类可以同时实现多个接口，如：
```java
public class 实现类 implement 接口1，接口2，...{}
```

### 为什么一个类可以对应多个接口

1. 首先想一下为什么类与类是单继承关系：
首先无论父类是不是抽象类，父类中都可能含有实例方法，假设类与类是多继承关系时，当两个父类均含有同名的实例方法时，子类的重写或调用将会发生冲突，为了java语言严谨性，将类与类之间的继承关系设置为单继承关系。

2. 为什么接口与接口，接口与类之间是多继承关系：
首先是因为接口中只有常量和抽象方法，当两个同名的抽象方法被实现时不会冲突，是因为抽象方法中没有方法块，不存在一个接口的方法覆盖另一个同名的方法。而接口中的常量有属于该接口自己，使用接口名加常量名即可使用，也不会存在冲突问题。接口与接口的多继承关系也同理。

## 注意事项

### 接口不能创建对象

因为抽象类不能创建对象，所以比抽象类更抽象的接口也不能创建对象。

### 多个接口的同名静态方法不冲突

因为静态方法一般是使用类名直接调用，而接口的静态方法也一般使用接口名直接调用，所以不会起冲突。

### 父类与接口类中有同名方法时，父类优先

设计时父类更多为实体类，父类方法更多为实例方法，而接口中一般为抽象方法，所以一般直接调用父类同名方法。

### 存在同名不同接口的默认方法时，实现类必需重写该方法

为了不冲突，必须重写。

### 同名不同接口规范冲突时不能多继承

