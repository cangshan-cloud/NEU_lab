package com.neulab.fund.service.impl;

import com.neulab.fund.entity.Fund;
import com.neulab.fund.repository.FundRepository;
import com.neulab.fund.service.FundService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 基金业务实现
 */
@Service
public class FundServiceImpl implements FundService {
    private final FundRepository fundRepository;

    public FundServiceImpl(FundRepository fundRepository) {
        this.fundRepository = fundRepository;
    }

    @Override
    public List<Fund> getAllFunds() {
        return fundRepository.findAll();
    }

    @Override
    public Fund getFundById(Long id) {
        return fundRepository.findById(id).orElse(null);
    }

    @Override
    public Fund createFund(Fund fund) {
        return fundRepository.save(fund);
    }
} 