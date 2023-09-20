---
title: JavaEE系统学习笔记（一）
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



- 应用理解：
> `getResourceAsStream()`中指定的路径就是`PropertiesLoader`所在的`classpath`
> 在`IDEA`中，该文件可放在项目的`Resources`目录下，其文件最终会被放置于`classpath`
> 注意，一个项目中`classpath`是有且仅有一个的确定值，它是所有`.class文件`所在的根路径！
>
> 

```java
public class PropertiesLoader {

    public static void main(String[] args) {
        Properties props = new Properties();
        try (InputStream input =
                     PropertiesLoader.class.getResourceAsStream("/default.properties")) {
            if (input == null) {return;}
            props.load(input);
            System.out.println(props.getProperty("name"));
            System.out.println(props.getProperty("text"));
        } catch (IOException e) {
            System.out.println("Load properties error!");
        }

    }
}
```

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



> 输出：

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



#### try-with-resource 语法

```java
try(FileReader fr = new FileReader("test.txt");
    BufferedReader br = new BufferedReader(fr)){
    // code ...

} catch (IOException e) {
    throw new RuntimeException(e);
}
```





### Java 高级

#### IO 和 Streams

- 工具类：Files，用于简单的文件读写

> 字符：

```java
// 默认使用UTF-8编码读取:
String content1 = Files.readString(Path.of("/path/to/file.txt"));
// 可指定编码:
String content2 = Files.readString(Path.of("/path", "to", "file.txt"), StandardCharsets.ISO_8859_1);
// 按行读取并返回每行内容:
List<String> lines = Files.readAllLines(Path.of("/path/to/file.txt"));
```
> 字节：

```java
// 写入二进制文件:
byte[] data = new byte[1024];
Files.write(Path.of("/path/to/file.txt"), data);
// 写入文本并指定编码
Files.writeString(Path.of("/path/to/file.txt"), "文本内容...", StandardCharsets.ISO_8859_1);
// 按行写入文本
List<String> lines = ...
Files.write(Path.of("/path/to/file.txt"), lines);
```

{{< admonition type=tip title="工具类总结">}} 
其实就是：（所有`java.utils`包下的类）

- Files
- Collections
- Arrays
- Properties

 {{< /admonition >}}



#### 泛型

- 类型安全，类型错误能够在编译期间被检查出来



- 推荐的常见泛型参数命名：

```
T denotes a Type.
E denotes an Element.
K denotes a Key
N denotes a Number
V denotes a Value.
```



- 方法中定义泛型

```java
public static <T extends Comparable<T>> T maximum(T x, T y, T z) {
    T max = x;
    if (y.compareTo(max) > 0) {
        max = y;
    }
    if (z.compareTo(max) > 0) {
        max = z;
    }
    return max;
}
```



#### Reflection 反射（=>自省）

- 三种获取 Class 对象的方式：

```java
// 类名
Class userClass = Class.forName("com.forDece.User");
// 类
Class userClass = User.class;
// 对象
User user = new User();
Class userClass = user.getClass();
```

- 获取字段注意（方法同理）

> `getFields()`  // 获取所有字段，包含当前类及其父类
>
> `getDeclaredFields()`  // 只获取当前类声明的字段，不包含父类

- 读私有属性

```java
xxxField.setAccessible(true);
```

- 反射创建对象（和直接 new 的区别在于，反射创建的对象可以通过参数来动态指定）

```java
Class userClass = Class.forName("edu.whu.model.User");
Object user = userClass.newInstance();    //call parameterless constructor

Constructor constructor = userClass.getConstructor(int.class, String.class);
Object user2= constructor.newInstance(1,"Li"); // call a constrctor method

Method method = userClass.getMethod("setName", String.class); 
method.invoke(user2, “zhang”); //invoke a method 
```

#### 注解 Annotation

- 多用于框架开发
- 通过反射可读取注解

