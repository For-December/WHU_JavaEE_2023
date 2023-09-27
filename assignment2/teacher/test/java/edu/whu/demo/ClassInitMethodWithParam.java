package edu.whu.demo;

import edu.whu.framework.InitMethod;

public class ClassInitMethodWithParam {

    @InitMethod
    public void init(String name){
        System.out.println(name+"init()方法被调用");
    }
}
