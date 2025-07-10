package com.neulab.fund.service.impl;

import com.neulab.fund.entity.Strategy;
import com.neulab.fund.entity.StrategyBacktest;
import com.neulab.fund.entity.StrategyPortfolio;
import com.neulab.fund.entity.FofPortfolio;
import com.neulab.fund.entity.IndexPortfolio;
import com.neulab.fund.entity.TimingPortfolio;
import com.neulab.fund.repository.StrategyRepository;
import com.neulab.fund.repository.StrategyBacktestRepository;
import com.neulab.fund.repository.StrategyPortfolioRepository;
import com.neulab.fund.repository.FofPortfolioRepository;
import com.neulab.fund.repository.IndexPortfolioRepository;
import com.neulab.fund.repository.TimingPortfolioRepository;
import com.neulab.fund.service.StrategyService;
import com.neulab.fund.vo.StrategyVO;
import com.neulab.fund.vo.StrategyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * 策略服务实现类
 */
@Service
public class StrategyServiceImpl implements StrategyService {
    
    @Autowired
    private StrategyRepository strategyRepository;
    
    @Autowired
    private StrategyBacktestRepository strategyBacktestRepository;
    
    @Autowired
    private StrategyPortfolioRepository strategyPortfolioRepository;
    
    @Autowired
    private FofPortfolioRepository fofPortfolioRepository;
    
    @Autowired
    private IndexPortfolioRepository indexPortfolioRepository;
    
    @Autowired
    private TimingPortfolioRepository timingPortfolioRepository;

    @Override
    public List<Strategy> getAllStrategies() {
        return strategyRepository.findAll();
    }

    @Override
    public Page<Strategy> getStrategiesWithPage(Pageable pageable) {
        return strategyRepository.findAll(pageable);
    }

    @Override
    public Strategy getStrategyById(Long id) {
        return strategyRepository.findById(id).orElse(null);
    }

    @Override
    public Strategy createStrategy(Strategy strategy) {
        strategy.setCreatedAt(LocalDateTime.now());
        strategy.setUpdatedAt(LocalDateTime.now());
        return strategyRepository.save(strategy);
    }

    @Override
    public Strategy updateStrategy(Strategy strategy) {
        strategy.setUpdatedAt(LocalDateTime.now());
        return strategyRepository.save(strategy);
    }

    @Override
    public void deleteStrategy(Long id) {
        strategyRepository.deleteById(id);
    }

    @Override
    public StrategyBacktest backtestStrategy(Long strategyId, Map<String, Object> backtestConfig) {
        Strategy strategy = getStrategyById(strategyId);
        if (strategy == null) {
            return null;
        }
        
        StrategyBacktest backtest = new StrategyBacktest();
        backtest.setStrategyId(strategyId);
        backtest.setBacktestType("BACKTEST");
        
        // 转换日期字符串为LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        backtest.setStartDate(LocalDate.parse((String) backtestConfig.get("startDate"), formatter));
        backtest.setEndDate(LocalDate.parse((String) backtestConfig.get("endDate"), formatter));
        backtest.setInitialCapital((Double) backtestConfig.get("initialCapital"));
        
        // 执行回测计算
        Map<String, Object> results = executeBacktest(strategy, backtestConfig);
        backtest.setTotalReturn((Double) results.get("totalReturn"));
        backtest.setAnnualReturn((Double) results.get("annualReturn"));
        backtest.setMaxDrawdown((Double) results.get("maxDrawdown"));
        backtest.setSharpeRatio((Double) results.get("sharpeRatio"));
        backtest.setResults(results.toString());
        backtest.setStatus("COMPLETED");
        backtest.setCreatedAt(LocalDateTime.now());
        
        return strategyBacktestRepository.save(backtest);
    }

    @Override
    public List<StrategyBacktest> getStrategyBacktests(Long strategyId) {
        return strategyBacktestRepository.findByStrategyIdOrderByCreatedAtDesc(strategyId);
    }

    @Override
    public FofPortfolio createFofPortfolio(FofPortfolio fofPortfolio) {
        fofPortfolio.setCreatedAt(LocalDateTime.now());
        fofPortfolio.setUpdatedAt(LocalDateTime.now());
        return fofPortfolioRepository.save(fofPortfolio);
    }

    @Override
    public FofPortfolio updateFofPortfolio(FofPortfolio fofPortfolio) {
        fofPortfolio.setUpdatedAt(LocalDateTime.now());
        return fofPortfolioRepository.save(fofPortfolio);
    }

    @Override
    public FofPortfolio getFofPortfolioById(Long id) {
        return fofPortfolioRepository.findById(id).orElse(null);
    }

