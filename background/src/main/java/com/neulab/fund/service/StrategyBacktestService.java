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
} 