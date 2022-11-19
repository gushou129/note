# 配置lisp环境

下载环境：

`brew install clisp`

下载vscode的`common lisp`插件，在打开的`*.lisp`文件下方选择`Common Lisp`类型

在`Code-Runner`插件的`code-runner.executorMap`中添加:

```json
"lisp": "cd $dir && clisp -E UTF-8 $fileName",
"commonlisp": "cd $dir && clisp -E UTF-8 $fileName"
```

运行成功。