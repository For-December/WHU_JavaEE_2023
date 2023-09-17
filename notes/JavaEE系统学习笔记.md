---
title: JavaEE系统学习笔记
subtitle:
date: 2023-09-13T14:31:30+08:00
draft: false
author:
  name:
  link:
  email:
  avatar:
description:
keywords:
license:
comment: true
weight: 0
tags:
  - SpringBoot
categories:
  - SpringBoot
hiddenFromHomePage: false
hiddenFromSearch: false
summary:
resources:
  - name: featured-image
    src: featured-image.jpg
  - name: featured-image-preview
    src: featured-image-preview.jpg
toc: true
math: false
lightgallery: false
password:
message:
repost:
  enable: false
  url:

# See details front matter: https://fixit.lruihao.cn/documentation/content-management/introduction/#front-matter
---

重新开始，系统学习 javaEE
<!--more-->



## JavaSE

### classpath

- 指定编译出的字节码文件=>`.class文件`=>所在的根路径

```shell
java -classpath out/production/HelloJava edu.whu.Hello
# 可简写为 -cp
```

- 默认的`classpath`是当前路径=>`./`

详见：[classpath和jar - 廖雪峰的官方网站 (liaoxuefeng.com)](https://www.liaoxuefeng.com/wiki/1252599548343744/1260466914339296)

### signature

-  `参数类型` 和 `函数名` 确定一个函数，和返回值没关系
- 例如：`test(String,int)`就是一个 signature



### 异常处理

#### CheckedException

- 能够处理的异常，用户问题
- 编译时强制要求`try-catch`或者抛给外层调用者

#### RuntimeException

- 不强制 catch
- 经常是程序 bug 才抛出

#### Error

- 不可处理的错误

### 集合类及其工具



### 内置泛型类特性

> 今天有朋友问了个相关的问题，故引出此内容

上结论：

- LinkedList、ArrayList 等等容器相关的泛型类，`add()` 进去的自定义类（如 Dog{};）是其引用/指针（浅拷贝）
- 以 ArrayList 为例，看源码前先来个直观示例：

```java
Dog dog1 = new Dog();
ArrayList<Dog> strings = new ArrayList<>();
strings.add(dog1); // 这里其实是把 dog1 的指针存了进去（浅拷贝）
Dog dog2 = strings.get(0); // 从 list 里取出 dog1 的指针
dog2.setName("apache");
System.out.println("dog2.getName() = " + dog2.getName());
System.out.println("dog1.getName() = " + dog1.getName()); // 操作影响到了 dog1
```

输出：

![image-20230916004620025](https://img.fordece.cn/imgs/2023/09/image-20230916004620025.png)



- 以 ArrayList 为例，看源码：

```java
    /**
     * Appends the specified element to the end of this list.
     *
     * @param e element to be appended to this list
     * @return {@code true} (as specified by {@link Collection#add})
     */
    public boolean add(E e) {
        modCount++;
        add(e, elementData, size);
        return true;
    }

    /**
     * This helper method split out from add(E) to keep method
     * bytecode size under 35 (the -XX:MaxInlineSize default value),
     * which helps when add(E) is called in a C1-compiled loop.
     */
    private void add(E e, Object[] elementData, int s) {
        if (s == elementData.length)
            elementData = grow();
        elementData[s] = e;
        size = s + 1;
    }
```

> 其实只需要看下面这一个地方：
>
> java 不同与 cpp，前者没有`重载拷贝构造函数`一说，泛型 e 直接用等号赋值就是浅拷贝
> 

![image-20230916004910570](https://img.fordece.cn/imgs/2023/09/image-20230916004910570.png)

> ArrayList 同理：

![image-20230916005402237](https://img.fordece.cn/imgs/2023/09/image-20230916005402237.png)

> 同样是泛型对象直接等号赋值

![image-20230916005426942](https://img.fordece.cn/imgs/2023/09/image-20230916005426942.png)

- 总结

学到了一点点之前不知道的小细节，图一乐，我也不知道什么时候有用。

### JDK8 新特性

- 参考：[Java 8 新特性 | 菜鸟教程 (runoob.com)](https://www.runoob.com/java/java8-new-features.html)
- 语法糖++

#### 方法引用

- 菜鸟教程的代码我就不 copy 了
- :new:里面提到一个 jdk8 新加入的带注解泛型`Supplier`，实现如下：

```java
@FunctionalInterface
public interface Supplier<T> {
    T get();
}
```

- 可以用它实现一些工厂模式的需求

```java
Supplier<Car> carFactory = Car::new; // 构造器方法
boolean isEqual = carFactory.get() == carFactory.get();
System.out.println("isEqual = " + isEqual); // isEqual = false
```

- 传参数时，java 只有一个方法的接口可传入匿名内部类，也可以被简写为 lambda 表达式
- `Car::new` 可以理解为一个无参，返回 Car 对象的 lambda 表达式

#### Stream

- 流式编程、内部迭代。
- 生成流通常用以下四种方法：

```java
// 前两种是使用 Collection 接口的两个默认方法
default Stream<E> stream() {
    return StreamSupport.stream(spliterator(), false);
}
default Stream<E> parallelStream() { // 并行
    return StreamSupport.stream(spliterator(), true);
}
// 后两种是使用工具类生成：
Arrays.stream(T[] array);
Stream.of(T... values);
```

- 看一个简单直观的栗子

```java
package com.forDece;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Arrays.asList(1, 2, 3, 4, 5, 6, 7)
                .parallelStream().map(num -> num * 2)
                .filter(num -> num > 3)
                .limit(6)
                .forEach(System.out::println);
    }
}
```

![image-20230917220646261](https://img.fordece.cn/imgs/2023/09/image-20230917220646261.png)

- 菜鸟教程提到： Stream 提供了内部迭代的方式， 通过访问者模式(Visitor)实现

#### 函数式接口

- 带有`@FunctionalInterface`注解的接口
- 一句话：可被隐式转换为 lambda 表达式的接口

```java
@FunctionalInterface
interface GreetingService 
{
    void sayMessage(String message);
}
```

- jdk8 提供的函数式接口：[Java 8 函数式接口 | 菜鸟教程 (runoob.com)](https://www.runoob.com/java/java8-functional-interfaces.html)

#### Base64
