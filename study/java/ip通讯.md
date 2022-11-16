# IP通讯

- IP：常使用`InetAddress`工具类来操作IP地址；
- 端口：标志进程的识别码；
- <span style="color=red">协议</span>：连接与通讯的公共约定。

## UDP：User Datagram Protocol

- 无连接、不可靠传输的协议；
- 不确认对方是否准备好，也不确认对方是否收到，所以不可靠；
- 将数据源IP、目的地IP和端口封装为数据包，每个数据包最大为64kb；
- 可以广播发送，因发送后不确认是否收到，所以开销小、速度快。

使用UDP协议发送数据：

Client:
```java
public class Client implements Runnable {
  @Override
  public void run() {
    System.out.println("----------------Client-----------------");
    int port = 8899;
    try (// create send obj
        DatagramSocket socket = new DatagramSocket()) {
      // create datagramOBJ for packet data
      byte[] buffer = "1234567890".getBytes();
      DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getLocalHost(), port);
      socket.send(packet);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
```

Server:
```java
public class Server implements Runnable {
  @Override
  public void run() {
    System.out.println("-------------Server-------------------------");
    int port = 8899;
    try (DatagramSocket socket = new DatagramSocket(port)) {
      byte[] buffer = new byte[1024 * 64];
      DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

      packet.getLength();
      // wait for receive
      socket.receive(packet);

      // print details
      System.out.println(new String(buffer, 0, packet.getLength()));
      System.out.println(packet.getAddress());
      System.out.println(packet.getPort());

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
```

main:
```java
public static void main(String[] args) throws Exception {
  ExecutorService exec = Executors.newCachedThreadPool();
  exec.execute(new Server());
  exec.execute(new Client());
}
/*$Out:(Simple)
-------------Server-------------------
-------------Client-------------------
1234567890
/192.168.31.91
49569
*/
```

### 广播、组播

广播地址`255.255.255.255`，使用该地址可向所有对应端口的主机发送数据。

组播地址`224.0.0.0 ～ 239.255.255.255`，使用该地址可向所有对应端口的主机发送数据。

## TCP：Transmission Control Protocol


- 连接中可进行大数据量的传输；
- 使用“三次握手”的方式建立连接，使用“四次挥手”的方式结束连接，是面向连接的可靠通讯协议；
- 因为连接与断开连接的方式复杂，所以通讯效率低。

### 多发多收消息

### 同时接受多个客户端消息

### 使用线程池优化