> 作用：
>
> - 生成文档，通过代码里标识的元数据生成javadoc文档。
>
> - 编译检查，通过代码里标识的元数据让编译器在编译期间进行检查验证。
>
> - 编译时动态处理，编译时通过代码里标识的元数据动态处理，例如动态生成代码。
>
> - 运行时动态处理，运行时通过代码里标识的元数据动态处理，例如使用反射注入实例。

- 元注解：

| 元注解形式  | 作用                              |
| ----------- | --------------------------------- |
| @Target     | 设置目标范围                      |
| @Retention  | 设置保持性，保留时间              |
| @Inherited  | 注解可被继承到子类                |
| @Repeatable | 此注解可以重复修饰                |
| @Documented | 注解可被 javadoc 工具解析，入文档 |

- 注解默认参数

```java
public @interface ValidateAge {
    int min() default 18;
    int max() default 100;
}
```



- 参考：[Java注解 - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/562451020)





## Maven

### Why we need Maven？

> 1. 标准化的项目结构
> 2. 标准化的构建过程（clean、compile、test、package、release......）
> 3. 依赖管理（包管理器）

### Maven 生命周期流：

![image-20230920153620424](https://img.fordece.cn/imgs/2023/09/image-20230920153620424.png)

- 为什么是生命周期？

![image-20230920160355254](https://img.fordece.cn/imgs/2023/09/image-20230920160355254.png)

例如，当执行了 package，其之前的（图中 package 上面的）所有流程操作都会执行！

相当于经历了 package 之前的生命周期

- package 和 install

> package 打包到 target 目录，install 会打包并发布到本地仓库

| 命令        | 说明                       |
| :---------- | :------------------------- |
| mvn clean   | 清除target目录             |
| mvn compile | 编译main目录               |
| mvn test    | 执行所有的测试方法         |
| mvn package | 打包当前的项目到target目录 |
| mvn install | 打包并且发布到本地仓库     |

- plugin 是对 maven 生命周期的扩展

![image-20230920160858789](https://img.fordece.cn/imgs/2023/09/image-20230920160858789.png)





### 依赖 scope 字段

![image-20230920154901002](https://img.fordece.cn/imgs/2023/09/image-20230920154901002.png)

- 为空则所有期间都生效

### 镜像配置

阿里云镜像文档：[maven镜像_maven下载地址_maven安装教程-阿里巴巴开源镜像站 (aliyun.com)](https://developer.aliyun.com/mirror/maven)

- 所有项目配置（缺省、全局）

> `setting.xml`

```xml
<mirror>
    <id>aliyunmaven</id>
    <mirrorOf>*</mirrorOf>
    <name>阿里云公共仓库</name>
    <url>https://maven.aliyun.com/repository/public</url>
</mirror>
```





## 单元测试



### 首要准则 FIRST

- Fast 要快，不然影响部署，目的是快速找到问题
- Isolated 各单元测试之间不要互相依赖
- Repeatable 每次测试前的环境都必须相同，测试结果可重复，不要有本地环境依赖、绝对路径（C 盘xxxx文件）
- Self-validating 自校验，用 Assert 语句，必须完全脱离人工检查（不人工看控制台打印）
- Thoroughly & Timely 测试各种非法输入、极端条件。写一段代码就写一段单元测试，不能代码写完了再补单元测试

### JUNIT 坐标

```xml
   <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.9.2</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <!--    添加 maven-surefire-plugin 否则 junit5 构建会出问题，插件和生命周期相关-->
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <!-- JUnit 5 requires Surefire version 2.22.0 or higher -->
                <version>2.22.0</version>
            </plugin>
        </plugins>
    </build>
```

### JUNIT 断言

```java
// org.junit.jupiter.api.Assertions包下有很多断言的方法
// 示例：

    @Test
    public void testCreateObj() {
        // 类不存在，对象创建失败
        assertThrows(ClassNotFoundException.class,
                () -> Main.createObj(""));


        // 类创建成功，且是 MyClass 的实例
        assertDoesNotThrow(() -> {
            assertInstanceOf(MyClass.class, Main.createObj(bootstrapClass));
        });
    }
```

### JUNIT 注解

- @BeforeEach
- @AfterEach
- @BeforeAll
- @AfterAll
- @Test
