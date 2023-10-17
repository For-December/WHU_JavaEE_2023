package com.fordece.student4;

import com.fordece.student4.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @BeforeEach
    public void addData() {
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/api/products/", new Product(),
                String.class)).contains("success");
    }

    @Test
    public void testPost() {
        Product product = new Product();
        product.setId(0L);
        product.setName("Product1");
        product.setPrice(99.9);
        product.setDescription("普通商品。");
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/api/products/", new Product(),
                String.class)).contains("success");
    }

    @Test
    public void testGetById() {

    }

    @Test
    public void testPut() {
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/api/products", new Product(),
                Product.class)).isNotNull();
    }
}