package com.forDece.dao;


import com.forDece.framework.annotations.Autowired;
import com.forDece.framework.annotations.Value;

@Autowired
public class VipPlusLongClassNameAutoCamelCase {

    @Value("这个类没有指定实例名，则实例默认为驼峰命名~")
    String name;

    @Override
    public String toString() {
        return "VipPlusLongClassNameAutoCamelCase{" +
                "name='" + name + '\'' +
                '}';
    }

}
