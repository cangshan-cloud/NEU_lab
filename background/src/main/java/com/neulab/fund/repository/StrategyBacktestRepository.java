package com.neulab.fund.repository;

import com.neulab.fund.entity.StrategyBacktest;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 策略回测数据访问接口
 */
public interface StrategyBacktestRepository extends JpaRepository<StrategyBacktest, Long> {
    // 可自定义查询方法
} 