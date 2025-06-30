package com.neulab.fund.repository;

import com.neulab.fund.entity.StrategyBacktest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 策略回测数据访问层
 */
@Repository
public interface StrategyBacktestRepository extends JpaRepository<StrategyBacktest, Long> {
    
    /**
     * 根据策略ID查询回测结果，按创建时间倒序
     */
    List<StrategyBacktest> findByStrategyIdOrderByCreatedAtDesc(Long strategyId);
    
    /**
     * 根据策略ID和回测类型查询
     */
    List<StrategyBacktest> findByStrategyIdAndBacktestType(Long strategyId, String backtestType);
    
    /**
     * 根据状态查询回测结果
     */
    List<StrategyBacktest> findByStatus(String status);
} 