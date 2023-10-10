package com.forDece.framework.impl;

import com.forDece.framework.MiniApplicationContext;
import com.forDece.framework.annotations.*;
import com.forDece.framework.convert.impl.StringToNumber;
import com.forDece.framework.exception.BeansException;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class MyApplicationContextPlus implements MiniApplicationContext {
    public static Map<String, Object> beans = new HashMap<>();

    public MyApplicationContextPlus(String packageName) {
        try {
            ImmutableSet<ClassPath.ClassInfo> topLevelClassesRecursive = ClassPath.from(ClassLoader.getSystemClassLoader()).getTopLevelClassesRecursive(packageName);
            for (ClassPath.ClassInfo classInfo : topLevelClassesRecursive) {
                System.out.println(classInfo.getName());
                Class<?> aClass = Class.forName(classInfo.getName());
                if (!aClass.isAnnotationPresent(Autowired.class)) {
                    continue;
                }

                String beanName = aClass.getDeclaredAnnotation(Autowired.class).value();

                if (beanName.isEmpty()) {
                    // 改为驼峰
                    beanName = (aClass.getSimpleName().charAt(0) + "").toLowerCase() +
                            aClass.getSimpleName().substring(1);
                }

                // 有自动注入注解, 先创建实例
                Object obj = aClass.getDeclaredConstructor().newInstance();
                Field[] declaredFields = aClass.getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    if (declaredField.isAnnotationPresent(Value.class)) {
                        declaredField.setAccessible(true);
                        if (declaredField.getType() == String.class) {
                            declaredField.set(obj, declaredField.getAnnotationsByType(Value.class)[0].value());
                            beans.put(beanName, obj);
                            continue;
                        }

                        // 一个类是否可以分配给另一个类，Number 可以由 Integer 等分配得到~
                        if (Number.class.isAssignableFrom(declaredField.getType())) {
                            declaredField.set(obj,
                                    new StringToNumber<>(declaredField.getType()).parseNumber(
                                            declaredField.getAnnotationsByType(Value.class)[0].value()
                                    ));
                            beans.put(beanName, obj);
                            continue;
                        }

                        throw new RuntimeException("无法进行类型转换，请自行实现String 到该转换器！");

                    }

                    if (declaredField.isAnnotationPresent(DoubleType.class)) {
                        declaredField.setAccessible(true);
                        declaredField.setDouble(obj, declaredField.getAnnotationsByType(DoubleType.class)[0].value());
                        beans.put(beanName, obj);

                        continue;
                    }

                    if (declaredField.isAnnotationPresent(BoolType.class)) {
                        declaredField.setAccessible(true);
                        declaredField.setBoolean(obj, declaredField.getAnnotationsByType(BoolType.class)[0].value());
                        beans.put(beanName, obj);
                        continue;
                    }

                    if (declaredField.isAnnotationPresent(IntType.class)) {
                        declaredField.setAccessible(true);
                        declaredField.setInt(obj, declaredField.getAnnotationsByType(IntType.class)[0].value());
                        beans.put(beanName, obj);
                        continue;
                    }

                    if (declaredField.isAnnotationPresent(ShortType.class)) {
                        declaredField.setAccessible(true);
                        declaredField.setShort(obj, declaredField.getAnnotationsByType(ShortType.class)[0].value());
                        beans.put(beanName, obj);
                        continue;
                    }

                    System.out.println("该字段未添加注解, 字段为：" + declaredField.getName());

//                    throw new RuntimeException("检测到陌生注解，请使用 annotation 包下的注解！");


                }

            }
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public Object getBean(String name) throws BeansException {
        return beans.get(name);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) beans.get(name);
    }
}
