package com.neulab.fund.repository;

import com.neulab.fund.entity.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 策略数据访问接口
 */
public interface StrategyRepository extends JpaRepository<Strategy, Long> {
    // 可自定义查询方法
} 