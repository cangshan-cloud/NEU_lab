package com.neulab.fund.repository;

import com.neulab.fund.entity.IndexPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 基金指数组合数据访问接口
 */
public interface IndexPortfolioRepository extends JpaRepository<IndexPortfolio, Long> {
    // 可自定义查询方法
} 