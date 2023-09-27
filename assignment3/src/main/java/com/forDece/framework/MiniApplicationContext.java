package com.forDece.framework;

import com.forDece.framework.exception.BeansException;

public interface MiniApplicationContext {
    Object getBean(String name) throws BeansException;

    // 先声明泛型，后使用
    <T> T getBean(String name, Class<T> requiredType) throws BeansException;
}
