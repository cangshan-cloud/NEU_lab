package com.neulab.fund.service.impl;

import com.neulab.fund.entity.TimingPortfolio;
import com.neulab.fund.repository.TimingPortfolioRepository;
import com.neulab.fund.service.TimingPortfolioService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 择时组合业务实现
 */
@Service
public class TimingPortfolioServiceImpl implements TimingPortfolioService {
    private final TimingPortfolioRepository timingPortfolioRepository;

    public TimingPortfolioServiceImpl(TimingPortfolioRepository timingPortfolioRepository) {
        this.timingPortfolioRepository = timingPortfolioRepository;
    }

    @Override
    public List<TimingPortfolio> getAllTimingPortfolios() {
        return timingPortfolioRepository.findAll();
    }

    @Override
    public TimingPortfolio getTimingPortfolioById(Long id) {
        return timingPortfolioRepository.findById(id).orElse(null);
    }

    @Override
    public TimingPortfolio createTimingPortfolio(TimingPortfolio timingPortfolio) {
        return timingPortfolioRepository.save(timingPortfolio);
    }
} 