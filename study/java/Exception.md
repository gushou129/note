# Exception

**Error**
- 系统级别问题，JVM退出等，代码无法控制。

Exception
编译或运行时可能出现的错误。

- RuntimeException及其子类：运行时异常，编译阶段不会报错，例如`NullPointException`等；
- 除上述情况以外的所有异常：编译时异常，编译阶段必须处理，否则程序无法通过编译，如`ParseException`。

## 如何处理异常

### 编译时异常

```java
// 1. throws
void method() throws Exception;
// 2. try catch
try {}
catch (Exception e){ e.printStackTrack; }
```

使用第一种和第二种的结合：
```java
public static void main(String[] args) {
    try {
        print();
    } catch (Exception e) {
        e.printStackTrace();
    }

}
public static void print() throws ParseException {
    String date = "2013-12";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM");
    Date d = sdf.parse(date);
    System.out.println(d);
}
```

### RuntimeException

```java
public static void main(String[] args) {
    try {
        print(10, 0);
    } catch (Exception e) {
        e.printStackTrace();
    }

}
public static void print(int i1, int i2) {
    System.out.println(i1 / i2);
}
```

运行时异常会自行抛出，所以只需要在外层进行`try catch`就行了。

## 如何利用异常机制

使用异常机制来处理多种非法数据时非常方便。

```java
while (true) {
    try {
        System.out.println("pls enter a number, bigger than 0.");
        String priceStr = sc.nextLine();
        double price = Double.valueOf(priceStr);
        if (price > 0) {
            System.out.println(price);
            break;
        }
    } catch (Exception e) {
        System.out.println("您的输入有误，请重试。");
    }
}
```

## 自定义异常

使用自定义异常可以在数据异常时可以直接输出异常日志，方便调试与定位。

### 编译时异常

在编译阶段一定要处理的异常，必须处理。

```java
public static void main(String[] args) {
    try {
        checkAge(-12);
    } catch (JamException e) {
        e.printStackTrace();
    }
}
public static void checkAge(int age) throws JamException {
    if (age < 0 || age > 200) {
        throw new JamException(age + " is illeagal!");
    } else {
        System.out.println("yes.");
    }
}
```

### 运行时异常

基本用法与编译时异常一样，只是将自定义异常的继承父类从`Exception`更换到了`RuntimeException`了。
