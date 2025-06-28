package com.neulab.fund.service.impl;

import com.neulab.fund.entity.FundManager;
import com.neulab.fund.repository.FundManagerRepository;
import com.neulab.fund.service.FundManagerService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 基金经理业务实现
 */
@Service
public class FundManagerServiceImpl implements FundManagerService {
    private final FundManagerRepository fundManagerRepository;

    public FundManagerServiceImpl(FundManagerRepository fundManagerRepository) {
        this.fundManagerRepository = fundManagerRepository;
    }

    @Override
    public List<FundManager> getAllManagers() {
        return fundManagerRepository.findAll();
    }

    @Override
    public FundManager getManagerById(Long id) {
        return fundManagerRepository.findById(id).orElse(null);
    }

    @Override
    public FundManager createManager(FundManager manager) {
        return fundManagerRepository.save(manager);
    }
} 