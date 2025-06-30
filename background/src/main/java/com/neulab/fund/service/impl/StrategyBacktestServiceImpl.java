package com.neulab.fund.service.impl;

import com.neulab.fund.entity.StrategyBacktest;
import com.neulab.fund.repository.StrategyBacktestRepository;
import com.neulab.fund.service.StrategyBacktestService;
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
} 