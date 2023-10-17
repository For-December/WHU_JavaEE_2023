package com.fordece.student4.service.impl;

import com.fordece.student4.entity.Product;
import com.fordece.student4.service.ProductService;
import org.springframework.stereotype.Service;

@Service

public class ProductServiceImpl implements ProductService {
    @Override
    public Product getProductById(Long id) {
        return new Product();
    }
}
