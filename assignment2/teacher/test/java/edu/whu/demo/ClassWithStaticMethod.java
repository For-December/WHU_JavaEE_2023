package edu.whu.demo;

import edu.whu.framework.InitMethod;

public class ClassWithStaticMethod {

    @InitMethod
    public static void init(){
        System.out.println("init()方法被调用");
    }
}
