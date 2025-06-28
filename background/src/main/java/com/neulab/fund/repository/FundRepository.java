package com.neulab.fund.repository;

import com.neulab.fund.entity.Fund;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 基金数据访问接口
 */
public interface FundRepository extends JpaRepository<Fund, Long> {
    // 可自定义查询方法
} 