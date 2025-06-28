package com.neulab.fund.service.impl;

import com.neulab.fund.entity.StrategyPortfolio;
import com.neulab.fund.repository.StrategyPortfolioRepository;
import com.neulab.fund.service.StrategyPortfolioService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 策略与组合关联业务实现
 */
@Service
public class StrategyPortfolioServiceImpl implements StrategyPortfolioService {
    private final StrategyPortfolioRepository strategyPortfolioRepository;

    public StrategyPortfolioServiceImpl(StrategyPortfolioRepository strategyPortfolioRepository) {
        this.strategyPortfolioRepository = strategyPortfolioRepository;
    }

    @Override
    public List<StrategyPortfolio> getAllRelations() {
        return strategyPortfolioRepository.findAll();
    }

    @Override
    public StrategyPortfolio getRelationById(Long id) {
        return strategyPortfolioRepository.findById(id).orElse(null);
    }

    @Override
    public StrategyPortfolio createRelation(StrategyPortfolio relation) {
        return strategyPortfolioRepository.save(relation);
    }
} 