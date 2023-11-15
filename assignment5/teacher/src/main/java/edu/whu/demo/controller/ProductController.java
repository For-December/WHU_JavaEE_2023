package edu.whu.demo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import edu.whu.demo.domain.Product;
import edu.whu.demo.domain.ProductDTO;
import edu.whu.demo.domain.Result;
import edu.whu.demo.domain.Supplier;
import edu.whu.demo.exception.ProductAdminException;
import edu.whu.demo.service.IProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产品管理的API
 * 目前所有方法都返回ResponseEntity<Result<T>>, 使用全局异常检测后，这些代码可以简化
 * </p>
 *
 * @author jiaxy
 * @since 2022-10-31
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    IProductService productService;

    @ApiOperation("根据Id查询商品")
    @GetMapping("/{id}")
    public ResponseEntity<Result<Product>> getProduct(@ApiParam("商品Id") @PathVariable long id) {
        Product result = productService.getById(id);
        if (result == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(new Result<>(result, null));
        }
    }

    @ApiOperation("根据条件查询商品")
    @GetMapping("")
    public ResponseEntity<Result<IPage<ProductDTO>>> findProduct(@ApiParam("商品名称") String name,
                                                                 @ApiParam("商品价格") Float price,
                                                                 @ApiParam("商品价格") Float stockQuantity,
                                                                 @ApiParam("商品价格") String category,
                                                                 @ApiParam("商品价格") String productType,
                                                                 @ApiParam("供应商名称") String supplierName,
                                                                 @ApiParam("页码") @RequestParam(defaultValue = "0") Integer pageNum,
                                                                 @ApiParam("每页记录数") @RequestParam(defaultValue = "10") Integer pageSize) {
        Map<String, Object> condition = new HashMap<>();
        if (name != null) {
            condition.put("name", "%" + name + "%");
        }
        if (price != null) {
            condition.put("price", price);
        }
        if (category != null) {
            condition.put("category", category);
        }
        if (stockQuantity != null) {
            condition.put("stockQuantity", stockQuantity);
        }
        if (productType != null) {
            condition.put("productType", productType);
        }
        if (supplierName != null) {
            condition.put("supplierName", supplierName);
        }
        IPage<ProductDTO> result = productService.findProduct(condition, pageNum, pageSize);
        return ResponseEntity.ok(new Result<>(result, null));
    }


    @ApiOperation("添加商品")
    @PostMapping("")
    public ResponseEntity<Result<Product>> addProduct(@RequestBody Product product) {
        try {
            Product result = productService.addProduct(product);
            return ResponseEntity.ok(new Result<>(result, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Result<>(null, e.getMessage()));
        }
    }

    @ApiOperation("修改商品信息")
    @PutMapping("/{id}")
    public ResponseEntity<Result<String>> updateProduct(@PathVariable long id, @RequestBody Product product) throws ProductAdminException {
        try {
            productService.updateProduct(id, product);
            return ResponseEntity.ok(new Result<>("success", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Result<>(null, e.getMessage()));
        }
    }

    @ApiOperation("修改商品的供应商")
    @PutMapping("/{id}/suppliers")
    public ResponseEntity<Result<String>> updateProductSuppliers(@PathVariable long id, @RequestBody List<Supplier> suppliers) throws ProductAdminException {
        try {
            productService.updateProductSuppliers(id, suppliers);
            return ResponseEntity.ok(new Result<>("success", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Result<>(null, e.getMessage()));
        }
    }

    @ApiOperation("删除商品")
    @DeleteMapping("/{id}")
    public ResponseEntity<Result<String>> deleteProduct(@PathVariable long id) {
        try {
            productService.removeById(id);
            return ResponseEntity.ok(new Result<>("success", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Result<>(null, e.getMessage()));
        }
    }
}

