package com.neulab.fund.service.impl;

import com.neulab.fund.entity.FundCompany;
import com.neulab.fund.repository.FundCompanyRepository;
import com.neulab.fund.service.FundCompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 基金公司业务实现
 */
@Service
public class FundCompanyServiceImpl implements FundCompanyService {
    private final FundCompanyRepository fundCompanyRepository;

    public FundCompanyServiceImpl(FundCompanyRepository fundCompanyRepository) {
        this.fundCompanyRepository = fundCompanyRepository;
    }

    @Override
    public List<FundCompany> getAllCompanies() {
        return fundCompanyRepository.findAll();
    }

    @Override
    public FundCompany getCompanyById(Long id) {
        return fundCompanyRepository.findById(id).orElse(null);
    }

    @Override
    public FundCompany createCompany(FundCompany company) {
        return fundCompanyRepository.save(company);
    }
} 