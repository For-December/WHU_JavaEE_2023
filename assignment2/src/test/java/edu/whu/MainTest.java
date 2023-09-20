package edu.whu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    String bootstrapClass;

    @BeforeEach
    public void init() {
        bootstrapClass = Main.getPropertiesByPath("/myApp.properties")
                .getProperty("bootstrapClass");
    }

    @Test
    public void testGetProperties() {
        assertDoesNotThrow(
                () -> Main.getPropertiesByPath("/myApp.properties"));
    }

    @Test
    public void testCreateObj() {
        // 类不存在，对象创建失败
        assertThrows(ClassNotFoundException.class,
                () -> Main.createObj(""));


        // 类创建成功，且是 MyClass 的实例
        assertDoesNotThrow(() -> {
            assertInstanceOf(MyClass.class, Main.createObj(bootstrapClass));
        });
    }
}
