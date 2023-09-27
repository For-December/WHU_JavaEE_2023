package edu.whu.demo;

import edu.whu.framework.InitMethod;

public class ClassWithPrivateMethod {


    @InitMethod
    private void init(){
        System.out.println("init()方法被调用");
    }
}
