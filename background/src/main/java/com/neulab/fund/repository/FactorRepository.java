package com.neulab.fund.repository;

import com.neulab.fund.entity.Factor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 因子数据访问接口
 */
public interface FactorRepository extends JpaRepository<Factor, Long> {
    // 可自定义查询方法
} 