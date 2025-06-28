package com.neulab.fund.repository;

import com.neulab.fund.entity.FundPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 基金组合数据访问接口
 */
public interface FundPortfolioRepository extends JpaRepository<FundPortfolio, Long> {
    // 可自定义查询方法
} 