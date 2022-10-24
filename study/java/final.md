# final

## 有什么用

- 修饰类，表示该类为最终类，无法被继承；
- 修饰方法，表示该方法为最终方法，无法被重写；
- 修饰变量，表示该变量第一次赋值后，无法再被赋值。

### 修饰变量

#### 基本类型

变量存储的**数据值**不能改变。

#### 引用类型

变量存储的**地址值**不能改变，但是地址值指向的数据可以改变。

## 常量

### 是什么

用`public static final`做修饰的成员变量，必须有初始值。

### 有什么用

用作系统配置信息，方便程序的维护，同时提高可读性。

### 原理

在使用常量的地方会在编译时自动替换为真实的数据，提高了程序运行速度。
```java
public class Demo1 {
    public static final String MY_NAME = "Jam";
    public static void main(String[] args) {
        String s1 = MY_NAME;
        String s2 = MY_NAME;
        System.out.println(s1 == s2);
    }
}
/*$Out:
true
*/
```

