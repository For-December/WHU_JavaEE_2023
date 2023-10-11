package edu.whu.demo;

import edu.whu.framework.InitMethod;

public class ClassWithoutParamlessConstructor {

    public ClassWithoutParamlessConstructor(String str){};

    @InitMethod
    public void init(){
        System.out.println("init()方法被调用");
    }
}
