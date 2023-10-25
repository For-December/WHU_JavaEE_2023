package edu.whu.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import edu.whu.demo.domain.Result;
import edu.whu.demo.domain.Supplier;
import edu.whu.demo.exception.ProductAdminException;
import edu.whu.demo.service.ISupplierService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  供应商管理的API
 * </p>
 *  目前所有方法都返回ResponseEntity<Result<T>>, 使用全局异常检测后，这些代码可以简化
 * @author jiaxy
 * @since 2022-10-31
 */
@RestController
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    ISupplierService supplierService;

    @ApiOperation("根据Id查询供应商")
    @GetMapping("/{id}")
    public ResponseEntity<Result<Supplier>> getSupplier(@ApiParam("供应商Id")@PathVariable long id){
        Supplier result = supplierService.getById(id);
        if(result==null) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(new Result<>(result,null));
        }
    }

    @ApiOperation("根据条件查询供应商")
    @GetMapping("")
    public ResponseEntity<Result<IPage<Supplier>>> findSupplier(@ApiParam("商品名称")String name,
                                                                @ApiParam("页码")@RequestParam(defaultValue = "0")Integer pageNum,
                                                                @ApiParam("每页记录数") @RequestParam(defaultValue = "10")Integer pageSize){
        IPage<Supplier> result = supplierService.findSuppliers(name, pageNum, pageSize);
        return ResponseEntity.ok(new Result<>(result,null));
    }

    /**
     * 添加供应商
     * @param supplier
     * @return
     * @throws ProductAdminException 在Controller抛出异常，可以通过全局异常处理进行捕获
     */
    @ApiOperation("添加供应商")
    @PostMapping("")
    public ResponseEntity<Result<Supplier>> addSupplier(@RequestBody Supplier supplier){
        try {
            supplierService.saveOrUpdate(supplier);
            return ResponseEntity.ok(new Result<>(supplier,null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new Result<>(null,e.getMessage()));
        }
    }

    @ApiOperation("修改供应商")
    @PutMapping("/{id}")
    public ResponseEntity<Result<String>> updateSupplier(@PathVariable long id,@RequestBody Supplier supplier){
        try {
            supplierService.saveOrUpdate(supplier);
            return ResponseEntity.ok(new Result<>("success",null));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new Result<>(null,e.getMessage()));
        }
    }

    @ApiOperation("删除供应商")
    @DeleteMapping("/{id}")
    public ResponseEntity<Result<String>> deleteSupplier(@PathVariable long id){
        try {
            supplierService.removeById(id);
            return ResponseEntity.ok(new Result<>("success",null));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new Result<>(null,e.getMessage()));
        }
    }

}

