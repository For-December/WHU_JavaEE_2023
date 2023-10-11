package edu.whu;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        String bootstrapClass = getPropertiesByPath("/myApp.properties").getProperty("bootstrapClass");

        try {
            Object obj = createObj(bootstrapClass);
            System.out.println("obj = " + obj);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static Object createObj(String bootstrapClass) throws Exception {

        Class<?> myClass = Class.forName(bootstrapClass);
        var obj = myClass.getDeclaredConstructor().newInstance();
        for (Method method : myClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(InitMethod.class)) {
                System.out.print("@InitMethod: ");
                method.invoke(obj);
            }
        }
        return obj;

    }

    public static Properties getPropertiesByPath(String filePath) {
        Properties props = new Properties();
        try (InputStream input =
                     Main.class.getResourceAsStream(filePath)) {
            if (Optional.ofNullable(input).isEmpty()) {
                throw new RuntimeException("获取资源流失败！");
            }
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }
}
