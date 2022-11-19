# 在macOS上配置maven

`brew install maven`

如果无法安装依赖：使用`brew update`后再重试。

brew 默认把应用安装在`/usr/local/Cellar/maven`

进入文件夹中的`libexec/conf`修改`setting.xml`

在`mirrors`中添加：
```xml
<mirror>
  <id>alimaven</id>
  <name>aliyun maven</name>
  <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
  <mirrorOf>central</mirrorOf>        
</mirror>
```

完成镜像配置。

在命令行运行`mvn -v`，如果输出无法找到`JAVA_HOME`时，可修改`~/.zshrc`：

```shell
// 添加该行，根据本地安装路径修改
export JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk-17.0.5.jdk/Contents/Home"
```
