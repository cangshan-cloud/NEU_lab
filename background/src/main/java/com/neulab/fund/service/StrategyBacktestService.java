package com.neulab.fund.service;

import com.neulab.fund.entity.StrategyBacktest;
import java.util.List;

/**
 * 策略回测业务接口
 */
public interface StrategyBacktestService {
    /** 查询全部回测记录 */
    List<StrategyBacktest> getAllBacktests();
    /** 根据ID查询回测记录 */
    StrategyBacktest getBacktestById(Long id);
    /** 新增回测记录 */
    StrategyBacktest createBacktest(StrategyBacktest backtest);
    /**
     * 策略回测（VO版）
     */
    com.neulab.fund.vo.StrategyBacktestVO backtest(Long strategyId, com.neulab.fund.vo.StrategyBacktestDTO dto);
    org.springframework.data.domain.Page<com.neulab.fund.entity.StrategyBacktest> findByStrategyId(Long strategyId, org.springframework.data.domain.Pageable pageable);
    org.springframework.data.domain.Page<com.neulab.fund.entity.StrategyBacktest> findAll(org.springframework.data.domain.Pageable pageable);
} 