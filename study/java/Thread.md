# Thread

## 进程与线程

进程就是可以运行的程序，线程就是单个进程内部的子进程。
进程就是汽车，线程就是汽车内部运行的零件。

开车时，汽车一定在动，但是汽车内部的零件不一定全在动，比如空调、车窗在等待开关，发动机在等待汽油打着火等待。

### 并行与并发

并行与串行的区别，是在cpu个数的多少，并行使用多个cpu处理串行处理多列任务，串行使用单个cpu处理单列任务。

并发与串行的区别，是要执行的程序列数的多少，并发使用单个cpu处理多列任务，串行使用单个cpu处理单列任务。

并行：存在于多核，多列串行；
并发：存在于单核，多列交替执行；

![上并发，下并发](https://jam-note-img.oss-cn-hangzhou.aliyuncs.com/leanote-img/202211082037508.png)
上并发，下并发

## 多线程的创建

### 继承`Thread`类

重写`run()`后使用对象调用`start()`来创建线程。

```java
class MyThread extends Thread {
  @Override
  public void run() {
    System.out.println("is MyThread");
  }
}
```

### 实现`Runnable`接口

在实现接口后使用`Thread`类接收`Runnable`的实现类创建线程。

```java
public class Test {
  public static void main(String[] args) {
    MyThread mt = new MyThread();
    MyThread mt2 = new MyThread();
    Thread t = new Thread(mt);
    Thread t2 = new Thread(mt2);
    t.start();
    t2.start();
  }
}

class MyThread implements Runnable {
  @Override
  public void run() {
    System.out.println("is MyThread");
  }
}
```

### 实现`Callable`接口

使用`FutureTask`去接收`Callable`的实现类用于保存运行结果，在线程运行结束后可使用`FutureTask`的`get()`方法获取运行结果。

```java
public class Test {
  public static void main(String[] args) {
    FutureTask<String> f = new FutureTask<>(new MyThread());
    FutureTask<String> f2 = new FutureTask<>(new MyThread());
    Thread t = new Thread(f);
    Thread t2 = new Thread(f2);

    t.start();
    t2.start();
    try {
      System.out.println(f.get());
      System.out.println(f2.get());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

class MyThread extends Thread implements Callable<String> {
  @Override
  public String call() throws Exception {
    return super.getName();
  }
}
```

### 三种创建线程的区别

|                  Thread                  |                 Runnable                 |               Callable               |
| :--------------------------------------: | :--------------------------------------: | :----------------------------------: |
| 创建方便，但拓展性差，且不能获取运行结果 | 拓展性强，但创建不便，且不能获取运行结果 | 拓展性强，可获取运行结果，但创建不便 |

## Thread 的常用方法

```java
// 获取当前线程对象
public Thread currentThread();
// 获取当前线程名字
public String getName();
// 设置当前线程名字
Public void setName(String s);
```

## 线程安全

问题：两个人同时使用同一个账户取 1000 元，当前账户有 1000 元，此时 ATM 会吐出多少钱？

分析：

1. 两人同时登陆账号
2. 两人同时输入金额
3. ATM 检测到两人输入的金额均大于等于当前账户余额
4. ATM 为两人分别吐出 1000 元

很明显，此时账户中只有 1000 元，无法为两人分别吐出 1000 元，此时就产生了线程安全问题。

如何解决？

## 线程同步

情景代换为上厕所问题，多人使用一个厕所，一个人进去之后会把门上锁，出来后会解锁，等待下一个人使用。

使用代码表现是这样的：

### 同步代码块
```java
// synchronized{} 描述相当于是一个厕所
// door 代表厕所门
synchronized (door){
  // 此处描写上厕所做的事
  takeAShit();
}
```

或者

### 同步方法
```java
// 使用 synchronized 修饰方法即可
public synchronized void takeAShit();
```

再或者

### Lock
```java
// l相当于门锁，加final是表示这把锁只有一把钥匙
private final Lock l = new ReentrantLock();
// 把门上锁
l.lock();
try {
  // 在门后做的事情
  drawMoney();
} finally {
  // 先开锁，后出门
  l.unlock();
}
```

## 线程通信

问题：现有3个生产者与2个消费者，生产者生产商品放入仓库，消费者消费商品从仓库取出，当仓库中的商品小于100时生产者生产商品，大于等于100时消费者消费商品，模拟过程。

分析：至少需要定义三个类生产者、消费者、仓库。检测商品数使用`if`判断，同步线程使用`synchronized`保证线程安全，<span style="color:red">暂时使用</span>`sleep()`等待同步。

仓库：
```java
public class Count {
  private double money;

  public double getMoney() {
    return money;
  }

  public void setMoney(double money) {
    this.money = money;
  }
}
```

生产者：
```java
pubulic static int i;
public void produce() {
  synchronized (c) {
    try {
      while (true) {
        if (c.getMoney() != Count.NUM) {
          c.setMoney(c.getMoney() + 1);
          i++;
          Thread.sleep(15);
          // c.notifyAll();
          // c.wait();
        } 
        // else {
        //   c.notifyAll();
        //   c.wait();
        // }
      }
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
```

消费者：
```java
pubulic static int i;
public void consume() {
  synchronized (c) {
    try {
      while (true) {
        Thread.sleep(30);
        if (c.getMoney() == Count.NUM) {
          c.setMoney(c.getMoney() - 1);
          i++;
          Thread.sleep(15);
          // c.notifyAll();
          // c.wait();
        } 
        // else {
        //   c.notifyAll();
        //   c.wait();
        // }
        }
      }
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
```

为了方便观察使用`Monitor`输出数据。
```java
@Override
public void run() {
  while(true){
    System.out.println("当前money为：" + c.getMoney());
    System.out.println("当前消费了" + Consumer.i + "次。");
    System.out.println("当前生产了" + Producer.i + "次。");
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
```

运行结果基本正常，如果将等待进程的方式切换为`wait()`也可以使程序正常运行，那么使用`sleep()`与`wait()`的区别在哪里呢？

### sleep()




## 线程池

## 定时器

## 并行并发

## 线程的生命周期
