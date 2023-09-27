package edu.whu.demo;

import edu.whu.framework.InitMethod;

public abstract class AbstractClass {
    @InitMethod
    public void init(String name){
        System.out.println(name+"init()方法被调用");
    }

}
