# JDBC

防止SQL注入为什么要用`PreparedStatement`

什么是SQL注入：

通过输入特殊字符串来充当后台SQL语句并执行的方式就是SQL注入。

某一企业的密码验证是使用一下方式拼接而成：

```java
String sql1 = 
  "select * from test where username='" 
    + name 
    + "' and password='" 
    + pwd 
    + "'";
```

直接使用`conn.createStatement().executeQuery(sql1);`会使密码验证失效。

于是便有了预设SQL的方法进行规避风险。

`PreparedStatement`的原理便是转义字符串。

使用`useServerPrepStmts=true`将SQL语句进行预编译，提高SQL执行效率。
