package com.neulab.fund.repository;

import com.neulab.fund.entity.FactorData;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 因子数据访问接口
 */
public interface FactorDataRepository extends JpaRepository<FactorData, Long> {
    // 可自定义查询方法
} 