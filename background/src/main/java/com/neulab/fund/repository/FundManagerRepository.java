package com.neulab.fund.repository;

import com.neulab.fund.entity.FundManager;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 基金经理数据访问接口
 */
public interface FundManagerRepository extends JpaRepository<FundManager, Long> {
    // 可自定义查询方法
} 