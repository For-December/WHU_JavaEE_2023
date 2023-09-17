package com.forDece;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Arrays.asList(1, 2, 3, 4, 5, 6, 7)
                .parallelStream().map(num -> num * 2)
                .filter(num -> num > 3)
                .limit(6)
                .forEach(System.out::println);
    }
}