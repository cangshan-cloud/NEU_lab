package com.neulab.fund.repository;

import com.neulab.fund.entity.Factor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 因子数据访问接口
 */
@Repository
public interface FactorRepository extends JpaRepository<Factor, Long> {
    // 可自定义查询方法
} 