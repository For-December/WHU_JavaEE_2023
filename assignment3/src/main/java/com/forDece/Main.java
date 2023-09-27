package com.forDece;

import com.forDece.framework.MiniApplicationContext;
import com.forDece.framework.impl.MyApplicationContext;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        MiniApplicationContext ctx = new MyApplicationContext("/applicationContext.xml");
    }

}