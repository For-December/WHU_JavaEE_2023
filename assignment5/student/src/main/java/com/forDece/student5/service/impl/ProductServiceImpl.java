package com.forDece.student5.service.impl;

import com.forDece.student5.domain.Product;
import com.forDece.student5.dao.ProductDao;
import com.forDece.student5.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author forDece
 * @since 2023-10-30
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductDao, Product> implements IProductService {

}
