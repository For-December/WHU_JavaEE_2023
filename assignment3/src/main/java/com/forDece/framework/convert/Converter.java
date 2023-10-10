package com.forDece.framework.convert;

@FunctionalInterface
public interface Converter<S, T> {
    // 实现不同类型间的转换
    T convert(S source);
}
