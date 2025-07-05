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

    @Override
    public java.util.List<com.neulab.fund.vo.FundCompanyVO> getAllCompanyVOs() {
        java.util.List<FundCompany> companyList = fundCompanyRepository.findAll();
        java.util.List<com.neulab.fund.vo.FundCompanyVO> voList = new java.util.ArrayList<>();
        for (FundCompany company : companyList) {
            com.neulab.fund.vo.FundCompanyVO vo = new com.neulab.fund.vo.FundCompanyVO();
            vo.setId(company.getId());
            vo.setCompanyCode(company.getCompanyCode());
            vo.setCompanyName(company.getCompanyName());
            vo.setCompanyShortName(company.getCompanyShortName());
            vo.setEstablishmentDate(company.getEstablishmentDate());
            vo.setRegisteredCapital(company.getRegisteredCapital());
            vo.setLegalRepresentative(company.getLegalRepresentative());
            vo.setContactPhone(company.getContactPhone());
            vo.setContactEmail(company.getContactEmail());
            vo.setWebsite(company.getWebsite());
            vo.setStatus(company.getStatus());
            vo.setDescription(company.getDescription());
            vo.setCreatedAt(company.getCreatedAt() != null ? company.getCreatedAt().toString() : null);
            vo.setUpdatedAt(company.getUpdatedAt() != null ? company.getUpdatedAt().toString() : null);
            voList.add(vo);
        }
        return voList;
    }
} 