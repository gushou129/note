# Date

获取时间信息以及处理时间

## System.currentTimeMillis() & new Date().getTime()

两者都获取当前时间信息，性能方便有什么优劣？

Date类源码：

```java
public Date() {
    this(System.currentTimeMillis());
}
```

在**仅获取时间信息**时，
可见使用Date类中的getTime方法逼格更高，但效率不如直接使用`System.currentTimeMillis()`更高。

但是如果**需要处理时间信息**时，
还是建议使用Date类中的方法，因为其中自带更多处理时间信息的工具。

### 例子

需求：
输出当前时间加上1小时121秒后的时间。

```java
Date d1 = new Date();

// + 1:02:01

d1.setTime(System.currentTimeMillis()
     + (60 * 60 + 121) * 1000);
System.out.println(d1);
/*$Out: 
Wed Oct 26 12:06:10 CST 2022
*/
```

# SimpleDateFormat

将Date**对象**或**时间毫秒值**<span style="color: red;">格式化</span>为需要的形式。

## 构造器

```java
// 默认格式
public SimpleDateFormat()
// 自定义格式
public SimpleDateFormat(String pattern)
/*
常用格式符号
y:年
M:月
d:日
H:时
m:分
s:秒
E:星期
a:上午下午
*/

```

## 常用方法

```java
// 将Date对象格式化为指定格式
public final String format(Date date)
// 将时间毫秒值格式化为指定格式
public final String format(Object tim)
// 将字符串按照指定格式格式化为Date数据
public Date parse(String source)
```

### parse()

2021年8月6日11:11:11的两天14小时49分6秒后是什么时间？

```java
Date d = new Date();
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
// 使用SimpleDateFormat 中的parse方法解析输入的字符串
Date d2 = sdf.parse("2021-08-06 11-11-11");

// +time
d.setTime(d2.getTime()
        + (2L * 24 * 60 * 60 + 14 * 60 * 60 + 49 * 60 + 6) * 1000);
System.out.println(sdf.format(d));

```

## 案例

秒杀开始时间：2022年11月11日 00:00:00
秒杀结束时间：2022年11月11日 00:10:00 

A下单并付款时间为2022年11月11日 00:03:47
B下单并付款时间为2022年11月11日 00:10:11

用代码说明谁成功参加了该活动。

```java
// set pattern
SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
// set start time
Date startTime = sdf.parse("2022年11月11日 00:00:00");
// set end time
Date endTime = sdf.parse("2022年11月11日 00:10:00");

Date a = sdf.parse("2022年11月11日 00:03:47");
Date b = sdf.parse("2022年11月11日 00:10:11");

isIn(a, startTime, endTime);
isIn(b, startTime, endTime);
```

isIn()
```java
public static void isIn(Date date, Date start, Date end) {
    // 使用Date 中的before & after 来判断大小
    if (date.before(end) && date.after(start)) {
        System.out.println("Yes!");
    } else {
        System.out.println("No!");
    }
}
```

# Calendar

```java
// 获取日期中某个字段
public int get(int field)
// 修改日历中某个字段
public void dset(int field, int value)
// 给某个字段添加或减少value
public void add(int field, int amount)
// 获取当前时间对象
public final Date getTime()
// 获取当前时间毫秒值
public long getTimeInMillis()
```





