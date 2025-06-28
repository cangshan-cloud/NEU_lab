package com.neulab.fund.repository;

import com.neulab.fund.entity.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 产品审核数据访问接口
 */
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
    // 可自定义查询方法
} 