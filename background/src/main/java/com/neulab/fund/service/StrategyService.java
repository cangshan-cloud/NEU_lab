package com.neulab.fund.service;

import com.neulab.fund.entity.Strategy;
import com.neulab.fund.entity.StrategyBacktest;
import com.neulab.fund.entity.StrategyPortfolio;
import com.neulab.fund.entity.FofPortfolio;
import com.neulab.fund.entity.IndexPortfolio;
import com.neulab.fund.entity.TimingPortfolio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import com.neulab.fund.vo.StrategyVO;
import com.neulab.fund.vo.StrategyDTO;

/**
 * 策略服务接口
 */
public interface StrategyService {
    
    /**
     * 获取所有策略
     */
    List<Strategy> getAllStrategies();
    
    /**
     * 分页获取策略列表
     */
    Page<Strategy> getStrategiesWithPage(Pageable pageable);
    
    /**
     * 根据ID获取策略
     */
    Strategy getStrategyById(Long id);
    
    /**
     * 创建策略
     */
    Strategy createStrategy(Strategy strategy);
    
    /**
     * 更新策略
     */
    Strategy updateStrategy(Strategy strategy);
    
    /**
     * 删除策略
     */
    void deleteStrategy(Long id);
    
    /**
     * 策略回测
     */
    StrategyBacktest backtestStrategy(Long strategyId, Map<String, Object> backtestConfig);
    
    /**
     * 获取策略回测结果
     */
    List<StrategyBacktest> getStrategyBacktests(Long strategyId);
    
    /**
     * 创建FOF组合
     */
    FofPortfolio createFofPortfolio(FofPortfolio fofPortfolio);
    
    /**
     * 更新FOF组合
     */
    FofPortfolio updateFofPortfolio(FofPortfolio fofPortfolio);
    
    /**
     * 获取FOF组合
     */
    FofPortfolio getFofPortfolioById(Long id);
    
    /**
     * 获取所有FOF组合
     */
    List<FofPortfolio> getAllFofPortfolios();
    
    /**
     * 创建基金指数组合
     */
    IndexPortfolio createIndexPortfolio(IndexPortfolio indexPortfolio);
    
    /**
     * 更新基金指数组合
     */
    IndexPortfolio updateIndexPortfolio(IndexPortfolio indexPortfolio);
    
    /**
     * 获取基金指数组合
     */
    IndexPortfolio getIndexPortfolioById(Long id);
    
    /**
     * 获取所有基金指数组合
     */
    List<IndexPortfolio> getAllIndexPortfolios();
    
    /**
     * 创建择时组合
     */
    TimingPortfolio createTimingPortfolio(TimingPortfolio timingPortfolio);
    
    /**
     * 更新择时组合
     */
    TimingPortfolio updateTimingPortfolio(TimingPortfolio timingPortfolio);
    
    /**
     * 获取择时组合
     */
    TimingPortfolio getTimingPortfolioById(Long id);
    
    /**
     * 获取所有择时组合
     */
    List<TimingPortfolio> getAllTimingPortfolios();
    
    /**
     * 大资产配置策略
     */
    Strategy createAssetAllocationStrategy(Map<String, Object> strategyConfig);
    
    /**
     * 计算策略权重矩阵
     */
    Map<String, Object> calculateWeightMatrix(Map<String, Object> config);
    
    /**
     * 选择策略资产
     */
    List<Map<String, Object>> selectStrategyAssets(Map<String, Object> config);
    
    /**
     * 选择策略基金
     */
    List<Map<String, Object>> selectStrategyFunds(Map<String, Object> config);

    /**
     * 分页+筛选查询策略列表（VO版）
     */
    org.springframework.data.domain.Page<com.neulab.fund.vo.StrategyVO> pageList(String keyword, String type, String riskLevel, String status, org.springframework.data.domain.Pageable pageable);
    /**
     * 获取策略详情（VO版）
     */
    com.neulab.fund.vo.StrategyVO getStrategyVOById(Long id);
    /**
     * 新建策略（DTO版）
     */
    com.neulab.fund.vo.StrategyVO createStrategy(com.neulab.fund.vo.StrategyDTO dto);
    /**
     * 编辑策略（DTO版）
     */
    com.neulab.fund.vo.StrategyVO updateStrategy(Long id, com.neulab.fund.vo.StrategyDTO dto);
} 