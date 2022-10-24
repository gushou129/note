# enum

用作信息的标志与信息的分类。

```java
enum Season{
    SPRING, SUMMER, AUTUMN, WINTER;
}
```

## 特征

- 继承自`java.lang.Enum`；
- 枚举都是最终类，不可被继承；
- 枚举的构造器都是私有的，枚举不能对外创造对象；
- 枚举类第一行默认时罗列枚举对象的名称的；
- 枚举约等于多例模式。