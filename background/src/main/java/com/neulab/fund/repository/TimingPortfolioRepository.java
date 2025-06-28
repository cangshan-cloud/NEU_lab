package com.neulab.fund.repository;

import com.neulab.fund.entity.TimingPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 择时组合数据访问接口
 */
public interface TimingPortfolioRepository extends JpaRepository<TimingPortfolio, Long> {
    // 可自定义查询方法
} 