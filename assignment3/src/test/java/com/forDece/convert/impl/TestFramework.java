package com.forDece.convert.impl;

import com.forDece.dao.User;
import com.forDece.framework.MiniApplicationContext;
import com.forDece.framework.exception.BeansException;
import com.forDece.framework.impl.MyApplicationContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFramework {

    @Test
    public void test() {
        assertEquals(true, Number.class.isAssignableFrom(Long.class));

    }

    @Test
    public void testCreateUser() {
        System.out.println("Hello world!");
        MiniApplicationContext ctx = new MyApplicationContext("/applicationContext.xml");
        User user;
        try {
            user = ctx.getBean("myUser", User.class);
            System.out.println(user);
        } catch (BeansException e) {
            throw new RuntimeException(e);
        }

        assertEquals("jack", user.getName());
        assertEquals(12, user.getId());
        assertEquals(99.9999, user.getScore());

    }
}
