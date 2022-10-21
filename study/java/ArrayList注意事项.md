# ArrayList注意事项

## 集合容器中存储的对象是什么

集合中每个对象存储的是该数据在**堆内存**中的地址

## 泛型

**作用**：使用泛型来统一ArrayList集合中的所有元素

`ArrayList<E>`:通过更改E(引用类型)来更改`ArrayList`的存储类型。

如：

 - `ArrayList<String>` :只能操作`String`类型的元素。
 - `ArrayList<Integer>`:只能操作`Integer`类型的元素。

## 常用API

```java
// 1. get data from the index
public E get(int inex);
// 2. get size from the ArrayList
public int size();

// 3. traverse it 
for (int i = 0; i < arrayList.size(); i++) {          System.out.println(arrayList.get(i));
}

// 4. remove data from the index then return this item
public E remove(int index);
// 5. remove data from the index then return true or false
public boolean remove(object o);
// 6. set data from the index
public E set(int index, E element);
```
使用案例：
```java
ArrayList<String> arrayList = new ArrayList<>();

// add
arrayList.add("1");
arrayList.add("2");
arrayList.add("3");
arrayList.add("4");
arrayList.add("5");
System.out.println(arrayList);
System.out.println("--------");
// delete
System.out.println(arrayList.remove(2));
System.out.println(arrayList);
System.out.println("--------");
// search
System.out.println(arrayList.get(1));
System.out.println(arrayList);
System.out.println("--------");
// change
System.out.println(arrayList.set(1, "100"));
System.out.println(arrayList);
///*$Out:
[1, 2, 3, 4, 5]
--------
3
[1, 2, 4, 5]
--------
2
[1, 2, 4, 5]
--------
2
[1, 100, 4, 5]
--------
*/
```

## 删除元素

第一次写删除元素：

``` java
Random r = new Random();

for (int i = 0; i < 10; i++) {
    scores.add(r.nextInt(100));
}
System.out.println(scores);

for (int i = 0; i < scores.size(); i++) {
    if(scores.get(i) <= 60){
        System.out.println(scores.remove(i));
    }
}
///*$Out:
[25, 27, 21, 84, 82, 10, 1, 35, 58, 25]
25
21
10
35
25
[27, 84, 82, 1, 58]
*/
```

怎么回事呢，为什么会要求删除<=60的元素，怎么删除之后还有呢？
当我删除元素时，我在干什么？
首先按照列表顺序遍历所有元素，然后按照要求删除元素。当我删除第二位元素时，第三位元素便成为了第二位元素，于是该遍历程序就会跳过被删除元素的下一个元素，因为删除元素是**破坏列表顺序**的行为。

原来是这样的，那么怎么解决这个问题呢？

既然它要跳过被删除元素的下一个元素，那我们把他的指示器在删除一个元素之后退一个单位长度就行了吧。

```java
for (int i = 0; i < scores.size(); i++) {
    if(scores.get(i) <= 60){
        System.out.println(scores.remove(i));
        i--;    //指向删除后的第一个元素
    }
}
```

另一种方法： 
``` java
// 倒着遍历再删除也可以！
for (int i = scores.size() - 1; i >= 0 ; i--) {
    if (scores.get(i) <= 60) {
        System.out.println(scores.remove(i));
    }
}
```
**p.s.**当该数列倒序删除时不会破坏该数列顺序，因为数列以线性方式存储，当在数列非最末端进行修改时会破坏数列顺序。

成功运行！

### 注意：

当`scores.size()`替换为`10`或者是具体的数字使，将会使代码无法运行，错误例如：

```java
for (int i = 0; i < 10; i++) {
    System.out.println(i + ":" + socers.size());
    if (socers.get(i) < 60) {
        socers.remove(i);
        i--;
    }
}

Exception in thread "main" java.lang.IndexOutOfBoundsException: Index: 3, Size: 3
	at java.util.ArrayList.rangeCheck(ArrayList.java:659)
	at java.util.ArrayList.get(ArrayList.java:435)
	at ArrayList.Test.main(Test.java:18)
```

错误显示为：`IndexOutOfBoundsException`，是因为当以定值为循环判定条件时进行添加或删除操作时，列表的长度在变化，size()也在变化，但是循环条件是不变，所以就会出现该错误。假设该数列有一个值小于60，结果应该做**10**次循环，但是如果循环判定条件设置的是`i < 常数`就会做**11**次循环，导致index out of。
