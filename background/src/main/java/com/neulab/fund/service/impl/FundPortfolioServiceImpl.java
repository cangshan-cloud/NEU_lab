package com.neulab.fund.service.impl;

import com.neulab.fund.entity.FundPortfolio;
import com.neulab.fund.repository.FundPortfolioRepository;
import com.neulab.fund.service.FundPortfolioService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 基金组合业务实现
 */
@Service
public class FundPortfolioServiceImpl implements FundPortfolioService {
    private final FundPortfolioRepository fundPortfolioRepository;

    public FundPortfolioServiceImpl(FundPortfolioRepository fundPortfolioRepository) {
        this.fundPortfolioRepository = fundPortfolioRepository;
    }

    @Override
    public List<FundPortfolio> getAllPortfolios() {
        return fundPortfolioRepository.findAll();
    }

    @Override
    public FundPortfolio getPortfolioById(Long id) {
        return fundPortfolioRepository.findById(id).orElse(null);
    }

    @Override
    public FundPortfolio createPortfolio(FundPortfolio portfolio) {
        return fundPortfolioRepository.save(portfolio);
    }
} 