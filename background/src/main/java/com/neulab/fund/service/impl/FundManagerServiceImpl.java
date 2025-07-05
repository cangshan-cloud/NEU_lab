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

    @Override
    public java.util.List<com.neulab.fund.vo.FundManagerVO> getAllManagerVOs() {
        java.util.List<FundManager> managerList = fundManagerRepository.findAll();
        java.util.List<com.neulab.fund.vo.FundManagerVO> voList = new java.util.ArrayList<>();
        for (FundManager manager : managerList) {
            com.neulab.fund.vo.FundManagerVO vo = new com.neulab.fund.vo.FundManagerVO();
            vo.setId(manager.getId());
            vo.setManagerCode(manager.getManagerCode());
            vo.setManagerName(manager.getManagerName());
            vo.setGender(manager.getGender());
            vo.setBirthDate(manager.getBirthDate());
            vo.setEducation(manager.getEducation());
            vo.setExperienceYears(manager.getExperienceYears());
            vo.setCompanyId(manager.getCompanyId());
            vo.setIntroduction(manager.getIntroduction());
            vo.setInvestmentPhilosophy(manager.getInvestmentPhilosophy());
            vo.setAwards(manager.getAwards());
            vo.setStatus(manager.getStatus());
            vo.setCreatedAt(manager.getCreatedAt() != null ? manager.getCreatedAt().toString() : null);
            vo.setUpdatedAt(manager.getUpdatedAt() != null ? manager.getUpdatedAt().toString() : null);
            voList.add(vo);
        }
        return voList;
    }
} 