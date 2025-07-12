package com.neulab.fund.service.impl;

import com.neulab.fund.entity.FundPortfolio;
import com.neulab.fund.repository.FundPortfolioRepository;
import com.neulab.fund.service.FundPortfolioService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
        // 若portfolio_code为空则自动生成
        if (portfolio.getPortfolioCode() == null || portfolio.getPortfolioCode().trim().isEmpty()) {
            portfolio.setPortfolioCode(generatePortfolioCode());
        }
        return fundPortfolioRepository.save(portfolio);
    }

    /**
     * 生成唯一组合代码（不超过20位）
     */
    private String generatePortfolioCode() {
        // FP + 时间戳后6位 + 4位大写字母，总长12位
        String timePart = String.valueOf(System.currentTimeMillis()).substring(7); // 6位
        String uuidPart = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 4).toUpperCase();
        return "FP" + timePart + uuidPart;
    }

    @Override
    public FundPortfolio updatePortfolio(FundPortfolio portfolio) {
        return fundPortfolioRepository.save(portfolio);
    }

    @Override
    public void deletePortfolio(Long id) {
        fundPortfolioRepository.deleteById(id);
    }

    @Override
    public java.util.List<com.neulab.fund.vo.FundPortfolioVO> getAllPortfolioVOs() {
        java.util.List<FundPortfolio> portfolioList = fundPortfolioRepository.findAll();
        java.util.List<com.neulab.fund.vo.FundPortfolioVO> voList = new java.util.ArrayList<>();
        for (FundPortfolio portfolio : portfolioList) {
            com.neulab.fund.vo.FundPortfolioVO vo = new com.neulab.fund.vo.FundPortfolioVO();
            vo.setId(portfolio.getId());
            vo.setPortfolioCode(portfolio.getPortfolioCode());
            vo.setPortfolioName(portfolio.getPortfolioName());
            vo.setPortfolioType(portfolio.getPortfolioType());
            vo.setRiskLevel(portfolio.getRiskLevel());
            vo.setTargetReturn(portfolio.getTargetReturn());
            vo.setMaxDrawdown(portfolio.getMaxDrawdown());
            vo.setInvestmentHorizon(portfolio.getInvestmentHorizon());
            vo.setMinInvestment(portfolio.getMinInvestment());
            vo.setStatus(portfolio.getStatus());
            vo.setDescription(portfolio.getDescription());
            vo.setCreatedAt(portfolio.getCreatedAt() != null ? portfolio.getCreatedAt().toString() : null);
            vo.setUpdatedAt(portfolio.getUpdatedAt() != null ? portfolio.getUpdatedAt().toString() : null);
            voList.add(vo);
        }
        return voList;
    }
} 