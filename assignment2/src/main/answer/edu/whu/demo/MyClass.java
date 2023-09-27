package edu.whu.demo;

import edu.whu.framework.InitMethod;

/**
 * 使用@InitMethod注解的类
 * 一般情况下，注解在框架或者类库中定义。其他项目通过标注注解来让框架对其进行处理
 */
public class MyClass {
    String status = "offline";

    @InitMethod
    public void init(){
        this.status = "online";
        System.out.println("init()方法被调用");
    }

    public String getStatus() {
        return status;
    }
}
