package com.neulab.fund.repository;

import com.neulab.fund.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 组合产品数据访问接口
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    // 可自定义查询方法
} 