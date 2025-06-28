package com.neulab.fund.service;

import com.neulab.fund.entity.Strategy;
import java.util.List;

/**
 * 策略业务接口
 */
public interface StrategyService {
    /** 查询全部策略 */
    List<Strategy> getAllStrategies();
    /** 根据ID查询策略 */
    Strategy getStrategyById(Long id);
    /** 新增策略 */
    Strategy createStrategy(Strategy strategy);
} 