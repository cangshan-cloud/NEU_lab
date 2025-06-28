package com.neulab.fund.repository;

import com.neulab.fund.entity.StrategyPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 策略与组合关联数据访问接口
 */
public interface StrategyPortfolioRepository extends JpaRepository<StrategyPortfolio, Long> {
    // 可自定义查询方法
} 