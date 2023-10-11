package com.forDece.framework.convert.impl;

import com.forDece.framework.convert.Converter;

public class StringToNumber<T extends Number> implements Converter<String, T> {
    private final Class<?> targetType;
    // 实现 String 转 数字类型
    public StringToNumber(Class<?> targetType) {
        this.targetType = targetType;
//        System.out.println(Number.class.isAssignableFrom(Long.class));
    }

    @SuppressWarnings("unchecked")
    public T parseNumber(String text) {
        if (targetType == Float.class) {
            return (T) Float.valueOf(text);
        } else if (targetType == Integer.class) {
            return (T) Integer.valueOf(text);
        } else if (targetType == Double.class) {
            return (T) Double.valueOf(text);
        } else if (targetType == Long.class) {
            return (T) Long.valueOf(text);
        }
        return null;
    }

    @Override
    public T convert(String source) {
        return source.isEmpty() ? null : parseNumber(source);
    }
}
