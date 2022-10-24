---
categories: 
- 变成自己的话
tags: 
- java
---

# String注意事项

## String是不可变字符串

``` java
String str = "str1";
str += "+str2";
str += "+str3";
```

当你用String类new出一个对象并赋值时，该对象的数据就已经不能修改了，之后所有对该对象的修改操作都是在原数据的基础上重新创建一个新对象并将结果给其赋值的操作。


![](https://jam-note-img.oss-cn-hangzhou.aliyuncs.com/leanote-img/20221014092209.png)

## String的常用构造器
``` java 
// 创建一个空白字符串，不含有任何内容
// public String();
String s1 = new String();
System.out.println(s1);
// 根据传入的字符串内容，来创建字符串对象
// public String(String);
String s2 = new String("s2");
System.out.println(s2);
// 根据字符数组的内容，来创建字符串对象
// public String(char[] c);
char[] chars = {'c', 'h', 'a', 'r', 's'};
String s3 = new String(chars);
System.out.println(s3);
// 根据字节数组的内容，以数组中对应的ASCII来创建字符串对象
// public String(byte[] b);
byte[] bytes = {65, 66, 67, 68};
String s4 = new String(bytes);
System.out.println(s4);
/*$Out:

s2
chars
ABCD
*/
```

## 使用""创建的对象与使用new创建的对象有区别吗
有区别：

1.  用""创建的相同内容的String对象只会存在一个
**因为""创造的对象在堆内存的字符串常量池中**
``` java
String s1 = new String("s1");
String s2 = "s1";
String s3 = s1;        
System.out.println(System.identityHashCode(s1));
System.out.println(System.identityHashCode(s2));
System.out.println(System.identityHashCode(s3));
///*$Out:
312714112
312714112
312714112
*/
```
![](https://jam-note-img.oss-cn-hangzhou.aliyuncs.com/leanote-img/20221014100824.png)

2.用new创建的相同内容的String对象只会存在一个
**用new创造的对象放在堆内存中**
``` java
String s1 = new String("s2");
String s2 = new String("s2");
String s3 = s1;        System.out.println(System.identityHashCode(s1));    System.out.println(System.identityHashCode(s2));  System.out.println(System.identityHashCode(s3));
///*$Out:
312714112
692404036
312714112
*/
```
![](https://jam-note-img.oss-cn-hangzhou.aliyuncs.com/leanote-img/20221014100940.png)

## "=="与equals()的区别

“==”用来判断基础类型或对象的地址是否相同，equals()用来判断字符串内容的是否相同

## String API
```java
// public char  charAt(int index): get index char
// 遍历字符串
for (int i = 0; i < name.length(); i++) {
    char c = name.charAt(i);
    System.out.println(c);
}

public char[] toCharArray(); // use String to get char[] 

public String substring(int beginIndex, int endIndex); //截取
public String substring(int beginIndex):

public String replace(CharSequence target, CahrSequence replacement); //替换

public boolean contains(CharSequence s); // 查询s是否存在

public boolean startsWiths(String prefix); //判断起始字符是否为prefix

public String[] split(String s); //用s把字符串分割
```

## 面试题
![](https://jam-note-img.oss-cn-hangzhou.aliyuncs.com/leanote-img/20221014101249.png)

![](https://jam-note-img.oss-cn-hangzhou.aliyuncs.com/leanote-img/20221014102339.png)
```java
String s1 = "a";
String s2 = "b";
String s3 = "c";
String s6 = "abc";
String s4 = s1 + s2 + s3;
String s5 = "a" + "b" + "c";

System.out.println("s1 + s2 + s3 \t == \"abc\"\t\t\t:" + (s4 == s6));
System.out.println("\"a\" + \"b\" + \"c\"  == \"abc\"\t\t\t:" + (s5 == s6));
System.out.println("s1 + s2 + s3 \t == \"a\" + \"b\" + \"c\" :" + (s4 == s5));
///*$Out:
s1 + s2 + s3 	 == "abc"			:false
"a" + "b" + "c"  == "abc"			:true
s1 + s2 + s3 	 == "a" + "b" + "c" :false
*/
```