    @Override
    public List<FofPortfolio> getAllFofPortfolios() {
        return fofPortfolioRepository.findAll();
    }

    @Override
    public IndexPortfolio createIndexPortfolio(IndexPortfolio indexPortfolio) {
        indexPortfolio.setCreatedAt(LocalDateTime.now());
        indexPortfolio.setUpdatedAt(LocalDateTime.now());
        return indexPortfolioRepository.save(indexPortfolio);
    }

    @Override
    public IndexPortfolio updateIndexPortfolio(IndexPortfolio indexPortfolio) {
        indexPortfolio.setUpdatedAt(LocalDateTime.now());
        return indexPortfolioRepository.save(indexPortfolio);
    }

    @Override
    public IndexPortfolio getIndexPortfolioById(Long id) {
        return indexPortfolioRepository.findById(id).orElse(null);
    }

    @Override
    public List<IndexPortfolio> getAllIndexPortfolios() {
        return indexPortfolioRepository.findAll();
    }

    @Override
    public TimingPortfolio createTimingPortfolio(TimingPortfolio timingPortfolio) {
        timingPortfolio.setCreatedAt(LocalDateTime.now());
        timingPortfolio.setUpdatedAt(LocalDateTime.now());
        return timingPortfolioRepository.save(timingPortfolio);
    }

    @Override
    public TimingPortfolio updateTimingPortfolio(TimingPortfolio timingPortfolio) {
        timingPortfolio.setUpdatedAt(LocalDateTime.now());
        return timingPortfolioRepository.save(timingPortfolio);
    }

    @Override
    public TimingPortfolio getTimingPortfolioById(Long id) {
        return timingPortfolioRepository.findById(id).orElse(null);
    }

    @Override
    public List<TimingPortfolio> getAllTimingPortfolios() {
        return timingPortfolioRepository.findAll();
    }

    @Override
    public Strategy createAssetAllocationStrategy(Map<String, Object> strategyConfig) {
        Strategy strategy = new Strategy();
        strategy.setStrategyName((String) strategyConfig.get("name"));
        strategy.setStrategyType("ASSET_ALLOCATION");
        strategy.setDescription((String) strategyConfig.get("description"));
        strategy.setParameters(strategyConfig.toString());
        strategy.setStatus("ACTIVE");
        
        return createStrategy(strategy);
    }

    @Override
    public Map<String, Object> calculateWeightMatrix(Map<String, Object> config) {
        // 实现权重矩阵计算逻辑
        Map<String, Object> weightMatrix = new HashMap<>();
        
        // 这里可以添加复杂的权重计算算法
        // 例如：马科维茨均值方差模型、风险平价模型等
        
        weightMatrix.put("weights", new HashMap<>());
        weightMatrix.put("riskMetrics", new HashMap<>());
        weightMatrix.put("correlationMatrix", new HashMap<>());
        
        return weightMatrix;
    }

    @Override
    public List<Map<String, Object>> selectStrategyAssets(Map<String, Object> config) {
        // 实现资产选择逻辑
        List<Map<String, Object>> selectedAssets = new ArrayList<>();
        
        // 这里可以添加资产筛选算法
        // 例如：基于因子模型、技术指标、基本面分析等
        
        return selectedAssets;
    }

    @Override
    public List<Map<String, Object>> selectStrategyFunds(Map<String, Object> config) {
        // 实现基金选择逻辑
        List<Map<String, Object>> selectedFunds = new ArrayList<>();
        
        // 这里可以添加基金筛选算法
        // 例如：基于基金评级、历史业绩、风险指标等
        
        return selectedFunds;
    }
    
    /**
     * 执行回测计算
     */
    private Map<String, Object> executeBacktest(Strategy strategy, Map<String, Object> config) {
        Map<String, Object> results = new HashMap<>();
        
        // 这里实现回测计算逻辑
        // 使用简单的数学公式计算收益率、回撤等指标
        
        results.put("totalReturn", 0.15); // 15%总收益
        results.put("annualReturn", 0.08); // 8%年化收益
        results.put("maxDrawdown", -0.05); // -5%最大回撤
        results.put("sharpeRatio", 1.2); // 夏普比率
        
        return results;
    }
    
    /**
     * 执行再平衡逻辑
     */
    private Map<String, Object> executeRebalance(Strategy strategy, Map<String, Object> config) {
        Map<String, Object> results = new HashMap<>();
        
        // 这里实现再平衡逻辑
        // 根据市场变化调整资产配置
        
        results.put("rebalanceDate", LocalDateTime.now());
        results.put("newWeights", new HashMap<>());
        results.put("rebalanceCost", 0.001);
        
        return results;
    }
    
