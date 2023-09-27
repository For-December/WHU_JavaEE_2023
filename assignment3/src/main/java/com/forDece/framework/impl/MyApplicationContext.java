package com.forDece.framework.impl;

import com.forDece.framework.MiniApplicationContext;
import com.forDece.framework.convert.Converter;
import com.forDece.framework.convert.impl.StringToNumber;
import com.forDece.framework.exception.BeansException;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class MyApplicationContext implements MiniApplicationContext {


    public static Map<String, Object> beans = new HashMap<>();

    public MyApplicationContext(String propFile) {
        try (InputStream input =
                     MyApplicationContext.class.getResourceAsStream(propFile)) {
            if (Optional.ofNullable(input).isEmpty()) {
                throw new RuntimeException("获取资源流失败！");
            }
            Document document = new SAXReader().read(input);
            Element rootElement = document.getRootElement();
            getElements(rootElement);

            System.out.println(beans.get("user"));

        } catch (IOException | DocumentException e) {
            throw new RuntimeException(e);
        }

    }


    public static void getElements(Element element) {
        for (Element bean : element.elements("bean")) {
            System.out.println(bean.attribute("id").getData());
            System.out.println(bean.attribute("class").getData());

            Map<String, String> param = new HashMap<>();
            for (Element property : bean.elements("property")) {
                param.put(property.attribute("name").getValue(),
                        property.attribute("val").getValue());
            }
            addBeans(bean.attribute("id").getValue(),
                    bean.attribute("class").getValue(),
                    param);
        }

    }

    public static synchronized void addBeans(String clazzId, String clazzName, Map<String, String> params) {
        try {
            Class<?> myClass = Class.forName(clazzName);
            var obj = myClass.getDeclaredConstructor().newInstance();

            for (Method declaredMethod : myClass.getDeclaredMethods()) {
                String name = declaredMethod.getName();
                if (name.contains("set")) {
                    params.entrySet().stream()
                            .filter(entry -> name.toLowerCase().contains(entry.getKey().toLowerCase()))
                            .forEach(entry -> {
                                try {
                                    assert (1 == declaredMethod.getParameterCount());
                                    if (declaredMethod.getParameterTypes()[0] == entry.getKey().getClass()) {
                                        declaredMethod.invoke(obj, entry.getValue());
                                    } else if (Number.class.isAssignableFrom(declaredMethod.getParameterTypes()[0])) {
                                        declaredMethod.invoke(obj,
                                                new StringToNumber<>(declaredMethod.getParameterTypes()[0])
                                                        .parseNumber(entry.getValue()));
                                    } else {
                                        throw new RuntimeException("无法进行类型转换！");
                                    }


                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                }
            }
            beans.put(clazzId, obj);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("配置文件中指定的类 " + clazzName + "不存在");
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("实例化类失败！");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("构造器不存在");
        }
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return beans.get(name);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return null;
    }
}
