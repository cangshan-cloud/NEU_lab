package com.neulab.fund.service;

import com.neulab.fund.entity.StrategyPortfolio;
import java.util.List;

/**
 * 策略与组合关联业务接口
 */
public interface StrategyPortfolioService {
    /** 查询全部关联 */
    List<StrategyPortfolio> getAllRelations();
    /** 根据ID查询关联 */
    StrategyPortfolio getRelationById(Long id);
    /** 新增关联 */
    StrategyPortfolio createRelation(StrategyPortfolio relation);
} 