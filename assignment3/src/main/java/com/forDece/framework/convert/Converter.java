package com.forDece.framework.convert;

@FunctionalInterface
public interface Converter<S, T> {
    T convert(S source);
}