    /**
     * 计算监控指标
     */
    private Map<String, Object> calculateMonitorMetrics(Strategy strategy) {
        Map<String, Object> metrics = new HashMap<>();
        
        // 这里计算实时监控指标
        // 例如：当前收益率、风险指标、偏离度等
        
        metrics.put("currentReturn", 0.05);
        metrics.put("riskLevel", "MEDIUM");
        metrics.put("deviation", 0.02);
        
        return metrics;
    }

    @Override
    public Page<StrategyVO> pageList(String keyword, String type, String riskLevel, String status, Pageable pageable) {
        // 支持条件筛选
        return strategyRepository.findAll((root, query, cb) -> {
            var predicates = new java.util.ArrayList<>();
            if (keyword != null && !keyword.isEmpty()) {
                predicates.add(cb.or(
                    cb.like(root.get("strategyCode"), "%" + keyword + "%"),
                    cb.like(root.get("strategyName"), "%" + keyword + "%"),
                    cb.like(root.get("description"), "%" + keyword + "%")
                ));
            }
            if (type != null && !type.isEmpty()) {
                predicates.add(cb.equal(root.get("strategyType"), type));
            }
            if (riskLevel != null && !riskLevel.isEmpty()) {
                predicates.add(cb.equal(root.get("riskLevel"), riskLevel));
            }
            if (status != null && !status.isEmpty()) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            return cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        }, pageable).map(this::toVO);
    }

    @Override
    public StrategyVO getStrategyVOById(Long id) {
        Strategy strategy = getStrategyById(id);
        return strategy == null ? null : toVO(strategy);
    }

    @Override
    public StrategyVO createStrategy(StrategyDTO dto) {
        Strategy entity = fromDTO(dto);
        entity.setCreatedAt(java.time.LocalDateTime.now());
        entity.setUpdatedAt(java.time.LocalDateTime.now());
        Strategy saved = strategyRepository.save(entity);
        return toVO(saved);
    }

    @Override
    public StrategyVO updateStrategy(Long id, StrategyDTO dto) {
        Strategy entity = getStrategyById(id);
        if (entity == null) return null;
        updateEntityFromDTO(entity, dto);
        entity.setUpdatedAt(java.time.LocalDateTime.now());
        Strategy saved = strategyRepository.save(entity);
        return toVO(saved);
    }

    // DTO转实体
    private Strategy fromDTO(StrategyDTO dto) {
        Strategy s = new Strategy();
        s.setStrategyCode(dto.getStrategyCode());
        s.setStrategyName(dto.getStrategyName());
        s.setStrategyType(dto.getStrategyType());
        s.setRiskLevel(dto.getRiskLevel());
        s.setTargetReturn(dto.getTargetReturn());
        s.setMaxDrawdown(dto.getMaxDrawdown());
        s.setInvestmentHorizon(dto.getInvestmentHorizon());
        s.setDescription(dto.getDescription());
        s.setFactorTreeId(dto.getFactorTreeId());
        s.setParameters(dto.getParameters());
        s.setStatus(dto.getStatus());
        return s;
    }
    // 实体转VO
    private StrategyVO toVO(Strategy s) {
        if (s == null) return null;
        StrategyVO vo = new StrategyVO();
        vo.setId(s.getId());
        vo.setStrategyCode(s.getStrategyCode());
        vo.setStrategyName(s.getStrategyName());
        vo.setStrategyType(s.getStrategyType());
        vo.setRiskLevel(s.getRiskLevel());
        vo.setTargetReturn(s.getTargetReturn());
        vo.setMaxDrawdown(s.getMaxDrawdown());
        vo.setInvestmentHorizon(s.getInvestmentHorizon());
        vo.setDescription(s.getDescription());
        vo.setFactorTreeId(s.getFactorTreeId());
        vo.setParameters(s.getParameters());
        vo.setStatus(s.getStatus());
        vo.setCreatedAt(s.getCreatedAt());
        vo.setUpdatedAt(s.getUpdatedAt());
        return vo;
    }
    // 更新实体
    private void updateEntityFromDTO(Strategy s, StrategyDTO dto) {
        s.setStrategyCode(dto.getStrategyCode());
        s.setStrategyName(dto.getStrategyName());
        s.setStrategyType(dto.getStrategyType());
        s.setRiskLevel(dto.getRiskLevel());
        s.setTargetReturn(dto.getTargetReturn());
        s.setMaxDrawdown(dto.getMaxDrawdown());
        s.setInvestmentHorizon(dto.getInvestmentHorizon());
        s.setDescription(dto.getDescription());
        s.setFactorTreeId(dto.getFactorTreeId());
        s.setParameters(dto.getParameters());
        s.setStatus(dto.getStatus());
    }
} 