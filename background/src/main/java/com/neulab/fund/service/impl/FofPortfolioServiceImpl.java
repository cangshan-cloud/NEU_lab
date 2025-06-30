package com.neulab.fund.service.impl;

import com.neulab.fund.entity.FofPortfolio;
import com.neulab.fund.repository.FofPortfolioRepository;
import com.neulab.fund.service.FofPortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * FOF组合业务实现
 */
@Service
public class FofPortfolioServiceImpl implements FofPortfolioService {
    @Autowired
    private FofPortfolioRepository fofPortfolioRepository;

    @Override
    public List<FofPortfolio> getAllFofPortfolios() {
        return fofPortfolioRepository.findAll();
    }

    @Override
    public FofPortfolio getFofPortfolioById(Long id) {
        return fofPortfolioRepository.findById(id).orElse(null);
    }

    @Override
    public FofPortfolio createFofPortfolio(FofPortfolio fofPortfolio) {
        return fofPortfolioRepository.save(fofPortfolio);
    }
} 