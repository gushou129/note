# Reflex

Reflex是使用java提供的特殊类对任意对象直接进行数据操作（破坏封装性）。

例如需要存储一个未知类对象的所有成员变量时，使用reflex机制，可以提取该类对象中的所有数据进行crud。

因其直接操作原始数据的特性，可以使用reflex机制搭建框架。

需求：使用一个方法，能将输入的对象中的所有实例成员变量存储入计算机中。

## 实现底层框架

```java
public static void saveAllInPC(Object object) {
  try (
      // 建立文件输出管道
      PrintStream ps = new PrintStream(new FileOutputStream("/Users/jam/java/day13/src/d4/1.txt", true))) {
    // 使用reflex机制获取该未知对象的所有成员变量
    Class c = object.getClass();
    Field[] fields = c.getDeclaredFields();
    // 遍历输出到PC
    ps.println("---------" + c.getSimpleName());
    for (Field field : fields) {
      // 将访问权限打开，否则在获取值时会报错
      field.setAccessible(true);
      // 输出到PC
      ps.println(field.getName() + "=" + field.get(object));
    }
  } catch (Exception e) {
    e.printStackTrace();
  }
}
```

## Proxy

使用reflex机制可是实现proxy实现类的方法使用，通过proxy可以解决需要在实现方法前后添加功能，例如性能分析等场景。

主要使用`Proxy.newProxyInstance()`对方法进行代理。

```java
public static Object newProxyInstance(
  // 通常使用入参 object.getClass().getClassLoader()
  ClassLoader loader,
  // 通常使用入参 object.getClass().getInterfaces()
  Class<?>[] interfaces,
  // 使用 new InvocationHandler() 实现 invoke()
  InvocationHandler h)
throws IllegalArgumentException
```

需求：对一个类中的所有方法做性能分析

interface:

```java
public interface MyInterface {
  void method1();

  void method2();
}
```

implement:

```java
public class MyInterfaceImpl implements MyInterface {
  private String name;

  public MyInterfaceImpl(String name) {
    this.name = name;
  }

  @Override
  public void method1() {
    System.out.println("MyInterfaceImpl.method1()---name=" + name);
  }

  @Override
  public void method2() {
    System.out.println("MyInterfaceImpl.method2()---name=" + name);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
```

proxy:

```java
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class MyInterfaceProxy {
  public static MyInterface getProxy(Object object) {
    return (MyInterface) Proxy.newProxyInstance(
      object.getClass().getClassLoader(), 
      object.getClass().getInterfaces(),
      new InvocationHandler() {
      @Override
      public Object invoke(
        Object proxy, 
        Method method, 
        Object[] args) throws Throwable {
          System.out.println("start to proxy.");
          // 将方法返回值保留并返回
          Object rs = method.invoke(object, args);
          System.out.println("end to proxy.");
          return rs;
        }
      });
  }
}
```

Test:

```java
public class Test {
  public static void main(String[] args) {
    MyInterface mi = MyInterfaceProxy.getProxy(new MyInterfaceImpl("001"));
    System.out.println("-----------");
    mi.method1();
    System.out.println("-----------");
    mi.method2();
  }
}
/*$Out:
-----------
start to proxy.
MyInterfaceImpl.method1()---name=001
end to proxy.
-----------
start to proxy.
MyInterfaceImpl.method2()---name=001
end to proxy.
*/
```
