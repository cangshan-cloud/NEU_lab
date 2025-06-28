package com.neulab.fund.service.impl;

import com.neulab.fund.entity.Strategy;
import com.neulab.fund.repository.StrategyRepository;
import com.neulab.fund.service.StrategyService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 策略业务实现
 */
@Service
public class StrategyServiceImpl implements StrategyService {
    private final StrategyRepository strategyRepository;

    public StrategyServiceImpl(StrategyRepository strategyRepository) {
        this.strategyRepository = strategyRepository;
    }

    @Override
    public List<Strategy> getAllStrategies() {
        return strategyRepository.findAll();
    }

    @Override
    public Strategy getStrategyById(Long id) {
        return strategyRepository.findById(id).orElse(null);
    }

    @Override
    public Strategy createStrategy(Strategy strategy) {
        return strategyRepository.save(strategy);
    }
} 