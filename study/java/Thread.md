# Thread

## 进程与线程

进程就是可以运行的程序，线程就是单个进程内部的子进程。
进程就是汽车，线程就是汽车内部运行的零件。

开车时，汽车一定在动，但是汽车内部的零件不一定全在动，比如空调、车窗在等待开关，发动机在等待汽油打着火等待。

### 并行与并发

并行与串行的区别，是在 cpu 个数的多少，并行使用多个 cpu 处理串行处理多列任务，串行使用单个 cpu 处理单列任务。

并发与串行的区别，是要执行的程序列数的多少，并发使用单个 cpu 处理多列任务，串行使用单个 cpu 处理单列任务。

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

问题：现有 3 个生产者与 2 个消费者，生产者生产商品放入仓库，消费者消费商品从仓库取出，当仓库中的商品小于 100 时生产者生产商品，大于等于 100 时消费者消费商品，模拟过程。

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
public synchronized void produce() {
    try {
      while (true) {
        if (c.getMoney() < Count.NUM) {
          c.setMoney(c.getMoney() + 1);
          i++;
        }
        Thread.sleep(15);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
}
```

消费者：

```java
pubulic static int i;
public synchronized void consume() {
    try {
      while (true) {
        if (c.getMoney() >= Count.NUM) {
          c.setMoney(c.getMoney() - 1);
          i++;
        }
        Thread.sleep(15);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
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

使用`sleep()`进行线程间通讯：

消费者与生产者的抽象父类：

```java
public abstract class Actor implements Runnable {
  private Count c;
  private String name;

  public Actor(String name, Count c) {
    this.c = c;
    this.name = name;
  }

  public abstract boolean compare();

  public abstract void plus();

  public void canDo() {
    synchronized (c) {
      try {
        while (true) {
          if (compare()) {
            plus();
            c.notifyAll();
            c.wait();
          } else {
            c.notifyAll();
            c.wait();
          }
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  };

  @Override
  public void run() {
    canDo();
  }

  public Count getC() {  return c;  }

  public void setC(Count c) { this.c = c;  }

  public String getName() {  return name;  }

  public void setName(String name) {  this.name = name; }
}
```

消费者：

```java
public class Consumer extends Actor {
  public static int i = 0;
  public Producer(String name, Count c) {
    super(name, c);
  }
  @Override
  public boolean compare() {
    return getC().getMoney() == Count.NUM;
  }

  @Override
  public void plus() {
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    getC().setMoney(getC().getMoney() - 1);
    i++;
  }
}
```

生产者：

```java
public class Producer extends Actor {
  public static int i = 0;

  public Producer(String name, Count c) {
    super(name, c);
  }
  @Override
  public boolean compare() {
    return getC().getMoney() != Count.NUM;
  }

  @Override
  public void plus() {
    getC().setMoney(getC().getMoney() + 1);
    i++;
  }
}
```

![线程状态](https://jam-note-img.oss-cn-hangzhou.aliyuncs.com/leanote-img/202211082237775.png)

线程共包括以下 5 种状态:

1. **新建状态**(New): 线程对象被创建后，就进入了新建状态。例如，`Thread thread = new Thread()`。

2. **就绪状态**(Runnable): 也被称为“可执行状态”。线程对象被创建后，其它线程调用了该对象的`start()`方法，从而来启动该线程。例如，`thread.start()`。处于就绪状态的线程，随时可能被 CPU 调度执行。

3. **运行状态**(Running): 线程获取 CPU 权限进行执行。需要注意的是，线程只能从就绪状态进入到运行状态。

4. **阻塞状态**(Blocked): 阻塞状态是线程因为某种原因放弃 CPU 使用权，暂时停止运行。直到线程进入就绪状态，才有机会转到运行状态。阻塞的情况分三种：

   1. 等待阻塞 -- 通过调用线程的`wait()`方法，让线程等待某工作的完成。
   2. 同步阻塞 -- 线程在获取`synchronized`同步锁失败(因为锁被其它线程所占用)，它会进入同步阻塞状态。
   3. 其他阻塞 -- 通过调用线程的`sleep()`或`join()`或发出了 I/O 请求时，线程会进入到阻塞状态。当`sleep()`状态超时、`join()`等待线程终止或者超时、或者 I/O 处理完毕时，线程重新转入就绪状态。

5. **死亡状态**(Dead): 线程执行完了或者因异常退出了`run()`方法，该线程结束生命周期。

### sleep 与 wait 的区别

#### 位置

`sleep()`是`Thread`类的静态方法；`wait()`是`Object`类的成员方法；

#### 锁的操作

`sleep()`调用后不会释放对象锁，只是将 cpu 让出给其他线程；`wait()`调用后会释放对象锁并进入阻塞状态`Blocked`，只有当此对象调用`notify()`后才会将此线程移入就绪状态`Runnable`；

#### 同步上下文

`sleep()`可以在任何地方使用但是需要异常处理，`wait()`只能在同步方法或同步代码块中使用且不需要异常处理。

#### 调用机制

`wait()`通常用于循环中处理不符合判断结果的情况，使该线程处于阻塞状态`Blocked`并释放对象锁等资源**直到该判断结果为真**；`sleep()`一般不用循环中处理结果，因为`sleep()`不释放对象锁资源，所以该资源会较长时间的被一个线程占用，导致其他线程无法使用该资源。

## 线程池

存放线程的池子，用完的线程放入，等待下次继续使用。

情景：现有 1000 个用户需要使用购物系统，那么购物系统需要创建 1000 个线程来处理这 1000 个用户的操作吗？

通常情况下，该系统会创建一个线程池用于存放**工作线程**和**任务队列**，其中工作线程`WorkThread`就是用来处理用户操作的线程，任务队列`WorkQueue`就是这 1000 个用户的操作指令。

### 实现线程池

- 线程池接口：`ExecutorService`。
- 线程池实现类：`ThreadPoolExecutor`。

```java
public ThreadPoolExecutor(
  // 核心线程数量
  int corPoolSize,
  // 可创建的最大线程数量 = 核心线程数量 + 临时线程数量
  int maximumPoolSize,
  // 临时线程存活时间
  long keepAliveTime,
  // 临时线程存活时间单位（时分秒）
  TimeUnit unit,
  // 任务队列容器，用于缓存未处理的任务
  BlockingQueue<Runnable> workQueue,
  // 线程工厂，用于创建线程类型
  ThreadFactory threadFactory,
  // 线程负荷时的拒绝方法
  RejectedEcecutionHandler handler
)
```

临时线程何时创建？什么时候拒绝任务？

- 临时线程只有在核心线程忙、任务队列满的情况才会创建
- 只有当所有线程忙、任务队列满的情况才会拒绝任务

通常使用多态方式创建对象。

### Runnable

使用`ExecutorService`中的`execute()`操作线程：

```java
public class Test {
  public static void main(String[] args) {
    ExecutorService pool = new ThreadPoolExecutor(3, 5, 4, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
    Runnable m = new MyRunnable();

    try {
      for (int i = 0; i < 11; i++) {
        pool.execute(m);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
class MyRunnable implements Runnable{
  private static int i = 0;
  @Override
  public void run() {
    System.out.println(Thread.currentThread().getName() + ":::" + ++MyRunnable.i);
  }
}
```

### Callable

使用`ExecutorService`中的`submit()`操作线程：

```java
public class Test {
  public static void main(String[] args) {
    ExecutorService pool = new ThreadPoolExecutor(3, 5, 4, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    try {
      for (int i = 0; i < 11; i++) {
        pool.submit(new MyCall());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

class MyCall implements Callable<String>{
  public static int i = 0;
  @Override
  public String call() throws Exception {
    return Thread.currentThread().getName() + "::::" + String.valueOf(++i);
  }
}
```

### Executors
