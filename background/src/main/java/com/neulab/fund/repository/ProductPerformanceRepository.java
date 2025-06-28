package com.neulab.fund.repository;

import com.neulab.fund.entity.ProductPerformance;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 产品收益数据访问接口
 */
public interface ProductPerformanceRepository extends JpaRepository<ProductPerformance, Long> {
    // 可自定义查询方法
} 