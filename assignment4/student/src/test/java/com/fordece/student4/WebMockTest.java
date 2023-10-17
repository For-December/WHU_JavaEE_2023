package com.fordece.student4;

import com.fordece.student4.controller.ProductController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class WebMockTest {
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ProductController()).build();
    }

    @Test
    public void testPost() throws Exception {
//        this.mockMvc.perform(post("/api/products/").content("{\"id\":1,\"name\":\"product1\",\"price\":99.9,\"description\":\"普通商品。\"}")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("success")));

    }
}
