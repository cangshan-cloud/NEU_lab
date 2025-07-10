package com.neulab.fund.service.impl;

import com.neulab.fund.entity.StrategyBacktest;
import com.neulab.fund.repository.StrategyBacktestRepository;
import com.neulab.fund.service.StrategyBacktestService;
import com.neulab.fund.vo.StrategyBacktestDTO;
import com.neulab.fund.vo.StrategyBacktestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 策略回测业务实现
 */
@Service
public class StrategyBacktestServiceImpl implements StrategyBacktestService {
    @Autowired
    private StrategyBacktestRepository strategyBacktestRepository;

    @Override
    public List<StrategyBacktest> getAllBacktests() {
        return strategyBacktestRepository.findAll();
    }

    @Override
    public StrategyBacktest getBacktestById(Long id) {
        return strategyBacktestRepository.findById(id).orElse(null);
    }

    @Override
    public StrategyBacktest createBacktest(StrategyBacktest backtest) {
        return strategyBacktestRepository.save(backtest);
    }

    @Override
    public StrategyBacktestVO backtest(Long strategyId, StrategyBacktestDTO dto) {
        // 简单模拟：保存回测记录并返回VO
        com.neulab.fund.entity.StrategyBacktest entity = new com.neulab.fund.entity.StrategyBacktest();
        entity.setStrategyId(strategyId);
        entity.setBacktestName(dto.getBacktestName());
        entity.setBacktestType(dto.getBacktestType());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setInitialCapital(dto.getInitialCapital());
        entity.setBacktestResult(dto.getParameters());
        entity.setStatus("SUCCESS");
        entity.setCreatedAt(java.time.LocalDateTime.now());
        entity.setUpdatedAt(java.time.LocalDateTime.now());
        com.neulab.fund.entity.StrategyBacktest saved = strategyBacktestRepository.save(entity);
        return toVO(saved);
    }
    private StrategyBacktestVO toVO(com.neulab.fund.entity.StrategyBacktest s) {
        if (s == null) return null;
        StrategyBacktestVO vo = new StrategyBacktestVO();
        vo.setId(s.getId());
        vo.setStrategyId(s.getStrategyId());
        vo.setBacktestName(s.getBacktestName());
        vo.setBacktestType(s.getBacktestType());
        vo.setStartDate(s.getStartDate());
        vo.setEndDate(s.getEndDate());
        vo.setInitialCapital(s.getInitialCapital());
        vo.setFinalValue(s.getFinalValue());
        vo.setTotalReturn(s.getTotalReturn());
        vo.setAnnualReturn(s.getAnnualReturn());
        vo.setMaxDrawdown(s.getMaxDrawdown());
        vo.setSharpeRatio(s.getSharpeRatio());
        vo.setVolatility(s.getVolatility());
        vo.setWinRate(s.getWinRate());
        vo.setBacktestResult(s.getBacktestResult());
        vo.setStatus(s.getStatus());
        vo.setCreatedAt(s.getCreatedAt());
        vo.setUpdatedAt(s.getUpdatedAt());
        vo.setResults(s.getResults());
        return vo;
    }

    @Override
    public org.springframework.data.domain.Page<com.neulab.fund.entity.StrategyBacktest> findByStrategyId(Long strategyId, org.springframework.data.domain.Pageable pageable) {
        return strategyBacktestRepository.findByStrategyId(strategyId, pageable);
    }
    @Override
    public org.springframework.data.domain.Page<com.neulab.fund.entity.StrategyBacktest> findAll(org.springframework.data.domain.Pageable pageable) {
        return strategyBacktestRepository.findAll(pageable);
    }
} 