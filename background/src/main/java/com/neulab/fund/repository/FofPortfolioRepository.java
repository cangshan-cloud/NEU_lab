package com.neulab.fund.repository;

import com.neulab.fund.entity.FofPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * FOF组合数据访问接口
 */
public interface FofPortfolioRepository extends JpaRepository<FofPortfolio, Long> {
    // 可自定义查询方法
} 