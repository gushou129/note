# API

## Object 

### toString()

### equals()

## Objects

### equals()
与`Object`的`equals()`不同，这个具有非空检验。

### isNull()

## StringBuilder

为什么比`String`创建字符串更快？
javac 大概从1.5版本开始，把所有用加号连接的 string 运算都隐式的改写成 stringbuilder。
如果没有循环的情况下，单行用加号拼接字符串是没有性能损失的，java 编译器会隐式的替换成 stringbuilder，但在有循环的情况下，编译器没法做到足够智能的替换，仍然会有不必要的性能损耗，

## Math

## System

## BIgDecimal
