package com.neulab.fund.service.impl;

import com.neulab.fund.entity.IndexPortfolio;
import com.neulab.fund.repository.IndexPortfolioRepository;
import com.neulab.fund.service.IndexPortfolioService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 基金指数组合业务实现
 */
@Service
public class IndexPortfolioServiceImpl implements IndexPortfolioService {
    private final IndexPortfolioRepository indexPortfolioRepository;

    public IndexPortfolioServiceImpl(IndexPortfolioRepository indexPortfolioRepository) {
        this.indexPortfolioRepository = indexPortfolioRepository;
    }

    @Override
    public List<IndexPortfolio> getAllIndexPortfolios() {
        return indexPortfolioRepository.findAll();
    }

    @Override
    public IndexPortfolio getIndexPortfolioById(Long id) {
        return indexPortfolioRepository.findById(id).orElse(null);
    }

    @Override
    public IndexPortfolio createIndexPortfolio(IndexPortfolio indexPortfolio) {
        return indexPortfolioRepository.save(indexPortfolio);
    }
} 