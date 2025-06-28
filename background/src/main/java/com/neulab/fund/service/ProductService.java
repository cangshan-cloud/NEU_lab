package com.neulab.fund.service;

import com.neulab.fund.entity.Product;
import java.util.List;

/**
 * 组合产品业务接口
 */
public interface ProductService {
    /** 查询全部产品 */
    List<Product> getAllProducts();
    /** 根据ID查询产品 */
    Product getProductById(Long id);
    /** 新增产品 */
    Product createProduct(Product product);
} 