package com.forDece.convert.impl;

import com.forDece.dao.Vip;
import com.forDece.dao.VipPlusLongClassNameAutoCamelCase;
import com.forDece.framework.MiniApplicationContext;
import com.forDece.framework.exception.BeansException;
import com.forDece.framework.impl.MyApplicationContextPlus;
import org.junit.jupiter.api.Test;

public class TestFrameworkAnnotation {

    @Test
    public void testCreateVip() throws BeansException {
        MiniApplicationContext ctx = new MyApplicationContextPlus("com.forDece.dao");
        Vip myVip = ctx.getBean("myVip", Vip.class);
        System.out.println(myVip);

    }

    @Test
    public void testDefaultAutoWire() throws BeansException {
        MiniApplicationContext ctx = new MyApplicationContextPlus("com.forDece.dao");
        VipPlusLongClassNameAutoCamelCase unNamed =
                ctx.getBean("vipPlusLongClassNameAutoCamelCase", VipPlusLongClassNameAutoCamelCase.class);
        System.out.println(unNamed);
    }
}
