package com.forDece;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    @BeforeAll
    static void init(){

    }

    @AfterAll
    static void end(){

    }



    // 测试运行顺序是不确定的
    // 而且有可能是并行测试
    // 如果有可能被共享的资源，可以每次测试前都新建对象
    @BeforeEach
    public void setUp() {

    }

    // 保证每个单元测试前，环境都是相同的
    @AfterEach
    public void tearDown() {

    }

    @Test
    void testA() {
        assertEquals(1, 1);
    }
}
