package com.fordece.student4.controller;


import com.fordece.student4.entity.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@Tag(name = "对商品进行增删改查")
public class ProductController {

    static Map<Long, Product> productMap = Collections.synchronizedMap(new HashMap<>());

    @GetMapping("/{id}")
    @Operation(summary = "通过id查询商品")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        if (productMap.containsKey(id)) {
            Product product = productMap.get(id);
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    @Operation(summary = "添加商品")
    public String postProduct(@RequestBody Product product) {
        productMap.put(product.getId(), product);
        return "success";
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改商品")
    public String putProduct(@PathVariable Long id, @RequestBody Product product) {
        if (!productMap.containsKey(id)) {
            return "no such product";
        }
        productMap.put(id, product);
        return "success";
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "通过id删除商品")
    public String deleteProductById(@PathVariable Long id) {
        if (!productMap.containsKey(id)) {
            return "success";
        }
        productMap.remove(id);
        return "success";

    }

}
